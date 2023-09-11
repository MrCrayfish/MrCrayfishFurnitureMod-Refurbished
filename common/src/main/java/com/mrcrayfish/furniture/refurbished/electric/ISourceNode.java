package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.Config;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.Set;

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
    default void setReceivingPower(boolean power) {}

    @Override
    default boolean isReceivingPower()
    {
        return false;
    }

    @Override
    default AABB getInteractBox()
    {
        return DEFAULT_NODE_BOX;
    }

    default void earlyLevelTick()
    {
        // TODO figure out way to cache this instead of searching again every tick
        if(this.isPowered())
        {
            Set<IElectricNode> nodes = new HashSet<>();
            IElectricNode.searchNodes(this, nodes, Config.SERVER.electricity.maximumDaisyChain.get(), node -> !node.isSource());
            nodes.forEach(node -> node.setReceivingPower(true));
        }
    }

    static void register(ISourceNode node, Level level)
    {
        if(level instanceof ServerLevel serverLevel)
        {
            ElectricitySources.get(serverLevel).add(node);
        }
    }
}
