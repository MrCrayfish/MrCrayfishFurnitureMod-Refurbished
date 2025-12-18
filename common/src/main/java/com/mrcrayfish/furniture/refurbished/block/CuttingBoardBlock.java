package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.ConventionalTags;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlock extends FurnitureHorizontalEntityBlock implements BlockTagSupplier
{
    private static final MapCodec<CuttingBoardBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, CuttingBoardBlock::new);
    });

    private final WoodType type;

    public CuttingBoardBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<CuttingBoardBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(3, 0, 1, 13, 1, 15);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return VoxelShapeHelper.rotateHorizontally(baseShape, state.getValue(DIRECTION));
        })));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard)
        {
            ItemStack heldItem = player.getItemInHand(hand);
            if(heldItem.is(ConventionalTags.Items.TOOLS_KNIVES))
            {
                boolean dropAsEntity = !Services.ENTITY.isFakePlayer(player);
                if(cuttingBoard.sliceItem(level, dropAsEntity))
                {
                    if(!level.isClientSide())
                    {
                        heldItem.hurtAndBreak(1, player, hand.asEquipmentSlot());
                    }
                }
                return InteractionResult.SUCCESS;
            }
            else if(cuttingBoard.placeItem(heldItem)) // Modify to only be server side
            {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new CuttingBoardBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        if(!level.isClientSide())
        {
            return createTicker(type, ModBlockEntities.CUTTING_BOARD.get(), CuttingBoardBlockEntity::serverTick);
        }
        return null;
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}
