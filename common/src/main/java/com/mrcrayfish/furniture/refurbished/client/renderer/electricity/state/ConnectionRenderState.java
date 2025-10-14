package com.mrcrayfish.furniture.refurbished.client.renderer.electricity.state;

import net.minecraft.core.BlockPos;

import java.util.Objects;

public record ConnectionRenderState(BlockPos a, BlockPos b, boolean hovered, int colour)
{
    @Override
    public int hashCode()
    {
        return Objects.hash(this.a, this.b);
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null || this.getClass() != o.getClass()) return false;
        ConnectionRenderState that = (ConnectionRenderState) o;
        return this.a.equals(that.a) && this.b.equals(that.b);
    }
}
