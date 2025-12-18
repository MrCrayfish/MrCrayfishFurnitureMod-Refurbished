package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

public class TelevisionRenderState extends BlockEntityRenderState
{
    public boolean powered = false;
    public Identifier currentChannel = TelevisionBlockEntity.WHITE_NOISE.id();
    public Direction direction = Direction.NORTH;
}
