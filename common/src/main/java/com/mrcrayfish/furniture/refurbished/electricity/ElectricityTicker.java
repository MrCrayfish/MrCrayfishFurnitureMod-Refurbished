package com.mrcrayfish.furniture.refurbished.electricity;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.Nullable;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public final class ElectricityTicker
{
    private final Level level;
    private final Map<BlockPos, WeakReference<IModuleNode>> modules = new ConcurrentHashMap<>();
    private final Map<BlockPos, WeakReference<ISourceNode>> sources = new ConcurrentHashMap<>();

    public ElectricityTicker(Level level)
    {
        this.level = level;
    }

    /**
     * Adds an electricity node to be ticked
     * @param node a module node instance
     */
    public void addElectricityNode(IElectricityNode node)
    {
        if(node instanceof IModuleNode module)
        {
            this.modules.put(node.getNodePosition(), new WeakReference<>(module));
        }
        else if(node instanceof ISourceNode source)
        {
            this.sources.put(node.getNodePosition(), new WeakReference<>(source));
        }
    }

    /**
     * Called before block entities. This method ticks all source nodes that are currently loaded.
     * Electricity nodes that no longer exist or are unloaded are automatically removed.
     */
    public void earlyTick()
    {
        this.tickSet(this.modules, this::getModuleNode, IElectricityNode::earlyNodeTick);
        this.tickSet(this.sources, this::getSourceNode, IElectricityNode::earlyNodeTick);
    }

    /**
     * A standard tick at the same time block entities are ticked
     */
    public void tick()
    {
        this.tickSet(this.modules, this::getModuleNode, IModuleNode::moduleTick);
    }

    private <T extends IElectricityNode> void tickSet(Map<BlockPos, WeakReference<T>> nodes, Function<BlockPos, T> getter, BiConsumer<T, Level> ticker)
    {
        Iterator<BlockPos> it = nodes.keySet().iterator();
        while(it.hasNext())
        {
            BlockPos pos = it.next();
            T node = getter.apply(pos);
            if(node == null)
            {
                Constants.LOG.debug("Stopping ticking node at {}", pos);
                it.remove();
            }
            else if(this.level.shouldTickBlocksAt(pos))
            {
                ticker.accept(node, this.level);
            }
        }
    }

    /**
     * Attempts to get the electricity node at the given block position. This method will cache and
     * keep a reference of the electricity node if found, which means future calls at the same
     * position will return from the reference as this is quicker than polling from the level.
     *
     * @param pos the block position
     * @return an electricity node or null if not found
     */
    @Nullable
    private <T extends IElectricityNode> T getElectricityNode(Map<BlockPos, WeakReference<T>> map, BlockPos pos, Function<BlockEntity, T> caster)
    {
        WeakReference<T> sourceRef = map.get(pos);
        if(sourceRef != null)
        {
            T node = sourceRef.get();
            if(node == null && this.level.isLoaded(pos))
            {
                node = caster.apply(this.level.getBlockEntity(pos));
                if(node != null)
                {
                    map.put(pos, new WeakReference<>(node));
                }
            }
            if(node != null && !node.getNodeOwner().isRemoved())
            {
                return node;
            }
        }
        return null;
    }

    /**
     * Gets the module node at the given block position or null
     *
     * @param pos the block position of the node
     * @return the module node or null
     */
    @Nullable
    private IModuleNode getModuleNode(BlockPos pos)
    {
        return this.getElectricityNode(this.modules, pos, entity -> {
            return entity instanceof IModuleNode node ? node : null;
        });
    }

    /**
     * Gets the source node at the given block position or null
     *
     * @param pos the block position of the node
     * @return the module node or null
     */
    @Nullable
    private ISourceNode getSourceNode(BlockPos pos)
    {
        // Try and get the source node from the reference
        return this.getElectricityNode(this.sources, pos, entity -> {
            return entity instanceof ISourceNode node ? node : null;
        });
    }

    public static ElectricityTicker get(Level level)
    {
        return ((ElectricityTicker.Access) level).refurbishedFurniture$GetElectricityTicker();
    }

    public interface Access
    {
        ElectricityTicker refurbishedFurniture$GetElectricityTicker();
    }
}
