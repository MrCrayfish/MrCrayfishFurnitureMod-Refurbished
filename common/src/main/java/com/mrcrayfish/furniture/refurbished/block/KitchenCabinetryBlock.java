package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class KitchenCabinetryBlock extends FurnitureHorizontalBlock implements IKitchenCabinetry
{
    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    public KitchenCabinetryBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(SHAPE, Shape.DEFAULT));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape topShape = Block.box(0, 13, 0, 16, 16, 16);
        VoxelShape baseShape = Block.box(2, 0, 2, 16, 13, 14);
        VoxelShape baseLeftShape = Block.box(2, 0, 0, 16, 13, 2);
        VoxelShape baseRightShape = Block.box(2, 0, 14, 16, 13, 16);
        VoxelShape insideCornerLeftShape = Block.box(0, 0, 0, 2, 13, 14);
        VoxelShape insideCornerRightShape = Block.box(0, 0, 2, 2, 13, 16);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            Shape shape = state.getValue(SHAPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(topShape);
            shapes.add(VoxelShapeHelper.rotateHorizontally(baseShape, direction));
            if(shape != Shape.OUTSIDE_CORNER_LEFT)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(baseLeftShape, direction));
            }
            if(shape != Shape.OUTSIDE_CORNER_RIGHT)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(baseRightShape, direction));
            }
            VoxelShape cornerShape = switch(shape)
            {
                case INSIDE_CORNER_LEFT -> insideCornerLeftShape;
                case INSIDE_CORNER_RIGHT -> insideCornerRightShape;
                default -> Shapes.empty();
            };
            shapes.add(VoxelShapeHelper.rotateHorizontally(cornerShape, direction));
            builder.put(state, VoxelShapeHelper.combine(shapes));
        }
        return builder.build();
    }

    // TODO fix placement state not updating to neighbours

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return state.setValue(SHAPE, this.getShape(state, level, pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(SHAPE);
    }

    public Shape getShape(BlockState state, LevelAccessor level, BlockPos pos)
    {
        Direction facing = state.getValue(DIRECTION);
        Direction front = this.getCabinetryDirection(level, pos, state.getValue(DIRECTION).getOpposite());
        if(front != null)
        {
            if(front == facing.getClockWise())
            {
                return Shape.INSIDE_CORNER_RIGHT;
            }
            else if(front == facing.getCounterClockWise())
            {
                return Shape.INSIDE_CORNER_LEFT;
            }
        }
        Direction behind = this.getCabinetryDirection(level, pos, state.getValue(DIRECTION));
        if(behind != null)
        {
            if(behind == facing.getClockWise())
            {
                return Shape.OUTSIDE_CORNER_LEFT;
            }
            else if(behind == facing.getCounterClockWise())
            {
                return Shape.OUTSIDE_CORNER_RIGHT;
            }
        }
        return Shape.DEFAULT;
    }

    public Direction getCabinetryDirection(LevelAccessor level, BlockPos pos, Direction side)
    {
        BlockState relativeState = level.getBlockState(pos.relative(side));
        return relativeState.getBlock() instanceof IKitchenCabinetry cabinetry ? cabinetry.getDirection(relativeState) : null;
    }

    @Override
    public Direction getDirection(BlockState state)
    {
        return state.getValue(DIRECTION);
    }

    public enum Shape implements StringRepresentable
    {
        DEFAULT("default"),
        INSIDE_CORNER_LEFT("inside_corner_left"),
        INSIDE_CORNER_RIGHT("inside_corner_right"),
        OUTSIDE_CORNER_LEFT("outside_corner_left"),
        OUTSIDE_CORNER_RIGHT("outside_corner_right");

        private final String name;

        Shape(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return this.name;
        }
    }
}
