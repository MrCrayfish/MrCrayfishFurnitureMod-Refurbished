package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class ComputerRenderState extends BlockEntityRenderState
{
    public boolean powered = false;
    public Direction direction = Direction.NORTH;
}
