package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class CeilingFanRenderState extends BlockEntityRenderState
{
    public Direction direction = Direction.DOWN;
    public float rotation;
    public AABB damageBox;
}
