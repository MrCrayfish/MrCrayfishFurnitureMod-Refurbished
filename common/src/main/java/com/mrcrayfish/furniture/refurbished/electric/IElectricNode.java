package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Author: MrCrayfish
 */
public interface IElectricNode
{
    AABB DEFAULT_NODE_BOX = new AABB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);

    BlockPos getPosition();

    Level getLevel();

    BlockEntity getBlockEntity();

    boolean isSource();

    boolean isPowered();

    void setPowered(boolean powered);

    Set<Connection> getConnections();

    void setReceivingPower(boolean power);

    boolean isReceivingPower();

    default boolean isValid()
    {
        return !this.getBlockEntity().isRemoved();
    }

    default void onDestroyed()
    {
        this.removeAllConnections();
    }

    default void onConnectedTo(IElectricNode other) {}

    default boolean canPowerTraverse()
    {
        return true;
    }

    default Set<Connection> updateAndGetConnections()
    {
        this.updateConnections();
        return this.getConnections();
    }

    default void updatePoweredState()
    {
        if(!this.isReceivingPower())
        {
            if(this.isPowered())
            {
                this.setPowered(false);
            }
        }
        else if(!this.isPowered())
        {
            this.setPowered(true);
        }
    }

    default void syncNodeData()
    {
        this.updateConnections();
        CompoundTag compound = new CompoundTag();
        this.writeConnections(compound);
        BlockEntityHelper.sendCustomUpdate(this.getBlockEntity(), compound);
    }

    default void readConnections(CompoundTag tag)
    {
        if(tag.contains("Connections", Tag.TAG_LONG_ARRAY))
        {
            BlockPos pos = this.getPosition();
            Set<Connection> connections = this.getConnections();
            connections.clear();
            long[] nodes = tag.getLongArray("Connections");
            for(long node : nodes)
            {
                connections.add(Connection.of(pos, BlockPos.of(node)));
            }
        }
    }

    default void writeConnections(CompoundTag tag)
    {
        Set<Connection> connections = this.getConnections();
        tag.putLongArray("Connections", connections.stream().map(Connection::getPosB).map(BlockPos::asLong).toList());
    }

    default void removeConnection(Connection connection)
    {
        this.updateAndGetConnections().remove(connection);
        this.syncNodeData();
    }

    default void removeAllConnections()
    {
        Level level = this.getLevel();
        Set<Connection> connections = this.getConnections();
        connections.forEach(c -> {
            IElectricNode node = c.getNodeB(level);
            if(node != null) {
                node.removeConnection(c);
            }
        });
        connections.clear();
    }

    default void updateConnections()
    {
        Level level = this.getLevel();
        this.getConnections().removeIf(c -> !c.isConnected(level));
    }

    default boolean isConnectionLimit()
    {
        return this.getConnections().size() >= Config.SERVER.electricity.maximumLinksPerElectricityNode.get();
    }

    default boolean connectTo(IElectricNode other)
    {
        BlockPos pos = this.getPosition();
        Set<Connection> connections = this.getConnections();
        if(connections.add(Connection.of(pos, other.getPosition())))
        {
            other.connectTo(this);
            this.onConnectedTo(other);
            this.syncNodeData();
            return true;
        }
        return false;
    }

    default boolean isConnectedTo(IElectricNode node)
    {
        Set<Connection> connections = this.getConnections();
        return connections.stream().anyMatch(c -> c.getPosB().equals(node.getPosition()));
    }

    default AABB getPositionedInteractBox()
    {
        return this.getInteractBox().move(this.getPosition());
    }

    default AABB getInteractBox()
    {
        return DEFAULT_NODE_BOX;
    }

    default void invalidateCache() {}

    static void searchNodes(IElectricNode start, Set<IElectricNode> found, int maxDepth, Predicate<IElectricNode> predicate)
    {
        // Add start to found if predicate matches
        if(predicate.test(start))
        {
            found.add(start);
        }

        // Queue representing nodes to search and their depth from the start
        Queue<Pair<IElectricNode, Integer>> queue = new ArrayDeque<>();
        queue.add(Pair.of(start, 0));
        while(!queue.isEmpty())
        {
            Pair<IElectricNode, Integer> pair = queue.poll();
            IElectricNode node = pair.getLeft();
            int currentDepth = pair.getRight();
            for(Connection connection : node.getConnections())
            {
                IElectricNode other = connection.getNodeB(node.getLevel());
                if(other == null || !other.isValid() || found.contains(other))
                    continue;

                if(!predicate.test(other))
                    continue;

                found.add(other);

                int nextDepth = currentDepth + 1;
                if(other.isSource() || !other.canPowerTraverse() || nextDepth >= maxDepth)
                    continue;

                queue.add(Pair.of(other, nextDepth));
            }
        }
    }
}
