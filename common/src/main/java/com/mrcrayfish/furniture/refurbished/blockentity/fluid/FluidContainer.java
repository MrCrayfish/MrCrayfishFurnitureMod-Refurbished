package com.mrcrayfish.furniture.refurbished.blockentity.fluid;

import com.google.common.base.Preconditions;
import com.mrcrayfish.framework.api.Environment;
import com.mrcrayfish.framework.api.LogicalEnvironment;
import com.mrcrayfish.framework.api.util.EnvironmentHelper;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public abstract class FluidContainer
{
    public static final long BUCKET_CAPACITY = Services.FLUID.getBucketCapacity();

    protected FluidContainer() {}

    public abstract long getCapacity();

    public abstract boolean isEmpty();

    public abstract Fluid getStoredFluid();

    public abstract long getStoredAmount();

    protected abstract void setStored(Fluid fluid, long amount);

    public abstract long push(Fluid fluid, long amount, boolean simulate);

    public abstract Pair<Fluid, Long> pull(long amount, boolean simulate);

    public abstract void load(CompoundTag tag);

    public abstract void save(CompoundTag tag);

    public final void sync(BlockEntity owner)
    {
        Level level = Objects.requireNonNull(owner.getLevel());
        Preconditions.checkState(!level.isClientSide());
        if(level.getChunkSource() instanceof ServerChunkCache cache)
        {
            BlockPos pos = owner.getBlockPos();
            List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> Network.getPlay().sendToPlayer(() -> player, new MessageSyncFluid(pos, this.getStoredFluid(), this.getStoredAmount())));
        }
    }

    public final void handleSync(Level level, Fluid fluid, long amount)
    {
        Preconditions.checkState(level.isClientSide());
        this.setStored(fluid, amount);
    }

    public static FluidContainer create(long capacity)
    {
        return Services.FLUID.createFluidContainer(capacity, null);
    }

    public static FluidContainer create(long capacity, Consumer<FluidContainer> onChange)
    {
        return Services.FLUID.createFluidContainer(capacity, onChange);
    }
}
