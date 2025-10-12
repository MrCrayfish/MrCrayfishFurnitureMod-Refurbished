package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class GrillRenderState extends BlockEntityRenderState
{
    public CookingItemStackRenderState[] foods;
    public ItemStackRenderState[] fuels;
}
