package com.mrcrayfish.furniture.refurbished.electricity;

import com.mrcrayfish.furniture.refurbished.blockentity.ElectricityModuleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Author: MrCrayfish
 */
public interface IModuleNode extends IElectricityNode
{
    AABB DEFAULT_NODE_BOX = new AABB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);

    @Override
    default boolean isSource()
    {
        return false;
    }

    @Override
    default AABB getInteractBox()
    {
        return DEFAULT_NODE_BOX;
    }

    /**
     * A default implementation of updating the powered state of this module node. This depends
     * on if this module node is receiving power from a source node. If it's receiving power
     * and not powered, it will become powered. While if it's not receiving power and is currently
     * powered, it will be powered off.
     */
    default void updatePoweredState()
    {
        if(!this.isReceivingPower())
        {
            if(this.isPowered())
            {
                this.setPowered(false);
            }
        }
        else if(!this.isPowered())
        {
            this.setPowered(true);
        }
    }

    /**
     * Default implementation of an electricity module server tick.
     *
     * @param node the module node to tick
     */
    static void serverTick(Level level, BlockPos pos, BlockState state, IModuleNode node)
    {
        node.updatePoweredState();
        node.setReceivingPower(false);
    }
}
