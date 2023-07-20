package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class ForgeKitchenSinkBlockEntity extends KitchenSinkBlockEntity
{
    protected final FluidTank tank = new FluidTank(FluidType.BUCKET_VOLUME * 3) {
        @Override
        protected void onContentsChanged()
        {
            Level level = Objects.requireNonNull(ForgeKitchenSinkBlockEntity.this.level);
            if(level.getChunkSource() instanceof ServerChunkCache cache)
            {
                BlockPos pos = ForgeKitchenSinkBlockEntity.this.getBlockPos();
                List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
                players.forEach(player -> Network.getPlay().sendToPlayer(() -> player, new MessageSyncFluid(pos, this.getFluid().getFluid(), this.getFluidAmount())));
            }
        }
    };
    protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> this.tank);

    public ForgeKitchenSinkBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    public ForgeKitchenSinkBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public FluidTank getTank()
    {
        return this.tank;
    }

    @Override
    public Optional<Fluid> getFluid()
    {
        return this.tank.getFluidAmount() > 0 ? Optional.of(this.tank.getFluid().getFluid()) : Optional.empty();
    }

    @Override
    public long getFluidAmount()
    {
        return this.tank.getFluidAmount();
    }

    @Override
    public long getTankCapacity()
    {
        return this.tank.getCapacity();
    }

    @Override
    public void onSyncFluid(Fluid fluid, long amount)
    {
        this.tank.setFluid(new FluidStack(fluid, (int) amount));
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, BlockHitResult result)
    {
        // Fills the sink with water when interacting with an empty hand. TODO make config option to disable free water
        if((this.tank.isEmpty() || this.tank.getFluid().getFluid() == Fluids.WATER) && player.getItemInHand(hand).isEmpty() && result.getDirection() != Direction.DOWN)
        {
            int filled = this.tank.fill(new FluidStack(Fluids.WATER, FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
            if(filled > 0)
            {
                SoundEvent event = Fluids.WATER.getFluidType().getSound(SoundActions.BUCKET_EMPTY);
                if(event != null)
                {
                    Objects.requireNonNull(this.level).playSound(null, this.worldPosition, event, SoundSource.BLOCKS);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return FluidUtil.interactWithFluidHandler(player, hand, this.getLevel(), this.getBlockPos(), result.getDirection()) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.tank.readFromNBT(tag.getCompound("FluidTank"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        CompoundTag tankTag = new CompoundTag();
        this.tank.writeToNBT(tankTag);
        tag.put("FluidTank", tankTag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return cap == ForgeCapabilities.FLUID_HANDLER ? this.holder.cast() : super.getCapability(cap);
    }
}
