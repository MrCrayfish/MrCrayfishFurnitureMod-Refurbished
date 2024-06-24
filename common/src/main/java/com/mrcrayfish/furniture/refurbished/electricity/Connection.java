package com.mrcrayfish.furniture.refurbished.electricity;

import com.google.common.base.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class Connection
{
    private final Node a;
    private final Node b;
    private Integer hash;

    private Connection(BlockPos a, BlockPos b)
    {
        this.a = new Node(a);
        this.b = new Node(b);
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
        return a != null && a.isNodePowered() && b != null && b.isNodePowered();
    }

    /**
     *
     * @param level
     * @return
     */
    public boolean isPowerable(Level level)
    {
        IElectricityNode a = this.a.getElectricNode(level);
        IElectricityNode b = this.b.getElectricNode(level);
        return a != null && a.isNodeInPowerableNetwork() && b != null && b.isNodeInPowerableNetwork();
    }

    /**
     *
     * @param level
     * @return
     */
    public boolean isCrossingPowerableZone(Level level)
    {
        IElectricityNode a = this.a.getElectricNode(level);
        IElectricityNode b = this.b.getElectricNode(level);
        return (a != null && a.isNodeInPowerableNetwork()) ^ (b != null && b.isNodeInPowerableNetwork());
    }

    public Optional<BlockPos> getOtherPos(BlockPos pos)
    {
        if(this.a.pos.equals(pos))
        {
            return Optional.of(this.b.pos);
        }
        else if(this.b.pos.equals(pos))
        {
            return Optional.of(this.a.pos);
        }
        return Optional.empty();
    }

    /**
     * Gets the electricity node at the end of the connection
     *
     * @param node the electricity node at the start of the connection
     * @return the other electricity node or empty optional
     */
    public Optional<IElectricityNode> getOtherNode(IElectricityNode node)
    {
        if(this.a.pos.equals(node.getNodePosition()))
        {
            return Optional.ofNullable(this.b.getElectricNode(node.getNodeLevel()));
        }
        else if(this.b.pos.equals(node.getNodePosition()))
        {
            return Optional.ofNullable(this.a.getElectricNode(node.getNodeLevel()));
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Connection other = (Connection) o;
        if(this.getMinPos().equals(other.getMinPos()))
        {
            return this.getMaxPos().equals(other.getMaxPos());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        this.calculateHash();
        return this.hash;
    }

    /**
     * Calculates the hash for this connection. The calculated hash will be exactly the same even if
     * the connection nodes are switched.
     */
    private void calculateHash()
    {
        if(this.hash == null)
        {
            this.hash = Objects.hashCode(this.getMinPos(), this.getMaxPos());
        }
    }

    /**
     * @return The minimum block position in the connection
     */
    private BlockPos getMinPos()
    {
        int c = this.a.pos.compareTo(this.b.pos);
        return c > 0 ? this.a.pos : this.b.pos;
    }

    /**
     * @return The maximum block position in the connection
     */
    private BlockPos getMaxPos()
    {
        int c = this.a.pos.compareTo(this.b.pos);
        return c > 0 ? this.b.pos : this.a.pos;
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
        public boolean isValid(Level level)
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
        public IElectricityNode getElectricNode(Level level)
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
                if(level.getBlockEntity(this.pos) instanceof IElectricityNode found)
                {
                    this.ref = new WeakReference<>(found);
                    this.status = Status.ACTIVE;
                }
                else
                {
                    this.status = Status.INVALID;
                }
            }
            else
            {
                this.status = Status.UNDETERMINED;
            }
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
