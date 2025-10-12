package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class FryingPanRenderState extends BlockEntityRenderState
{
    public ItemStackRenderState item = new ItemStackRenderState();
    public boolean itemFlipped;
    public int itemRotation;
    public boolean animationPlaying;
    public float animationTime;
}
