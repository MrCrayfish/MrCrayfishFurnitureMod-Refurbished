package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public abstract class FurnitureAttachedFaceBlock extends FaceAttachedHorizontalDirectionalBlock
{
    protected final Map<BlockState, VoxelShape> shapes;

    protected FurnitureAttachedFaceBlock(Properties properties)
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(FACE);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction)
    {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return state.getBlock() instanceof EntityBlock;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int type)
    {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(id, type);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type)
    {
        return false;
    }
}
