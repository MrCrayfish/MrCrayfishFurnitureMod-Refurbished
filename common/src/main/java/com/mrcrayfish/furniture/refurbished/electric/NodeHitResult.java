package com.mrcrayfish.furniture.refurbished.electric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class NodeHitResult extends HitResult
{
    private final BlockPos pos;
    private final IElectricNode node;

    public NodeHitResult(Vec3 hit, @Nullable BlockPos pos, @Nullable IElectricNode node)
    {
        super(hit);
        this.pos = pos;
        this.node = node;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public IElectricNode getNode()
    {
        return this.node;
    }

    @Override
    public Type getType()
    {
        return this.node != null ? Type.BLOCK : Type.MISS;
    }
}
