package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class TelevisionBlock extends FurnitureHorizontalBlock
{
    public TelevisionBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(2, 0, 2, 14, 12, 14);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> baseShape)));
    }
}
