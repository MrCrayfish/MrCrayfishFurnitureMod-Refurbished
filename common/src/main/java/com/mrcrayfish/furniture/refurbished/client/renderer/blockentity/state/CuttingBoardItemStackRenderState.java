package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.item.ItemStackRenderState;

public class CuttingBoardItemStackRenderState extends ItemStackRenderState
{
    private boolean displayAsBlock;

    public void setDisplayAsBlock(boolean displayAsBlock)
    {
        this.displayAsBlock = displayAsBlock;
    }

    public boolean isDisplayAsBlock()
    {
        return this.displayAsBlock;
    }
}
