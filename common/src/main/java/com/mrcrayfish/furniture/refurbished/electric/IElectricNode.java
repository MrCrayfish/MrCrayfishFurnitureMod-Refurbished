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
import java.util.HashSet;
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

    default boolean isValid()
    {
        return !this.getBlockEntity().isRemoved();
    }

    default void onDestroyed()
    {
        this.updatePowerInNetwork(true);
        this.removeAllConnections();
    }

    default void onConnectedTo(IElectricNode other)
    {
        // Don't handle power update is other is a source
        if(other.isSource())
            return;

        // If connected node is not powered, don't bother performing an update
        if(!this.isPowered() && !other.isPowered())
            return;

        this.updatePowerInNetwork(false);
    }

    default void updatePower()
    {
        this.updatePowerInNetwork(false);
    }

    default Set<Connection> updateAndGetConnections()
    {
        this.updateConnections();
        return this.getConnections();
    }

    default void syncConnections()
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
        this.getConnections().remove(connection);
        this.syncConnections();
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
            this.syncConnections();
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

    default void updatePowerInNetwork(boolean removed)
    {
        // Test to check this node is a valid traversal node. On removed, it shouldn't be.
        Predicate<IElectricNode> includeSelf = node -> (!removed || node != this);
        int maxSearchDepth = Config.SERVER.electricity.maximumDaisyChain.get();

        // First find all the available nodes that we can connect to.
        Set<IElectricNode> availableNodes = new HashSet<>();
        searchNodes(this, availableNodes, maxSearchDepth, node -> !node.isSource() && includeSelf.test(node));

        // Next, search for powered electric source nodes in the network.
        // Again if this electric node is being removed, prevent it from being searched
        Set<IElectricNode> sourceNodes = new HashSet<>();
        searchNodes(this, sourceNodes, maxSearchDepth * 2, node -> includeSelf.test(node));
        sourceNodes.removeIf(node -> !node.isSource() || !node.isPowered());

        // For each powered source nodes, find all the nodes that it can connect to. Again, if this
        // node is being removed, ensure that it is ignored as a valid connection path.
        Set<IElectricNode> foundNodes = new HashSet<>();
        sourceNodes.forEach(source -> {
            Set<IElectricNode> searchedNodes = new HashSet<>();
            searchNodes(source, searchedNodes, maxSearchDepth, node -> !node.isSource() && includeSelf.test(node));
            foundNodes.addAll(searchedNodes);
        });

        // Update the power state of available nodes. Only the nodes where their power is different
        // will be updated. This comes in handy when you want to play sounds when an electric module
        // is turned on or off.
        availableNodes.forEach(node -> {
            if(foundNodes.contains(node)) {
                if(!node.isPowered()) {
                    node.setPowered(true);
                }
            }
            else if(node.isPowered()) {
                node.setPowered(false);
            }
        });
    }

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
                if(other.isSource() || nextDepth >= maxDepth)
                    continue;

                queue.add(Pair.of(other, nextDepth));
            }
        }
    }
}
