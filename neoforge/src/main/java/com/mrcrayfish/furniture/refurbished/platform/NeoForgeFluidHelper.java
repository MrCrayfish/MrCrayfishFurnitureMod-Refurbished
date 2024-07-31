package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.platform.services.IFluidHelper;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class NeoForgeFluidHelper implements IFluidHelper
{
    @Override
    public long getBucketCapacity()
    {
        return FluidType.BUCKET_VOLUME;
    }

    @Override
    public SoundEvent getBucketEmptySound(Fluid fluid)
    {
        return fluid.getFluidType().getSound(SoundActions.BUCKET_EMPTY);
    }

    @Override
    public FluidContainer createFluidContainer(long capacity, @Nullable Consumer<FluidContainer> onChange)
    {
        return new NeoForgeFluidContainer(capacity, onChange);
    }

    @Override
    public InteractionResult performInteractionWithBlock(Player player, InteractionHand hand, Level level, BlockPos pos, Direction face)
    {
        return FluidUtil.interactWithFluidHandler(player, hand, level, pos, face) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public boolean isFluidContainerItem(ItemStack stack)
    {
        return !stack.isEmpty() && stack.getCapability(Capabilities.ItemHandler.ITEM) != null;
    }

    @Override
    public Fluid getMilkFluid()
    {
        return NeoForgeMod.MILK.get();
    }

    public static class NeoForgeFluidContainer extends FluidContainer
    {
        protected final FluidTank tank;

        protected NeoForgeFluidContainer(long capacity, @Nullable Consumer<FluidContainer> onChange)
        {
            this.tank = new FluidTank((int) capacity) {
                @Override
                protected void onContentsChanged() {
                    if(onChange != null) {
                        onChange.accept(NeoForgeFluidContainer.this);
                    }
                }
            };
        }

        @Override
        public long getCapacity()
        {
            return this.tank.getCapacity();
        }

        @Override
        public boolean isEmpty()
        {
            return this.tank.isEmpty();
        }

        @Override
        public Fluid getStoredFluid()
        {
            return this.tank.getFluid().getFluid();
        }

        @Override
        public long getStoredAmount()
        {
            return this.tank.getFluidAmount();
        }

        @Override
        protected void setStored(Fluid fluid, long amount)
        {
            this.tank.setFluid(new FluidStack(fluid, (int) amount));
        }

        @Override
        public long push(Fluid fluid, long amount, boolean simulate)
        {
            return this.tank.fill(new FluidStack(fluid, (int) amount), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
        }

        @Override
        public Pair<Fluid, Long> pull(long amount, boolean simulate)
        {
            FluidStack stack = this.tank.drain((int) amount, simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
            return Pair.of(stack.getFluid(), (long) stack.getAmount());
        }

        @Override
        public void load(CompoundTag tag, HolderLookup.Provider provider)
        {
            this.tank.readFromNBT(provider, tag.getCompound("FluidTank"));
        }

        @Override
        public void save(CompoundTag tag, HolderLookup.Provider provider)
        {
            CompoundTag tankTag = new CompoundTag();
            this.tank.writeToNBT(provider, tankTag);
            tag.put("FluidTank", tankTag);
        }

        public FluidTank getTank()
        {
            return this.tank;
        }
    }
}
