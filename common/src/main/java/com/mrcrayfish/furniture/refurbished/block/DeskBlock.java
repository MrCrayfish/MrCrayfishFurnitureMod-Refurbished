package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class DeskBlock extends FurnitureHorizontalBlock implements BlockTagSupplier
{
    private final WoodType type;

    public DeskBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(LEFT, false).setValue(RIGHT, false));
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape topShape = Block.box(0, 14, 0, 16, 16, 16);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            boolean left = state.getValue(LEFT);
            boolean right = state.getValue(RIGHT);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(topShape);
            builder.put(state, VoxelShapeHelper.combine(shapes));
        }
        return builder.build();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        Direction facing = state.getValue(DIRECTION);
        boolean left = this.isConnectable(level, pos, facing.getClockWise(), facing);
        boolean right = this.isConnectable(level, pos, facing.getCounterClockWise(), facing);
        return state.setValue(LEFT, left).setValue(RIGHT, right);
    }

    public boolean isConnectable(LevelAccessor level, BlockPos pos, Direction checkDirection, Direction tableDirection)
    {
        BlockState state = level.getBlockState(pos.relative(checkDirection));
        return state.getBlock() instanceof DeskBlock && state.getValue(DIRECTION) == tableDirection;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(LEFT);
        builder.add(RIGHT);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}
