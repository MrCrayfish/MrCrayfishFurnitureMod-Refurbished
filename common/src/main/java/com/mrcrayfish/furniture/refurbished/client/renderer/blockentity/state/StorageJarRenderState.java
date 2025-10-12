package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class StorageJarRenderState extends BlockEntityRenderState
{
    public ItemStackRenderState[] items;
    public Direction direction;
    public Component label;
}
