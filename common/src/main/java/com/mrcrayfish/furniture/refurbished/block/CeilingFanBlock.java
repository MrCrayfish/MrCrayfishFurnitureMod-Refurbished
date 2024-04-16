package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mrcrayfish.furniture.refurbished.blockentity.CeilingFanBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class CeilingFanBlock extends FurnitureEntityBlock implements BlockTagSupplier
{
    private final WoodType woodType;
    private final MetalType metalType;

    public CeilingFanBlock(WoodType woodType, MetalType metalType, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(LIT, false));
        this.woodType = woodType;
        this.metalType = metalType;
    }

    public WoodType getWoodType()
    {
        return this.woodType;
    }

    public MetalType getMetalType()
    {
        return this.metalType;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape ceilingShape = Block.box(5, 7.5, 5, 11, 16, 11);
        VoxelShape wallShape = Block.box(7.5, 5, 5, 16, 11, 11);
        VoxelShape floorShape = Block.box(5, 0, 5, 11, 8.5, 11);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return switch(state.getValue(FACING)) {
                case UP -> ceilingShape;
                case DOWN -> floorShape;
                default -> VoxelShapeHelper.rotateHorizontally(wallShape, state.getValue(FACING));
            };
        })));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        level.setBlock(pos, state.setValue(LIT, !state.getValue(LIT)), Block.UPDATE_ALL);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(POWERED);
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new CeilingFanBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        if(level.isClientSide())
        {
            return createTicker(type, ModBlockEntities.CEILING_FAN.get(), CeilingFanBlockEntity::clientTick);
        }
        return createTicker(type, ModBlockEntities.CEILING_FAN.get(), CeilingFanBlockEntity::serverTick);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    public static int light(BlockState state)
    {
        return state.getValue(POWERED) && state.getValue(LIT) ? 15 : 0;
    }
}
