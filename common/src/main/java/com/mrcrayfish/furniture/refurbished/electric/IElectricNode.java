package com.mrcrayfish.furniture.refurbished.electric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

import java.util.Set;

/**
 * Author: MrCrayfish
 */
public interface IElectricNode
{
    boolean isValid();

    boolean isSource();

    boolean isPowered();

    void setPowered(boolean powered);

    Set<Connection> getConnections();

    void syncConnections();

    void removeConnection(Connection connection);

    boolean connectTo(IElectricNode other);

    boolean isConnectedTo(IElectricNode node);

    BlockPos getPosition();

    AABB getPositionedInteractBox();

    AABB getInteractBox();
}
