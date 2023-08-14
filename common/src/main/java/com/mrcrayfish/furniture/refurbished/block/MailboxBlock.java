package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.DrawerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class MailboxBlock extends FurnitureHorizontalBlock implements EntityBlock, BlockTagSupplier
{
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
        if(!level.isClientSide() && entity instanceof Player player && level.getBlockEntity(pos) instanceof MailboxBlockEntity mailbox)
        {
            mailbox.getMailbox().owner().setValue(player.getUUID());
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide() && level.getBlockEntity(pos) instanceof MailboxBlockEntity mailbox)
        {
            player.openMenu(mailbox);
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
