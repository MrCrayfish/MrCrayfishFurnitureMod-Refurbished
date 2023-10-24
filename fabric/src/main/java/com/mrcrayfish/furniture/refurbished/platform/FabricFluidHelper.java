package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.platform.services.IFluidHelper;
import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class FabricFluidHelper implements IFluidHelper
{
    @Override
    public long getBucketCapacity()
    {
        return FluidConstants.BUCKET;
    }

    @Override
    public SoundEvent getBucketEmptySound(Fluid fluid)
    {
        return FluidVariantAttributes.getEmptySound(FluidVariant.of(fluid));
    }

    @Override
    public FluidContainer createFluidContainer(long capacity, @Nullable Consumer<FluidContainer> onChange)
    {
        return new FabricFluidContainer(capacity, onChange);
    }

    @Override
    public InteractionResult performInteractionWithBlock(Player player, InteractionHand hand, Level level, BlockPos pos, Direction face)
    {
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(level, pos, face);
        return storage != null && FluidStorageUtil.interactWithFluidStorage(storage, player, hand) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    public static class FabricFluidContainer extends FluidContainer
    {
        private final SingleFluidStorage tank;

        protected FabricFluidContainer(long capacity, @Nullable Consumer<FluidContainer> onChange)
        {
            this.tank = SingleFluidStorage.withFixedCapacity(capacity, () -> {
                if(onChange != null) {
                    onChange.accept(FabricFluidContainer.this);
                }
            });
        }

        @Override
        public long getCapacity()
        {
            return this.tank.getCapacity();
        }

        @Override
        public boolean isEmpty()
        {
            return this.tank.isResourceBlank();
        }

        @Override
        public Fluid getStoredFluid()
        {
            return this.tank.getResource().getFluid();
        }

        @Override
        public long getStoredAmount()
        {
            return this.tank.getAmount();
        }

        @Override
        protected void setStored(Fluid fluid, long amount)
        {
            this.tank.variant = FluidVariant.of(fluid);
            this.tank.amount = amount;
        }

        @Override
        public long push(Fluid fluid, long amount, boolean simulate)
        {
            try(Transaction transaction = Transaction.openOuter())
            {
                long filled = this.tank.insert(FluidVariant.of(fluid), amount, transaction);
                if(!simulate)
                {
                    transaction.commit();
                }
                return filled;
            }
        }

        @Override
        public Pair<Fluid, Long> pull(long amount, boolean simulate)
        {
            try(Transaction transaction = Transaction.openOuter())
            {
                FluidVariant variant = this.tank.getResource();
                long drained = this.tank.extract(variant, amount, transaction);
                if(!simulate)
                {
                    transaction.commit();
                }
                return Pair.of(variant.getFluid(), drained);
            }
        }

        @Override
        public void load(CompoundTag tag)
        {
            this.tank.readNbt(tag.getCompound("FluidTank"));
        }

        @Override
        public void save(CompoundTag tag)
        {
            CompoundTag tankTag = new CompoundTag();
            this.tank.writeNbt(tankTag);
            tag.put("FluidTank", tankTag);
        }
    }
}
