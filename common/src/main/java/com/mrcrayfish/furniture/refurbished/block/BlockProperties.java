package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

/**
 * Author: MrCrayfish
 */
public interface BlockProperties
{
    BooleanProperty NORTH = BlockStateProperties.NORTH;
    BooleanProperty EAST = BlockStateProperties.EAST;
    BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    BooleanProperty WEST = BlockStateProperties.WEST;
    BooleanProperty OPEN = BlockStateProperties.OPEN;
    BooleanProperty LEFT = BooleanProperty.create("left");
    BooleanProperty RIGHT = BooleanProperty.create("right");
    BooleanProperty POWERED = BlockStateProperties.POWERED;
    BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    DirectionProperty FACING = BlockStateProperties.FACING;
    BooleanProperty LIT = BlockStateProperties.LIT;
}
