package com.mrcrayfish.furniture.refurbished.blockentity.fluid;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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
 * An abstraction of a fluid container for platform specific implementation
 * <p>
 * Author: MrCrayfish
 */
public abstract class FluidContainer
{
    public static final long BUCKET_CAPACITY = Services.FLUID.getBucketCapacity();

    protected FluidContainer() {}

    /**
     * @return The capacity of this fluid container
     */
    public abstract long getCapacity();

    /**
     * @return True if this fluid container is empty
     */
    public abstract boolean isEmpty();

    /**
     * @return The type of fluid stored in this tank or {@link net.minecraft.world.level.material.Fluids#EMPTY} if empty
     */
    public abstract Fluid getStoredFluid();

    /**
     * @return The amount of fluid stored in this fluid container or zero if empty
     */
    public abstract long getStoredAmount();

    /**
     * Raw method to set the fluid and the amount in this fluid container. This should not be
     * used to add or remove fluid. Instead, use {@link #push(Fluid, long, boolean)} and
     * {@link #pull(long, boolean)}.
     *
     * @param fluid the type of fluid
     * @param amount the amount of fluid
     */
    protected abstract void setStored(Fluid fluid, long amount);

    /**
     * Attempts to add the given fluid type and amount into the container. The returned long value
     * is the remaining amount of fluid when after adding it to the tank. If zero, all the fluid was
     * added. If not zero but is less then the initial amount, only a partial amount was added. If
     * exactly the same as the initial value, no fluid was added. This method can be used to test if
     * fluid can be added by passing simulate as true.
     *
     * @param fluid the type of fluid to add
     * @param amount the amount of fluid to add
     * @param simulate pass as true to test if the fluid can be added
     * @return the remaining amount of fluid from the amount added
     */
    public abstract long push(Fluid fluid, long amount, boolean simulate);

    /**
     * Attempts to pull given amount of fluid from this fluid container. This method returns a pair
     * containing the type of fluid and the amount that was removed. The amount removed is not
     * necessarily the same as the input amount, however this can be checked beforehand by passing
     * simulate as true before committing to removing the fluid. If the fluid container is empty,
     * the returned pair will simply be {@link net.minecraft.world.level.material.Fluids#EMPTY} and
     * an amount of zero.
     *
     * @param amount the amount of fluid to try to remove
     * @param simulate pass as true to test if the given amount can be removed
     * @return a pair containing the fluid and the amount remove.
     */
    public abstract Pair<Fluid, Long> pull(long amount, boolean simulate);

    /**
     * Loads this fluid container from the given compound tag
     *
     * @param tag the compound tag to read from
     */
    public abstract void load(CompoundTag tag, HolderLookup.Provider provider);

    /**
     * Saves the fluid container to the given compound tag.
     *
     * @param tag the compound tag to append the data to
     */
    public abstract void save(CompoundTag tag, HolderLookup.Provider provider);

    /**
     * Syncs
     * @param owner
     */
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

    /**
     *
     * @param level
     * @param fluid
     * @param amount
     */
    public final void handleSync(Level level, Fluid fluid, long amount)
    {
        Preconditions.checkState(level.isClientSide());
        this.setStored(fluid, amount);
    }

    /**
     *
     * @param capacity
     * @return
     */
    public static FluidContainer create(long capacity)
    {
        return Services.FLUID.createFluidContainer(capacity, null);
    }

    /**
     *
     * @param capacity
     * @param onChange
     * @return
     */
    public static FluidContainer create(long capacity, Consumer<FluidContainer> onChange)
    {
        return Services.FLUID.createFluidContainer(capacity, onChange);
    }
}
