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

    @Override
    default boolean isSource()
    {
        return true;
    }

    @Override
    default boolean canPowerTraverse()
    {
        return false;
    }

    @Override
    default AABB getInteractBox()
    {
        return DEFAULT_NODE_BOX;
    }

    @Override
    default void setReceivingPower(boolean power) {}

    @Override
    default boolean isReceivingPower()
    {
        return false;
    }

    /**
     * An early tick called at the start of the level tick before other block entities are ticked
     */
    default void earlyLevelTick()
    {
        // TODO figure out way to cache this instead of searching again every tick
        if(this.isPowered() && !this.isOverloaded())
        {
            //long time = Util.getNanos();
            List<IElectricityNode> nodes = IElectricityNode.searchNodes(this, Config.SERVER.electricity.maximumDaisyChain.get(), Config.SERVER.electricity.maximumNodesInNetwork.get(), false, node -> !node.isSource() && node.canPowerTraverse(), node -> true);
            if(nodes.size() > Config.SERVER.electricity.maximumNodesInNetwork.get())
            {
                this.setOverloaded(true);
                this.onOverloaded();
                return;
            }
            nodes.forEach(node -> node.setReceivingPower(true));
            //long searchTime = Util.getNanos() - time;
            //System.out.println("Search time: " + searchTime);
        }
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
}
