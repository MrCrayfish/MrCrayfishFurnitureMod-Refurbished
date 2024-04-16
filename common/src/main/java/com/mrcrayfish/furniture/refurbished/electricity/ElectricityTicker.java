package com.mrcrayfish.furniture.refurbished.electricity;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.commons.lang3.mutable.MutableObject;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: MrCrayfish
 */
public class ElectricityTicker extends SavedData
{
    private static final String STORAGE_ID = "refurbished_furniture_electricity_sources";

    /**
     * Creates or gets the electricity ticker for the given server level
     *
     * @param level a server level instance
     * @return an electricity ticker instance
     */
    public static ElectricityTicker get(ServerLevel level)
    {
        return level.getDataStorage().computeIfAbsent(tag -> new ElectricityTicker(level, tag), () -> new ElectricityTicker(level), STORAGE_ID);
    }

    private final ServerLevel level;
    private final Map<BlockPos, MutableObject<WeakReference<ISourceNode>>> sources = new ConcurrentHashMap<>();

    private ElectricityTicker(ServerLevel level)
    {
        this(level, new CompoundTag());
    }

    private ElectricityTicker(ServerLevel level, CompoundTag tag)
    {
        this.load(tag);
        this.level = level;
    }

    /**
     * Adds a source node to be ticked.
     * @param node a source node instance.
     */
    public void addSourceNode(ISourceNode node)
    {
        this.sources.put(node.getNodePosition(), new MutableObject<>(new WeakReference<>(node)));
    }

    /**
     * Called before block entities. This method ticks all source nodes that are currently loaded.
     * Sources nodes that no longer exist or are unloaded are automatically removed.
     */
    public void startLevelTick()
    {
        Iterator<BlockPos> it = this.sources.keySet().iterator();
        while(it.hasNext())
        {
            BlockPos pos = it.next();
            ISourceNode node = this.getSourceNode(pos);
            if(node == null)
            {
                Constants.LOG.debug("Removed electric source at {}", pos);
                it.remove();
                this.setDirty();
                continue;
            }

            node.earlyLevelTick();
        }
    }

    /**
     * Attempts to get the source node at the given block position. This method will cache and keep
     * a reference of the source node if found, which means future calls at the same position
     * will return from the reference as this is quicker than polling from the level.
     *
     * @param pos the block position
     * @return a source node or null if not found
     */
    @Nullable
    private ISourceNode getSourceNode(BlockPos pos)
    {
        MutableObject<WeakReference<ISourceNode>> mutableObj = this.sources.get(pos);
        WeakReference<ISourceNode> sourceRef = mutableObj.getValue();
        if(sourceRef != null)
        {
            ISourceNode node = sourceRef.get();
            if(node != null && node.isNodeValid())
            {
                return node;
            }
        }

        if(this.level.isLoaded(pos))
        {
            BlockEntity entity = this.level.getBlockEntity(pos);
            if(entity instanceof ISourceNode node && node.isNodeValid())
            {
                mutableObj.setValue(new WeakReference<>(node));
                return node;
            }
        }

        return null;
    }

    private void load(CompoundTag tag) {}

    @Override
    public CompoundTag save(CompoundTag tag)
    {
        return tag;
    }
}
