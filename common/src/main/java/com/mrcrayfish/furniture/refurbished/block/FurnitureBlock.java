package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public abstract class FurnitureBlock extends Block implements BlockProperties
{
    protected final Map<BlockState, VoxelShape> shapes;

    public FurnitureBlock(Properties properties)
    {
        super(properties);
        this.shapes = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    protected abstract Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        return this.shapes.get(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType type)
    {
        return false;
    }
}
