package com.mrcrayfish.furniture.refurbished.electricity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class NodeHitResult extends HitResult
{
    private final BlockPos pos;
    private final IElectricityNode node;

    public NodeHitResult(Vec3 hit, @Nullable BlockPos pos, @Nullable IElectricityNode node)
    {
        super(hit);
        this.pos = pos;
        this.node = node;
    }

    /**
     * @return The block position of the electricity node or null if missed
     */
    @Nullable
    public BlockPos getPos()
    {
        return this.pos;
    }

    /**
     * @return The electricity node that was hit or null if missed
     */
    @Nullable
    public IElectricityNode getNode()
    {
        return this.node;
    }

    @Override
    public Type getType()
    {
        return this.node != null ? Type.BLOCK : Type.MISS;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        NodeHitResult that = (NodeHitResult) o;
        return Objects.equals(this.pos, that.pos);
    }

    @Override
    public int hashCode()
    {
        return this.pos != null ? this.pos.hashCode() : 0;
    }
}
