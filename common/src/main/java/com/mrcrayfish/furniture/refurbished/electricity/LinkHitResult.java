package com.mrcrayfish.furniture.refurbished.electricity;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class LinkHitResult extends HitResult
{
    private final Connection connection;

    public LinkHitResult(Vec3 hit, @Nullable Connection connection)
    {
        super(hit);
        this.connection = connection;
    }

    /**
     * @return The connection (link) that was hit or null is missed
     */
    public Connection getConnection()
    {
        return this.connection;
    }

    @Override
    public Type getType()
    {
        return this.connection == null ? Type.MISS : Type.BLOCK;
    }
}
