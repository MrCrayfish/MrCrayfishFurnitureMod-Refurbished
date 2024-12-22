package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

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
    EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    BooleanProperty LIT = BlockStateProperties.LIT;
}
