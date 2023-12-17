package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.platform.services.IFluidHelper;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class ForgeFluidHelper implements IFluidHelper
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
        return new ForgeFluidContainer(capacity, onChange);
    }

    @Override
    public InteractionResult performInteractionWithBlock(Player player, InteractionHand hand, Level level, BlockPos pos, Direction face)
    {
        return FluidUtil.interactWithFluidHandler(player, hand, level, pos, face) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public boolean isFluidContainerItem(ItemStack stack)
    {
        return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
    }

    public static class ForgeFluidContainer extends FluidContainer
    {
        protected final FluidTank tank;

        protected ForgeFluidContainer(long capacity, @Nullable Consumer<FluidContainer> onChange)
        {
            this.tank = new FluidTank((int) capacity) {
                @Override
                protected void onContentsChanged() {
                    if(onChange != null) {
                        onChange.accept(ForgeFluidContainer.this);
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
        public void load(CompoundTag tag)
        {
            this.tank.readFromNBT(tag.getCompound("FluidTank"));
        }

        @Override
        public void save(CompoundTag tag)
        {
            CompoundTag tankTag = new CompoundTag();
            this.tank.writeToNBT(tankTag);
            tag.put("FluidTank", tankTag);
        }

        public FluidTank getTank()
        {
            return this.tank;
        }
    }
}
