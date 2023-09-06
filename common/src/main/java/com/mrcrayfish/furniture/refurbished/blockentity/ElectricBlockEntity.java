package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricBlockEntity extends BlockEntity implements IElectricNode
{
    public static final int MAX_SEARCH_DEPTH = 5;
    public static final AABB RENDER_BOX = Shapes.INFINITY.bounds();
    public static final AABB NODE_BOX = new AABB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);

    protected final Set<Connection> connections = new HashSet<>();

    public ElectricBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public Set<Connection> getConnections()
    {
        this.updateConnections();
        return this.connections;
    }

    @Override
    public final boolean connectTo(IElectricNode other)
    {
        if(this.connections.add(Connection.of(this.worldPosition, other.getPosition())))
        {
            other.connectTo(this);
            this.onConnectedTo(other);
            this.syncConnections();
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnectedTo(IElectricNode node)
    {
        return this.connections.stream().anyMatch(c -> c.getPosB().equals(node.getPosition()));
    }

    private void updateConnections()
    {
        this.connections.removeIf(c -> !c.isConnected(this.level));
    }

    protected void onConnectedTo(IElectricNode other) {}

    @Override
    public void syncConnections()
    {
        this.updateConnections();
        CompoundTag compound = new CompoundTag();
        this.writeConnections(compound);
        BlockEntityHelper.sendCustomUpdate(this, compound);
    }

    @Override
    public void removeConnection(Connection connection)
    {
        this.connections.remove(connection);
        this.syncConnections();
    }

    public void removeAllConnections()
    {
        this.connections.forEach(c -> {
            IElectricNode node = c.getNodeB(this.level);
            if(node != null) {
                node.removeConnection(c);
            }
        });
        this.connections.clear();
    }

    public void onDestroyed()
    {
        this.removeAllConnections();
    }

    @Override
    public boolean isValid()
    {
        return !this.isRemoved();
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("Connections", Tag.TAG_LONG_ARRAY))
        {
            this.connections.clear();
            long[] nodes = tag.getLongArray("Connections");
            for(long node : nodes)
            {
                this.connections.add(Connection.of(this.worldPosition, BlockPos.of(node)));
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        this.writeConnections(tag);
    }

    protected void writeConnections(CompoundTag tag)
    {
        tag.putLongArray("Connections", this.connections.stream().flatMap(c -> c.getOtherPos(this.worldPosition).stream()).map(BlockPos::asLong).toList());
    }

    @Override
    public BlockPos getPosition()
    {
        return this.worldPosition;
    }

    @Override
    public AABB getPositionedInteractBox()
    {
        return NODE_BOX.move(this.worldPosition);
    }

    @Override
    public AABB getInteractBox()
    {
        return NODE_BOX;
    }

    protected void searchNode(IElectricNode node, Set<IElectricNode> found, int depth, Predicate<IElectricNode> predicate)
    {
        if(depth <= 0)
            return;

        for(Connection connection : node.getConnections())
        {
            IElectricNode connectedNode = connection.getNodeB(this.level);
            if(connectedNode == null || found.contains(connectedNode))
                continue;

            if(!connectedNode.isValid())
                continue;

            if(!predicate.test(connectedNode))
                continue;

            found.add(connectedNode);

            // Searching stops when a source is reached
            if(connectedNode.isSource())
                continue;

            this.searchNode(connectedNode, found, depth - 1, predicate);
        }
    }

    // Forge method
    // @Override
    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox()
    {
        return RENDER_BOX;
    }
}
