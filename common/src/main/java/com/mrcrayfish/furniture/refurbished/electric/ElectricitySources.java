package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.commons.lang3.mutable.MutableObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: MrCrayfish
 */
public class ElectricitySources extends SavedData
{
    private static final String STORAGE_ID = "refurbished_furniture_electricity_sources";

    public static ElectricitySources get(ServerLevel level)
    {
        return level.getDataStorage().computeIfAbsent(tag -> new ElectricitySources(level, tag), () -> new ElectricitySources(level), STORAGE_ID);
    }

    private final ServerLevel level;
    private final Map<BlockPos, MutableObject<WeakReference<ISourceNode>>> sources = new ConcurrentHashMap<>();

    private ElectricitySources(ServerLevel level)
    {
        this(level, new CompoundTag());
    }

    private ElectricitySources(ServerLevel level, CompoundTag tag)
    {
        this.load(tag);
        this.level = level;
    }

    public void add(ISourceNode node)
    {
        this.sources.put(node.getPosition(), new MutableObject<>(new WeakReference<>(node)));
    }

    public void levelTick()
    {
        Iterator<BlockPos> it = this.sources.keySet().iterator();
        while(it.hasNext())
        {
            BlockPos pos = it.next();
            if(!this.level.isLoaded(pos))
                continue;

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

    private ISourceNode getSourceNode(BlockPos pos)
    {
        MutableObject<WeakReference<ISourceNode>> mutableObj = this.sources.get(pos);
        WeakReference<ISourceNode> sourceRef = mutableObj.getValue();
        if(sourceRef != null)
        {
            ISourceNode node = sourceRef.get();
            if(node != null && node.isValid())
            {
                return node;
            }
        }

        if(this.level.isLoaded(pos))
        {
            BlockEntity entity = this.level.getBlockEntity(pos);
            if(entity instanceof ISourceNode node && node.isValid())
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
