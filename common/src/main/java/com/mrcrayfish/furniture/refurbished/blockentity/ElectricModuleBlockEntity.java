package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricModuleBlockEntity extends ElectricBlockEntity
{
    public ElectricModuleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public boolean isSource()
    {
        return false;
    }

    @Override
    public void onDestroyed()
    {
        this.updatePowerInNetwork(true);
        super.onDestroyed();
    }

    @Override
    protected void onConnectedTo(IElectricNode other)
    {
        // Don't handle power update is other is a source
        if(other.isSource())
            return;

        // If connected node is not powered, don't bother performing an update
        if(!this.isPowered() && !other.isPowered())
            return;

        this.updatePowerInNetwork(false);
    }

    protected void updatePowerInNetwork(boolean removed)
    {
        // First find all the available nodes that we can connect to.
        // If this electric node is being removed, prevent it from being searched
        Set<IElectricNode> availableNodes = new HashSet<>();
        this.searchNode(this, availableNodes, MAX_SEARCH_DEPTH, node -> !node.isSource() && (!removed || node != this));

        // Removes available nodes based on if this node is being removed. If being removed, the
        // power is potentially being disconnected so the only nodes to deal with are ones that are
        // powered. If the node is not being removed, only deal with unpowered nodes since a new
        // connection may have been made to this node and the unpowered nodes may become powered.
        availableNodes.removeIf(node -> node.isPowered() != removed);

        // Next, search for powered electric source nodes in the network.
        // Again if this electric node is being removed, prevent it from being searched
        Set<IElectricNode> sourceNodes = new HashSet<>();
        this.searchNode(this, sourceNodes, MAX_SEARCH_DEPTH * 2, node -> (!removed || node != this));
        sourceNodes.removeIf(node -> !node.isSource() || !node.isPowered());

        // For each powered source nodes, find all the nodes that it can connect to. Again, if this
        // node is being removed, ensure that it is ignored as a valid connection path.
        sourceNodes.forEach(source -> {
            Set<IElectricNode> foundNodes = new HashSet<>();
            this.searchNode(source, foundNodes, MAX_SEARCH_DEPTH, node -> !node.isSource() && (!removed || node != this));
            // For the nodes found from each powered source node, remove the nodes based on the removed flag:
            // 1. If true, we remove available nodes that are found by powered source nodes.
            // 2. If false, we remove available nodes that don't reach a power source
            availableNodes.removeIf(node -> removed == foundNodes.contains(node));
        });

        // The nodes remaining in the available nodes are the one that need their power updated.
        // In the case the removed flag is true, the power state of each remaining node is turned off.
        // Otherwise, the remaining nodes have their power state turned on. Nodes that were removed
        // during the above logic are already in their correct power state and didn't need an update.
        availableNodes.forEach(node -> node.setPowered(!removed));

        // Further reading, this method also ensures that an electric node won't receive update to
        // its power that matches its current power. In other words, a node's power state will not
        // be set to true if it is already true. This comes in handy when you want to play sounds
        // when an electric module is turned on or off.
    }
}
