package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class PlateRenderState extends BlockEntityRenderState
{
    public Direction direction = Direction.NORTH;
    public ItemStackRenderState item;
}
