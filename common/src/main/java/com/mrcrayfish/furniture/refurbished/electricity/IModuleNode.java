package com.mrcrayfish.furniture.refurbished.electricity;

import com.mrcrayfish.furniture.refurbished.Config;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Author: MrCrayfish
 */
public interface IModuleNode extends IElectricityNode
{
    AABB DEFAULT_NODE_BOX = new AABB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);

    @Override
    default boolean isSourceNode()
    {
        return false;
    }

    @Override
    default AABB getNodeInteractBox()
    {
        return DEFAULT_NODE_BOX;
    }

    /**
     * A default implementation of updating the powered state of this module node. This depends
     * on if this module node is receiving power from a source node. If it's receiving power
     * and not powered, it will become powered. While if it's not receiving power and is currently
     * powered, it will be powered off.
     */
    default void updateNodePoweredState()
    {
        // Cheat mode for electricity. Free power
        if(Config.SERVER.electricity.cheats.everythingIsPowered.get())
        {
            if(!this.isNodePowered())
            {
                this.setNodePowered(true);
            }
            return;
        }

        if(!this.isNodeReceivingPower())
        {
            if(this.isNodePowered())
            {
                this.setNodePowered(false);
            }
        }
        else if(!this.isNodePowered())
        {
            this.setNodePowered(true);
        }
    }

    @Override
    default void earlyNodeTick(Level level)
    {
        this.getPowerSources().clear();
    }

    default void moduleTick(Level level)
    {
        if(!level.isClientSide)
        {
            this.updateNodePoweredState();
            this.setNodeReceivingPower(false);
        }
    }
}
