package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class PostBoxBlock extends FurnitureHorizontalBlock
{
    public PostBoxBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(4, 0, 4, 12, 2, 12);
        VoxelShape standShape = Block.box(6, 2, 6, 10, 9, 10);
        VoxelShape topShape = Block.box(2, 9, 1, 14, 22, 15);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return VoxelShapeHelper.combine(List.of(baseShape, standShape, VoxelShapeHelper.rotateHorizontally(topShape, state.getValue(DIRECTION))));
        })));
    }
}
