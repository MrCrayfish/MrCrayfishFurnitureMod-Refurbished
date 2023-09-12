package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.Config;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Set;

/**
 * Author: MrCrayfish
 */
public interface ISourceNode extends IElectricNode
{
    AABB DEFAULT_NODE_BOX = new AABB(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);

    void setOverloaded(boolean overloaded);

    boolean isOverloaded();

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
        if(this.isPowered() && !this.isOverloaded())
        {
            //long time = Util.getNanos();
            Set<IElectricNode> nodes = new ObjectOpenHashSet<>();
            SearchResult result = IElectricNode.searchNodes(this, nodes, Config.SERVER.electricity.maximumDaisyChain.get(), node -> !node.isSource());
            if(result == SearchResult.OVERLOADED)
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
        IElectricNode.super.readNodeNbt(tag);
        this.setOverloaded(tag.getBoolean("Overloaded"));
    }

    @Override
    default void writeNodeNbt(CompoundTag tag)
    {
        IElectricNode.super.writeNodeNbt(tag);
        tag.putBoolean("Overloaded", this.isOverloaded());
    }

    static void register(ISourceNode node, Level level)
    {
        if(level instanceof ServerLevel serverLevel)
        {
            ElectricitySources.get(serverLevel).add(node);
        }
    }
}
