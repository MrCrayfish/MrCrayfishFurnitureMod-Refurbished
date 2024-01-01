package com.mrcrayfish.furniture.refurbished.block;

import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class HedgeBlock extends CrossCollisionBlock implements BlockTagSupplier
{
    private final LeafType type;

    public HedgeBlock(LeafType type, Properties properties)
    {
        super(4, 4, 16, 16, 24, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
        this.type = type;
    }

    public LeafType getLeafType()
    {
        return this.type;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getHedgeState(state, level, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.getHedgeState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    private BlockState getHedgeState(BlockState state, LevelAccessor level, BlockPos pos)
    {
        FluidState fluid = level.getFluidState(pos);
        boolean north = this.canConnectToFace(level, pos, Direction.NORTH);
        boolean east = this.canConnectToFace(level, pos, Direction.EAST);
        boolean south = this.canConnectToFace(level, pos, Direction.SOUTH);
        boolean west = this.canConnectToFace(level, pos, Direction.WEST);
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west).setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    private boolean canConnectToFace(LevelAccessor level, BlockPos pos, Direction direction)
    {
        pos = pos.relative(direction);
        BlockState state = level.getBlockState(pos);
        return !isExceptionForConnection(state) && state.isFaceSturdy(level, pos, direction.getOpposite()) || state.getBlock() instanceof HedgeBlock || state.getBlock() instanceof LeavesBlock;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Deprecated
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos)
    {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_HOE);
    }
}
