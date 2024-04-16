package com.mrcrayfish.furniture.refurbished.electricity;

import com.mrcrayfish.furniture.refurbished.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public interface ISourceNode extends IElectricityNode
{
    // Source nodes have a bigger interaction box and model
    AABB DEFAULT_NODE_BOX = new AABB(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);

    /**
     * Sets the overloaded state of this source node
     *
     * @param overloaded the new state
     */
    void setOverloaded(boolean overloaded);

    /**
     * @return True if this source node is currently overloaded. An overloaded source node is when
     * there are too many module nodes in the network to power.
     */
    boolean isOverloaded();

    /**
     * Called when this source node becomes overloaded.
     */
    default void onOverloaded() {}

    /**
     * @return The maximum electricity nodes this source can supply
     */
    default int getMaxPowerableNodes()
    {
        return Config.SERVER.electricity.maximumNodesInNetwork.get();
    }

    @Override
    default boolean isSourceNode()
    {
        return true;
    }

    @Override
    default boolean canPowerTraverseNode()
    {
        return false;
    }

    @Override
    default AABB getNodeInteractBox()
    {
        return DEFAULT_NODE_BOX;
    }

    @Override
    default void setNodeReceivingPower(boolean power) {}

    @Override
    default boolean isNodeReceivingPower()
    {
        return false;
    }

    @Override
    default void readNodeNbt(CompoundTag tag)
    {
        IElectricityNode.super.readNodeNbt(tag);
        this.setOverloaded(tag.getBoolean("Overloaded"));
    }

    @Override
    default void writeNodeNbt(CompoundTag tag)
    {
        IElectricityNode.super.writeNodeNbt(tag);
        tag.putBoolean("Overloaded", this.isOverloaded());
    }

    /**
     * An early tick called at the start of the level tick before other block entities are ticked
     */
    default void earlyLevelTick()
    {
        // TODO figure out way to cache this instead of searching again every tick
        if(this.isNodePowered() && !this.isOverloaded())
        {
            //long time = Util.getNanos();
            NodeSearchResult result = this.search();
            if(result.overloaded())
            {
                this.setOverloaded(true);
                this.onOverloaded();
                return;
            }
            result.nodes().forEach(node -> node.setNodeReceivingPower(true));
            //long searchTime = Util.getNanos() - time;
            //System.out.println("Search time: " + searchTime);
        }
    }

    /**
     * Registers this source node into the electricity ticker handler
     *
     * @param level the level of this source node
     */
    default void registerTicker(Level level)
    {
        if(level instanceof ServerLevel serverLevel)
        {
            ElectricityTicker.get(serverLevel).addSourceNode(this);
        }
    }

    /**
     * The default search algorithm for locating electricity nodes. See {@link IElectricityNode#searchNodes}
     * for more information on how searching works.
     *
     * @return a result of all found nodes and if it's overloaded
     */
    default NodeSearchResult search()
    {
        List<IElectricityNode> nodes = IElectricityNode.searchNodes(this, Config.SERVER.electricity.maximumDaisyChain.get(), this.getMaxPowerableNodes(), false, node -> !node.isSourceNode() && node.canPowerTraverseNode(), node -> !node.isSourceNode());
        boolean overloaded = nodes.size() > this.getMaxPowerableNodes();
        return new NodeSearchResult(overloaded, nodes);
    }
}
