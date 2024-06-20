package com.mrcrayfish.furniture.refurbished.electricity;

import com.mrcrayfish.furniture.refurbished.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    void setNodeOverloaded(boolean overloaded);

    /**
     * @return True if this source node is currently overloaded. An overloaded source node is when
     * there are too many module nodes in the network to power.
     */
    boolean isNodeOverloaded();

    /**
     * Called when this source node becomes overloaded.
     */
    default void onNodeOverloaded() {}

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
    default void setNodeReceivingPower(boolean state) {}

    @Override
    default boolean isNodeReceivingPower()
    {
        return false;
    }

    @Override
    default Set<BlockPos> getPowerSources()
    {
        return new HashSet<>(Collections.singleton(this.getNodePosition()));
    }

    @Override
    default boolean isNodeInPowerableNetwork(BlockPos source)
    {
        return true; // Source nodes are always in a network
    }

    @Override
    default void readNodeNbt(CompoundTag tag)
    {
        IElectricityNode.super.readNodeNbt(tag);
        this.setNodeOverloaded(tag.getBoolean("Overloaded"));
    }

    @Override
    default void writeNodeNbt(CompoundTag tag)
    {
        IElectricityNode.super.writeNodeNbt(tag);
        tag.putBoolean("Overloaded", this.isNodeOverloaded());
    }

    /**
     * An early tick called at the start of the level tick before other block entities are ticked
     */
    @Override
    default void startLevelTick(Level level)
    {
        if(!level.isClientSide())
        {
            if(this.isNodePowered() && !this.isNodeOverloaded())
            {
                //long time = Util.getNanos();
                // TODO figure out way to cache this instead of searching again every tick
                NodeSearchResult result = this.searchNodeNetwork(false);
                if(result.overloaded())
                {
                    this.setNodeOverloaded(true);
                    this.onNodeOverloaded();
                    return;
                }
                result.nodes().forEach(node -> node.setNodeReceivingPower(true));
                //long searchTime = Util.getNanos() - time;
                //System.out.println("Search time: " + searchTime);
            }
        }
        else
        {
            NodeSearchResult result = this.searchNodeNetwork(true);
            result.nodes().forEach(node -> node.getPowerSources().add(this.getNodePosition()));
        }
    }

    /**
     * The default search algorithm for locating electricity nodes. See {@link IElectricityNode#searchNodes}
     * for more information on how searching works. Changing the default
     *
     * @return a result of all found nodes and if it's overloaded
     */
    default NodeSearchResult searchNodeNetwork(boolean cancelAtLimit)
    {
        List<IElectricityNode> nodes = IElectricityNode.searchNodes(this, this.getMaxPowerableNodes(), cancelAtLimit, node -> !node.isSourceNode() && node.canPowerTraverseNode(), node -> !node.isSourceNode());
        boolean overloaded = nodes.size() > this.getMaxPowerableNodes();
        return new NodeSearchResult(overloaded, nodes);
    }

    /**
     * Creates an AABB of the powerable zone for source nodes.
     *
     * @param level the level where the powerable zone will be located
     * @param pos   the block position of the source node
     * @return an AABB of the powerable zone
     */
    static AABB createPowerableZone(Level level, BlockPos pos)
    {
        double radius = Config.SERVER.electricity.powerableAreaRadius.get();
        double minX = pos.getX() - radius;
        double minY = pos.getY() - radius;
        double minZ = pos.getZ() - radius;
        double maxX = pos.getX() + radius + 1;
        double maxY = pos.getY() + radius + 1;
        double maxZ = pos.getZ() + radius + 1;
        minY = Math.max(minY, level.getMinBuildHeight());
        maxY = Math.min(maxY, level.getMaxBuildHeight());
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
