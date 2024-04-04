package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.FurnitureScreens;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.mail.Mailbox;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageNameMailbox;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class MailboxBlock extends FurnitureHorizontalEntityBlock implements BlockTagSupplier
{
    private static final MapCodec<MailboxBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, MailboxBlock::new);
    });

    private final WoodType type;

    public MailboxBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(ENABLED, false));
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<MailboxBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape standShape = Block.box(6.5, 0, 6.5, 9.5, 11, 9.5);
        VoxelShape boxShape = Block.box(3, 11, 3, 13, 20, 13);
        VoxelShape joinedShape = Shapes.joinUnoptimized(standShape, boxShape, BooleanOp.OR).optimize();
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> joinedShape)));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        if(entity instanceof ServerPlayer player)
        {
            if(level.getBlockEntity(pos) instanceof MailboxBlockEntity mailbox)
            {
                mailbox.getMailbox().owner().setValue(player.getUUID());
                DeliveryService.get(((ServerLevel) level).getServer()).ifPresent(service -> {
                    service.markMailboxAsPendingName(player, level, pos);
                });
            }
            Network.getPlay().sendToPlayer(() -> player, new MessageNameMailbox(pos));
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(!level.isClientSide() && !state.is(newState.getBlock()))
        {
            if(level.getBlockEntity(pos) instanceof MailboxBlockEntity mailbox)
            {
                Optional.ofNullable(mailbox.getMailbox()).ifPresent(Mailbox::remove);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide() && level.getBlockEntity(pos) instanceof MailboxBlockEntity blockEntity)
        {
            // Remove the little flag once the player open the mailbox
            if(state.getValue(ENABLED))
            {
                level.setBlock(pos, state.setValue(ENABLED, false), Block.UPDATE_ALL);
            }

            // Claim the mailbox if the mailbox is not owned.
            Mailbox mailbox = blockEntity.getMailbox();
            if(mailbox != null && !mailbox.hasOwner())
            {
                mailbox.setOwner(player.getUUID());
            }

            player.openMenu(blockEntity);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(ENABLED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new MailboxBlockEntity(pos, state);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}
