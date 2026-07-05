package com.mrcrayfish.furniture.refurbished.electricity;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class Connection
{
    private static final int DEFAULT_COLOUR = 0xFFFFFFFF;
    private static final int POWERED_COLOUR = 0xFFFFDA4C;
    private static final int CROSSING_ZONE_COLOUR = 0xFFC33636;

    private final Node a;
    private final Node b;

    // TODO Migrate to a relative system instead of absolute
    private Connection(BlockPos a, BlockPos b)
    {
        // Ensures connections equal even if the params are switched
        int c = a.compareTo(b);
        this.a = new Node(c > 0 ? a : b);
        this.b = new Node(c > 0 ? b : a);
    }

    /**
     * @return The block position of the first node
     */
    public BlockPos getPosA()
    {
        return this.a.pos;
    }

    /**
     * @return The block position of the second node
     */
    public BlockPos getPosB()
    {
        return this.b.pos;
    }

    /**
     * Gets the electricity node instance of the first node
     *
     * @param level the level which the connection exists
     * @return An electricity node instance or null if connection node is invalid/undetermined status.
     */
    @Nullable
    public IElectricityNode getNodeA(Level level)
    {
        return this.a.getElectricNode(level);
    }

    /**
     * Gets the electricity node instance of the second node
     *
     * @param level the level which the connection exists
     * @return An electricity node instance or null if connection node is invalid/undetermined status.
     */
    @Nullable
    public IElectricityNode getNodeB(Level level)
    {
        return this.b.getElectricNode(level);
    }

    /**
     * Determines if this connection is connected. A connection is considered connected when
     * the nodes in this connection are both valid.
     *
     * @param level the level which the connection exists
     * @return True if connected
     */
    public boolean isConnected(Level level)
    {
        return this.a.isValid(level) && this.b.isValid(level);
    }

    /**
     * Determines if this connection is powered. A connection is considered powered if the
     * nodes this connection is linking together are both powered.
     *
     * @param level the level which the connection exists
     * @return True if the nodes of this connection are both powered
     */
    public boolean isPowered(Level level)
    {
        IElectricityNode a = this.a.getElectricNode(level);
        IElectricityNode b = this.b.getElectricNode(level);
        if(a == null || b == null)
            return false;
        if(!a.canPowerTraverseNode() && !b.canPowerTraverseNode())
        {
            return a.isNodePowered() && a.isSourceNode() || b.isNodePowered() && b.isSourceNode();
        }
        return a.isNodePowered() && b.isNodePowered();
    }

    /**
     * Determines if this connection is crossing the border of a powerable zone. A connection is
     * crossing a powerable zone border if either the start or end node is outside the powerable
     * zone of the opposing node's power sources.
     *
     * @param level the level where the connection is present
     * @return True if crossing a powerable zone
     */
    public boolean isCrossingPowerableZone(Level level)
    {
        IElectricityNode a = this.a.getElectricNode(level);
        IElectricityNode b = this.b.getElectricNode(level);
        if(a != null && b != null)
        {
            Set<BlockPos> aPowerSources = a.getPowerSources();
            Set<BlockPos> bPowerSources = b.getPowerSources();
            if(!aPowerSources.equals(bPowerSources))
            {
                Vec3 aPos = Vec3.atCenterOf(this.a.pos);
                Vec3 bPos = Vec3.atCenterOf(this.b.pos);
                for(BlockPos source : Sets.symmetricDifference(aPowerSources, bPowerSources))
                {
                    AABB box = ISourceNode.createPowerableZone(level, source);
                    if(!box.contains(aPos) || !box.contains(bPos))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets the electricity node at the end of the connection
     *
     * @param node the electricity node at the start of the connection
     * @return the other electricity node or empty optional
     */
    @Nullable
    public IElectricityNode getOtherNode(IElectricityNode node)
    {
        if(this.a.pos.equals(node.getNodePosition()))
        {
            return this.b.getElectricNode(node.getNodeLevel());
        }
        else if(this.b.pos.equals(node.getNodePosition()))
        {
            return this.a.getElectricNode(node.getNodeLevel());
        }
        return null;
    }

    @Nullable
    public BlockPos getOtherPos(BlockPos pos)
    {
        if(this.a.pos.equals(pos))
        {
            return this.b.pos;
        }
        else if(this.b.pos.equals(pos))
        {
            return this.a.pos;
        }
        return null;
    }

    public int getColour(Level level)
    {
        if(this.isCrossingPowerableZone(level))
        {
            return CROSSING_ZONE_COLOUR;
        }
        if(this.isPowered(level))
        {
            return POWERED_COLOUR;
        }
        return DEFAULT_COLOUR;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        Connection other = (Connection) obj;
        return this.a.equals(other.a) && this.b.equals(other.b);
    }

    @Override
    public int hashCode()
    {
        int result = this.a.hashCode();
        result = 31 * result + this.b.hashCode();
        return result;
    }

    /**
     * Creates a new Connection with the given block positions. This does not create an actual
     * connection between the two positions, it just represents the link.
     *
     * @param a the block position of the first node
     * @param b the block position of the second node
     * @return a connection instance
     */
    public static Connection of(BlockPos a, BlockPos b)
    {
        return new Connection(a, b);
    }

    private static class Node
    {
        private final BlockPos pos;
        private WeakReference<IElectricityNode> ref;
        private Status status = Status.ACTIVE;

        private Node(BlockPos pos)
        {
            this.pos = pos;
            this.ref = new WeakReference<>(null);
        }

        /**
         * Determines if this connection node is valid.
         *
         * @param level the level where the connection exists
         * @return True if the connection node status is active or undetermined
         */
        private boolean isValid(Level level)
        {
            this.updateStatus(level);
            return this.status.valid;
        }

        /**
         * Gets the electricity node instance of this connection node
         *
         * @param level the level where the connection exists
         * @return An electricity node instance or null if invalid/undetermined status
         */
        @Nullable
        private IElectricityNode getElectricNode(Level level)
        {
            this.updateStatus(level);
            return this.ref.get();
        }

        /**
         * Updates the status of this connection node
         *
         * @param level the level where the connection exists
         */
        private void updateStatus(Level level)
        {
            IElectricityNode node = this.ref.get();
            if(node != null && node.isNodeValid())
            {
                this.status = Status.ACTIVE;
                return;
            }

            if(level.isLoaded(this.pos))
            {
                LevelChunk chunk = level.getChunkAt(this.pos);
                // Get BE from chunk BE map since LevelChunk#getBlockEntity runs logic we don't want to execute
                //noinspection ConstantValue
                if(chunk != null && chunk.getBlockEntities().get(this.pos) instanceof IElectricityNode found && found.isNodeValid())
                {
                    this.ref = new WeakReference<>(found);
                    this.status = Status.ACTIVE;
                }
                else
                {
                    this.ref.clear();
                    this.status = Status.INVALID;
                }
            }
            else
            {
                this.ref.clear();
                this.status = Status.UNDETERMINED;
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if(obj == null || this.getClass() != obj.getClass()) return false;
            Node other = (Node) obj;
            return this.pos.equals(other.pos);
        }

        @Override
        public int hashCode()
        {
            return this.pos.hashCode();
        }

        public enum Status
        {
            /**
             * An active connection node means that an electricity node exists at the block position.
             */
            ACTIVE(true),

            /**
             * An undetermined connection node means that it was not possible to access to the
             * electricity node in the level, but it may still exist due to being in an unloaded chunk.
             */
            UNDETERMINED(true),

            /**
             * An invalid connection node means that no electricity node exists at the block position.
             */
            INVALID(false);

            final boolean valid;

            Status(boolean valid)
            {
                this.valid = valid;
            }
        }
    }
}
