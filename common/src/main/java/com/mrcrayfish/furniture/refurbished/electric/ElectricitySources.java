package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.saveddata.SavedData;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class ElectricitySources extends SavedData
{
    private static final String STORAGE_ID = Utils.resource("electricity_network").toString();

    public static ElectricitySources get(ServerLevel level)
    {
        return level.getDataStorage().computeIfAbsent(tag -> new ElectricitySources(level, tag), () -> new ElectricitySources(level), STORAGE_ID);
    }

    private final ServerLevel level;
    private final Set<BlockPos> sources = new HashSet<>();
    private final Map<BlockPos, WeakReference<ISourceNode>> cache = new HashMap<>();

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
        this.sources.add(node.getPosition());
    }

    public void levelTick()
    {
        Iterator<BlockPos> it = this.sources.iterator();
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
        WeakReference<ISourceNode> sourceRef = this.cache.get(pos);
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
                this.cache.put(pos, new WeakReference<>(node));
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
