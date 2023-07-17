package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class TableBlock extends FurnitureBlock
{
    private final WoodType type;
    private final ImmutableMap<BlockState, VoxelShape> shapes;

    public TableBlock(WoodType type, BlockBehaviour.Properties properties)
    {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
        this.shapes = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        // TODO update for new model and reduce size of top
        VoxelShape tableTopShape = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        VoxelShape middlePostShape = Block.box(6.0, 0.0, 6.0, 10.0, 14.0, 10.0);
        VoxelShape endPostShape = Block.box(3.0, 0.0, 6.0, 7.0, 14.0, 10.0);
        VoxelShape cornerPostShape = Block.box(3.0, 0.0, 9.0, 7.0, 14.0, 13.0);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean north = state.getValue(NORTH);
            boolean east = state.getValue(EAST);
            boolean south = state.getValue(SOUTH);
            boolean west = state.getValue(WEST);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(tableTopShape);

            /*if(!north & !east && !south && !west)
            {
                shapes.add(middlePostShape);
            }
            else if(north & !east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(endPostShape, Direction.NORTH));
            }
            else if(!north & east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(endPostShape, Direction.EAST));
            }
            else if(!north & !east && south && !west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(endPostShape, Direction.SOUTH));
            }
            else if(!north & !east && !south && west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(endPostShape, Direction.WEST));
            }
            else if(north && east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(cornerPostShape, Direction.EAST));
            }
            else if(!north && east && south && !west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(cornerPostShape, Direction.SOUTH));
            }
            else if(!north && !east && south && west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(cornerPostShape, Direction.WEST));
            }
            else if(north && !east && !south && west)
            {
                shapes.add(VoxelShapeHelper.rotateHorizontally(cornerPostShape, Direction.NORTH));
            }*/
            builder.put(state, VoxelShapeHelper.combine(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return this.shapes.get(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        boolean north = level.getBlockState(pos.north()).getBlock() instanceof TableBlock;
        boolean east = level.getBlockState(pos.east()).getBlock() instanceof TableBlock;
        boolean south = level.getBlockState(pos.south()).getBlock() instanceof TableBlock;
        boolean west = level.getBlockState(pos.west()).getBlock() instanceof TableBlock;
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
    }
}