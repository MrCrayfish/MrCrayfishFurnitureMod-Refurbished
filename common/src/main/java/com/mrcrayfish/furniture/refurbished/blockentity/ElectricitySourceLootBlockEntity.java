package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.ISourceNode;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricitySourceLootBlockEntity extends BasicLootBlockEntity implements ISourceNode
{
    protected final Set<Connection> connections = new HashSet<>();
    protected boolean overloaded;

    public ElectricitySourceLootBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize)
    {
        super(type, pos, state, containerSize);
    }

    @Override
    public Level getNodeLevel()
    {
        return this.level;
    }

    @Override
    public BlockPos getPosition()
    {
        return this.worldPosition;
    }

    @Override
    public BlockEntity getBlockEntity()
    {
        return this;
    }

    @Override
    public Set<Connection> getConnections()
    {
        return this.connections;
    }

    @Override
    public void setOverloaded(boolean overloaded)
    {
        this.overloaded = overloaded;
    }

    @Override
    public boolean isOverloaded()
    {
        return this.overloaded;
    }

    @Override
    public void setLevel(Level level)
    {
        super.setLevel(level);
        this.registerTicker(level);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.readNodeNbt(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        this.writeNodeNbt(tag);
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
    public int hashCode()
    {
        return this.worldPosition.hashCode();
    }

    // Forge method
    // @Override
    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox()
    {
        return new AABB(this.worldPosition).inflate(Config.CLIENT.electricityViewDistance.get());
    }
}
