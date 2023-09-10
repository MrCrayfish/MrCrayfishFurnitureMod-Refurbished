package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.Config;
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
public interface ISourceNode extends IElectricNode
{
    AABB DEFAULT_NODE_BOX = new AABB(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);

    @Override
    default boolean isSource()
    {
        return true;
    }

    @Override
    default void onDestroyed()
    {
        this.updatePowerInNetwork(false);
        this.removeAllConnections();
    }

    @Override
    default void onConnectedTo(IElectricNode other)
    {
        if(other.isSource())
            return;

        if(!this.isPowered())
            return;

        this.updatePowerInNetwork(true);
    }

    @Override
    default void updatePower()
    {
        this.updatePowerInNetwork(this.isPowered());
    }

    @Override
    default AABB getInteractBox()
    {
        return DEFAULT_NODE_BOX;
    }

    @Override
    default void updatePowerInNetwork(boolean powered)
    {
        int maxSearchDepth = Config.SERVER.electricity.maximumDaisyChain.get();

        // First find all the nodes that we can power
        Set<IElectricNode> availableNodes = new HashSet<>();
        IElectricNode.searchNodes(this, availableNodes, maxSearchDepth, node -> !node.isSource());

        // Removes available nodes that already match the target power state
        availableNodes.removeIf(node -> node.isPowered() == powered);

        // Next, search for power sources in the network. We double the search depth since the furthest
        // power source could be connected to the furthest node we can find from this lightswitch.
        Set<IElectricNode> sourceNodes = new HashSet<>();
        IElectricNode.searchNodes(this, sourceNodes, maxSearchDepth * 2, node -> node != this);
        sourceNodes.removeIf(node -> !node.isSource() || !node.isPowered());

        // Find nodes each source is currently powering and remove them from the available nodes
        sourceNodes.forEach(source -> {
            Set<IElectricNode> foundNodes = new HashSet<>();
            IElectricNode.searchNodes(source, foundNodes, maxSearchDepth, node -> !node.isSource());
            availableNodes.removeAll(foundNodes);
        });

        // The remaining nodes are considered unpowered and need to be updated
        availableNodes.forEach(node -> node.setPowered(powered));
    }
}
