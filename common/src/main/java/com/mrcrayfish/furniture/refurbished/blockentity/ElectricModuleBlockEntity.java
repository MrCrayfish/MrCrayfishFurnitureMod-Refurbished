package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

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

    @Override
    public void updatePower()
    {
        this.updatePowerInNetwork(false);
    }

    protected void updatePowerInNetwork(boolean removed)
    {
        // Test to check this node is a valid traversal node. On removed, it shouldn't be.
        Predicate<IElectricNode> includeSelf = node -> (!removed || node != this);
        int maxSearchDepth = Config.SERVER.electricity.maximumDaisyChain.get();

        // First find all the available nodes that we can connect to.
        Set<IElectricNode> availableNodes = new HashSet<>();
        this.searchNode(this, availableNodes, maxSearchDepth, node -> !node.isSource() && includeSelf.test(node));

        // Next, search for powered electric source nodes in the network.
        // Again if this electric node is being removed, prevent it from being searched
        Set<IElectricNode> sourceNodes = new HashSet<>();
        this.searchNode(this, sourceNodes, maxSearchDepth * 2, node -> includeSelf.test(node));
        sourceNodes.removeIf(node -> !node.isSource() || !node.isPowered());

        // For each powered source nodes, find all the nodes that it can connect to. Again, if this
        // node is being removed, ensure that it is ignored as a valid connection path.
        Set<IElectricNode> foundNodes = new HashSet<>();
        sourceNodes.forEach(source -> {
            Set<IElectricNode> searchedNodes = new HashSet<>();
            this.searchNode(source, searchedNodes, maxSearchDepth, node -> !node.isSource() && includeSelf.test(node));
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
}
