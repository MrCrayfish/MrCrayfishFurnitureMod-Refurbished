package com.mrcrayfish.furniture.refurbished.electric;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class ElectricityNetwork extends SavedData
{
    private static final String STORAGE_ID = Utils.resource("electric_network").toString();
    private static final int MAX_SEARCH_DEPTH = 5; // TODO config value

    public static ElectricityNetwork get(ServerLevel level)
    {
        return null;
        //return level.getDataStorage().computeIfAbsent(tag -> new ElectricityGrid(level, tag), () -> new DeliveryService(level), STORAGE_ID);
    }

    @Nullable
    private final WeakReference<IElectricNode> root;
    private final Map<BlockPos, WeakReference<IElectricNode>> nodes;
    private final Set<Connection> connections;

    public ElectricityNetwork(IElectricNode root)
    {
        this.root = new WeakReference<>(root);
        this.nodes = new HashMap<>();
        this.connections = new HashSet<>();
        //this.recalculateGraph();
    }

    public List<WeakReference<IElectricNode>> getNodes()
    {
        return ImmutableList.copyOf(this.nodes.values());
    }

    public Set<Connection> getConnections()
    {
        return Collections.unmodifiableSet(this.connections);
    }

    public void forEach(Consumer<IElectricNode> consumer)
    {
        this.nodes.values().forEach(nodeRef -> {
            IElectricNode node = nodeRef.get();
            if(node != null) {
                consumer.accept(node);
            }
        });
    }

    /*public void recalculateGraph()
    {
        IElectricNode root = this.root.get();
        if(root != null)
        {
            // Remove network from old nodes
            this.nodes.forEach((pos, nodeRef) -> {
                IElectricNode node = nodeRef.get();
                if(node != null) {
                    node.setNetwork(null);
                }
            });

            // Reset graph
            this.nodes.clear();
            this.connections.clear();

            // Create the graph starting from the root
            Set<IElectricNode> found = new HashSet<>();
            found.add(root);
            this.searchNode(root, found, MAX_SEARCH_DEPTH);
            found.forEach(node -> this.nodes.put(node.getPosition(), new WeakReference<>(node)));
            found.forEach(node -> {
                node.getConnections().forEach(nodeRef -> {
                    IElectricNode other = nodeRef.get();
                    if(other != null && this.isNode(other)) {
                        this.connections.add(Connection.of(node.getPosition(), other.getPosition()));
                    }
                });
                node.setNetwork(this);
            });
        }
    }*/

    public boolean isNode(IElectricNode node)
    {
        return this.nodes.values().stream().anyMatch(nodeRef -> nodeRef.refersTo(node));
    }

    @Override
    public CompoundTag save(CompoundTag var1)
    {
        return null;
    }

}
