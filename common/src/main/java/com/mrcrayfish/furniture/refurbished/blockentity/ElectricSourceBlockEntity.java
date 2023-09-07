package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricSourceBlockEntity extends ElectricBlockEntity
{
    public ElectricSourceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public boolean isSource()
    {
        return true;
    }

    @Override
    public void onDestroyed()
    {
        this.updatePowerInNetwork(false);
        super.onDestroyed();
    }

    @Override
    protected void onConnectedTo(IElectricNode other)
    {
        if(other.isSource())
            return;

        if(!this.isPowered())
            return;

        this.updatePowerInNetwork(true);
    }

    @Override
    public void updatePower()
    {
        if(this.isPowered())
        {
            this.updatePowerInNetwork(true);
        }
    }

    protected void updatePowerInNetwork(boolean powered)
    {
        // First find all the nodes that we can power
        Set<IElectricNode> availableNodes = new HashSet<>();
        this.searchNode(this, availableNodes, MAX_SEARCH_DEPTH, node -> !node.isSource());

        // Removes available nodes that already match the target power state
        availableNodes.removeIf(node -> node.isPowered() == powered);

        // Next, search for power sources in the network. We double the search depth since the furthest
        // power source could be connected to the furthest node we can find from this lightswitch.
        Set<IElectricNode> sourceNodes = new HashSet<>();
        this.searchNode(this, sourceNodes, MAX_SEARCH_DEPTH * 2, node -> node != this);
        sourceNodes.removeIf(node -> !node.isSource() || !node.isPowered());

        // Find nodes each source is currently powering and remove them from the available nodes
        sourceNodes.forEach(source -> {
            Set<IElectricNode> foundNodes = new HashSet<>();
            this.searchNode(source, foundNodes, MAX_SEARCH_DEPTH, node -> !node.isSource());
            availableNodes.removeAll(foundNodes);
        });

        // The remaining nodes are considered unpowered and need to be updated
        availableNodes.forEach(node -> node.setPowered(powered));
    }
}
