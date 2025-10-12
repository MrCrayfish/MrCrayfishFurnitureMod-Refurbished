package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class ToasterRenderState extends BlockEntityRenderState
{
    public Direction direction = Direction.NORTH;
    public ItemStackRenderState[] items = new ItemStackRenderState[2];
    public boolean heating = false;
}
