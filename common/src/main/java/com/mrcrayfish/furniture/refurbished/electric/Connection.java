package com.mrcrayfish.furniture.refurbished.electric;

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

    public BlockPos getPosA()
    {
        return this.a.pos;
    }

    public BlockPos getPosB()
    {
        return this.b.pos;
    }

    @Nullable
    public IElectricNode getNodeA(Level level)
    {
        return this.a.getElectricNode(level);
    }

    @Nullable
    public IElectricNode getNodeB(Level level)
    {
        return this.b.getElectricNode(level);
    }

    public boolean isConnected(Level level)
    {
        return this.a.isValid(level) && this.b.isValid(level);
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
     * the positions are switched. This ensures connections from two different positions are
     */
    private void calculateHash()
    {
        if(this.hash == null)
        {
            this.hash = Objects.hashCode(this.getMinPos(), this.getMaxPos());
        }
    }

    private BlockPos getMinPos()
    {
        int c = this.a.pos.compareTo(this.b.pos);
        return c > 0 ? this.a.pos : this.b.pos;
    }

    private BlockPos getMaxPos()
    {
        int c = this.a.pos.compareTo(this.b.pos);
        return c > 0 ? this.b.pos : this.a.pos;
    }

    public static Connection of(BlockPos a, BlockPos b)
    {
        return new Connection(a, b);
    }

    private static class Node
    {
        private final BlockPos pos;
        private WeakReference<IElectricNode> ref;
        private Status status = Status.ACTIVE;

        private Node(BlockPos pos)
        {
            this.pos = pos;
            this.ref = new WeakReference<>(null);
        }

        public boolean isValid(Level level)
        {
            this.updateStatus(level);
            return this.status.valid;
        }

        @Nullable
        public IElectricNode getElectricNode(Level level)
        {
            this.updateStatus(level);
            return this.ref.get();
        }

        private void updateStatus(Level level)
        {
            IElectricNode node = this.ref.get();
            if(node != null && node.isValid())
            {
                this.status = Status.ACTIVE;
            }

            if(level.isLoaded(this.pos))
            {
                if(level.getBlockEntity(this.pos) instanceof IElectricNode found && found.isValid())
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
            ACTIVE(true),
            UNDETERMINED(true),
            INVALID(false);

            final boolean valid;

            Status(boolean valid)
            {
                this.valid = valid;
            }
        }
    }
}
