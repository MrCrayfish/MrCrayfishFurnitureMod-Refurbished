package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.google.common.collect.Sets;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.ISourceNode;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PowerableArea
{
    private final Set<BlockPos> sourcePositions = new HashSet<>();
    private final Set<BlockPos> lastSourcePositions = new HashSet<>();
    private VoxelShape cachedPowerableAreaShape;

    PowerableArea() {}

    /**
     *
     * @return
     */
    public boolean exists()
    {
        return !this.sourcePositions.isEmpty();
    }

    /**
     * @param level
     * @param start
     * @param end
     * @return
     */
    public boolean containsLine(Level level, Vec3 start, Vec3 end)
    {
        for(BlockPos pos : this.sourcePositions)
        {
            AABB box = ISourceNode.createPowerableZone(level, pos);
            if(box.contains(start) && box.contains(end))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and updates the source nodes that are connected to either the node we are linking or
     * node we are currently looking at.
     *
     * @param level
     * @param context
     */
    public void updatePowerSources(Level level, WrenchContext context)
    {
        this.sourcePositions.clear();

        // Look for power sources by the scanning the selectedNode or targetingNode node
        if(context.selectedNode() != null)
        {
            this.scanNetworkForPowerSources(context.selectedNode(), this.sourcePositions);
            if(context.targetingNode() != null && this.sourcePositions.isEmpty())
            {
                this.scanNetworkForPowerSources(context.targetingNode(), this.sourcePositions);
            }
            return;
        }

        // Look for power sources by scanning the hovered connection
        Connection connection = context.hoveredConnection();
        if(connection != null && !connection.isCrossingPowerableZone(level))
        {
            this.scanNetworkForPowerSources(level, connection, this.sourcePositions);
        }
    }

    /**
     * Searches the electricity network starting from a Connection and finds the first source node
     * that can provide power. The block position of the source node is then added to the found set.
     *
     * @param level the level the connection is located
     * @param connection the node to selectedNode the search or null
     * @param found a set that holds found block positions of source nodes
     */
    private void scanNetworkForPowerSources(Level level, Connection connection, Set<BlockPos> found)
    {
        IElectricityNode a = connection.getNodeA(level);
        IElectricityNode b = connection.getNodeB(level);
        if(a != null && b != null)
        {
            Set<BlockPos> delta = Sets.symmetricDifference(a.getPowerSources(), b.getPowerSources());
            if(!delta.isEmpty())
            {
                found.add(List.copyOf(delta).getFirst());
            }
        }
    }

    /**
     * Searches the electricity network starting from the provided selectedNode node and finds all the
     * source nodes that can provide power to the given selectedNode node. The block position of the source
     * node is then added to the found set.
     *
     * @param start the node to selectedNode the search or null
     * @param found a set that holds found block positions of source nodes
     */
    private void scanNetworkForPowerSources(IElectricityNode start, Set<BlockPos> found)
    {
        // Search network for all possible source nodes that can provide power to the selectedNode node
        if(!start.isSourceNode())
        {
            int searchLimit = Config.SERVER.electricity.maximumNodesInNetwork.get();
            IElectricityNode.searchNodes(start, searchLimit, true, node -> !node.isSourceNode(), IElectricityNode::isSourceNode).forEach(node -> {
                found.add(node.getNodePosition());
            });
            return;
        }
        found.add(start.getNodePosition());
    }

    /**
     * @return The powerable area shape (or cached version), otherwise null if no powerable area.
     */
    @Nullable
    public VoxelShape getPowerableAreaShape()
    {
        Minecraft mc = Minecraft.getInstance();
        if(this.sourcePositions.isEmpty() || mc.level == null)
            return null;

        // Return cached shape if same as last positions
        if(this.lastSourcePositions.equals(this.sourcePositions))
            return this.cachedPowerableAreaShape;

        // Creates the powerable area shape
        this.sourcePositions.stream().map(pos -> {
            return ISourceNode.createPowerableZone(mc.level, pos);
        }).map(aabb -> {
            VoxelShape shape1 = Shapes.create(aabb);
            VoxelShape shape2 = Shapes.create(aabb.inflate(0.001));
            return Pair.of(shape1, shape2);
        }).reduce((p1, p2) -> {
            VoxelShape shape1 = Shapes.joinUnoptimized(p1.first(), p2.first(), BooleanOp.OR);
            VoxelShape shape2 = Shapes.joinUnoptimized(p1.second(), p2.second(), BooleanOp.OR);
            return Pair.of(shape1, shape2);
        }).map(pair -> {
            return Shapes.joinUnoptimized(pair.first(), pair.second(), BooleanOp.ONLY_SECOND);
        }).ifPresent(shape -> {
            this.cachedPowerableAreaShape = shape;
        });

        // Finally remember the positions and return the shape
        this.lastSourcePositions.clear();
        this.lastSourcePositions.addAll(this.sourcePositions);
        return this.cachedPowerableAreaShape;
    }

    /**
     * Invalidates the powerable area shape. This will clear any caches.
     */
    public void invalidate()
    {
        this.sourcePositions.clear();
        this.lastSourcePositions.clear();
        this.cachedPowerableAreaShape = null;
    }
}
