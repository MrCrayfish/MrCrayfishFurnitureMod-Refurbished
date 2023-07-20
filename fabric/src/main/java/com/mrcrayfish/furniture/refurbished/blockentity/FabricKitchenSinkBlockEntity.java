package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class FabricKitchenSinkBlockEntity extends KitchenSinkBlockEntity
{
    // TODO make abstraction so tank can be in common class instead
    public final SingleFluidStorage tank = SingleFluidStorage.withFixedCapacity((FluidConstants.BUCKET * 3) / 81, () -> {
        this.setChanged();
        if(!Objects.requireNonNull(this.level).isClientSide()) {
            PlayerLookup.tracking(this).forEach(player -> {
                SingleFluidStorage tank = this.getTank();
                Network.getPlay().sendToPlayer(() -> player, new MessageSyncFluid(this.getBlockPos(), tank.getResource().getFluid(), tank.getAmount()));
            });
        }
    });
    
    public FabricKitchenSinkBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    public FabricKitchenSinkBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
        this.setChanged();
        FluidRenderHandlerRegistry.INSTANCE.get(this.tank.getResource().getFluid());
    }

    @Override
    public SingleFluidStorage getTank()
    {
        return this.tank;
    }

    @Override
    public Optional<Fluid> getFluid()
    {
        return this.tank.getAmount() > 0 ? Optional.of(this.tank.getResource().getFluid()) : Optional.empty();
    }

    @Override
    public long getFluidAmount()
    {
        return this.tank.getAmount();
    }

    @Override
    public long getTankCapacity()
    {
        return this.tank.getCapacity();
    }

    @Override
    public void onSyncFluid(Fluid fluid, long amount)
    {
        this.tank.variant = FluidVariant.of(fluid);
        this.tank.amount = amount;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, BlockHitResult result)
    {
        return FluidStorageUtil.interactWithFluidStorage(this.tank, player, hand) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.tank.readNbt(tag.getCompound("FluidTank"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        CompoundTag tankTag = new CompoundTag();
        this.tank.writeNbt(tankTag);
        tag.put("FluidTank", tankTag);
    }
}
