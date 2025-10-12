package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class DoorMatRenderState extends BlockEntityRenderState
{
    public Direction direction = Direction.NORTH;
    public RenderType renderType = RenderType.cutout();
}
