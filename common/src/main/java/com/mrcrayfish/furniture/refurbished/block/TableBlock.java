package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.data.tag.TagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class TableBlock extends FurnitureBlock implements BlockTagSupplier
{
    private final WoodType type;

    public TableBlock(WoodType type, BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape topShape = Block.box(0, 14, 0, 16, 16, 16);
        VoxelShape topNorthEdgeShape = Block.box(0, 14, -1, 16, 16, 0);
        VoxelShape topEastEdgeShape = Block.box(16, 14, 0, 17, 16, 16);
        VoxelShape topSouthEdgeShape = Block.box(0, 14, 16, 16, 16, 17);
        VoxelShape topWestEdgeShape = Block.box(-1, 14, 0, 0, 16, 16);
        VoxelShape northWestTopCornerShape = Block.box(-1, 14, -1, 0, 16, 0);
        VoxelShape northEastTopCornerShape = Block.box(16, 14, -1, 17, 16, 0);
        VoxelShape southWestTopCornerShape = Block.box(-1, 14, 16, 0, 16, 17);
        VoxelShape southEastTopCornerShape = Block.box(16, 14, 16, 17, 16, 17);
        VoxelShape northWestLegShape = Block.box(0, 0, 0, 2, 14, 2);
        VoxelShape northEastLegShape = Block.box(14, 0, 0, 16, 14, 2);
        VoxelShape southWestLegShape = Block.box(0, 0, 14, 2, 14, 16);
        VoxelShape southEastLegShape = Block.box(14, 0, 14, 16, 14, 16);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean north = state.getValue(NORTH);
            boolean east = state.getValue(EAST);
            boolean south = state.getValue(SOUTH);
            boolean west = state.getValue(WEST);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(topShape);
            if(!north)
            {
                shapes.add(topNorthEdgeShape);
                if(!east)
                {
                    shapes.add(northEastTopCornerShape);
                    shapes.add(northEastLegShape);
                }
                if(!west)
                {
                    shapes.add(northWestTopCornerShape);
                    shapes.add(northWestLegShape);
                }
            }
            if(!east)
            {
                shapes.add(topEastEdgeShape);
            }
            if(!south)
            {
                shapes.add(topSouthEdgeShape);
                if(!east)
                {
                    shapes.add(southEastTopCornerShape);
                    shapes.add(southEastLegShape);
                }
                if(!west)
                {
                    shapes.add(southWestTopCornerShape);
                    shapes.add(southWestLegShape);
                }
            }
            if(!west)
            {
                shapes.add(topWestEdgeShape);
            }
            builder.put(state, VoxelShapeHelper.combine(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
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

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE, ModTags.Blocks.TUCKABLE);
    }
}