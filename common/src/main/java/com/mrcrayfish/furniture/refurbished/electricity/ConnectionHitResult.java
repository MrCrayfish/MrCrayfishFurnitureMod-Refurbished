package com.mrcrayfish.furniture.refurbished.electricity;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class ConnectionHitResult extends HitResult
{
    private final Connection connection;

    public ConnectionHitResult(Vec3 hit, @Nullable Connection connection)
    {
        super(hit);
        this.connection = connection;
    }

    /**
     * @return The connection (link) that was hit or null is missed
     */
    @Nullable
    public Connection getConnection()
    {
        return this.connection;
    }

    @Override
    public Type getType()
    {
        return this.connection == null ? Type.MISS : Type.BLOCK;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ConnectionHitResult that = (ConnectionHitResult) o;
        return Objects.equals(this.connection, that.connection);
    }

    @Override
    public int hashCode()
    {
        return this.connection != null ? this.connection.hashCode() : 0;
    }
}
