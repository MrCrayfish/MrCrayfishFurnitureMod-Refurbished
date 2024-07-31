package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.ISourceNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricitySourceBlockEntity extends BlockEntity implements ISourceNode
{
    protected final Set<Connection> connections = new HashSet<>();
    protected boolean overloaded;

    public ElectricitySourceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public Level getNodeLevel()
    {
        return this.level;
    }

    @Override
    public BlockPos getNodePosition()
    {
        return this.worldPosition;
    }

    @Override
    public BlockEntity getNodeOwner()
    {
        return this;
    }

    @Override
    public Set<Connection> getNodeConnections()
    {
        return this.connections;
    }

    @Override
    public void setNodeOverloaded(boolean overloaded)
    {
        this.overloaded = overloaded;
    }

    @Override
    public boolean isNodeOverloaded()
    {
        return this.overloaded;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        this.readNodeNbt(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        this.writeNodeNbt(tag);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        return this.saveWithoutMetadata(provider);
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

    @Override
    public void saveToItem(ItemStack stack, HolderLookup.Provider provider)
    {
        this.saveNodeNbtToItem(stack, provider);
    }
}
