package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.platform.services.IFluidHelper;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidUtil;

import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
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
    public long getBottleCapacity()
    {
        // Not ideal, but NeoForge's bucket capacity is not divisible by 3.
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
        // TODO 1.21.10 find replacement
        return FluidUtil.interactWithFluidHandler(player, hand, level, pos, face) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public Fluid getMilkFluid()
    {
        return NeoForgeMod.MILK.get();
    }

    public static class NeoForgeFluidContainer extends FluidContainer
    {
        protected final FluidStacksResourceHandler tank;
        private final int capacity;

        protected NeoForgeFluidContainer(long capacity, @Nullable Consumer<FluidContainer> onChange)
        {
            this.tank = new FluidStacksResourceHandler(1, (int) capacity) {
                @Override
                protected void onContentsChanged(int index, FluidStack previousContents) {
                    if(onChange != null) {
                        onChange.accept(NeoForgeFluidContainer.this);
                    }
                }
            };
            this.capacity = (int) capacity;
        }

        @Override
        public long getCapacity()
        {
            return this.capacity;
        }

        @Override
        public boolean isEmpty()
        {
            return this.tank.getResource(0).isEmpty();
        }

        @Override
        public Fluid getStoredFluid()
        {
            return this.tank.getResource(0).getFluid();
        }

        @Override
        public long getStoredAmount()
        {
            return this.tank.getAmountAsLong(0);
        }

        @Override
        protected void setStored(Fluid fluid, long amount)
        {
            this.tank.set(0, FluidResource.of(fluid), (int) amount);
        }

        @Override
        public long push(Fluid fluid, long amount, boolean simulate)
        {
            try(Transaction tx = Transaction.open(null))
            {
                int inserted = this.tank.insert(FluidResource.of(fluid), (int) amount, tx);
                if(!simulate)
                {
                    tx.commit();
                }
                return inserted;
            }
        }

        @Override
        public Pair<Fluid, Long> pull(long amount, boolean simulate)
        {
            try(Transaction tx = Transaction.open(null))
            {
                FluidResource resource = this.tank.getResource(0);
                if(resource.isEmpty())
                {
                    return Pair.of(Fluids.EMPTY, 0L);
                }
                int extracted = this.tank.extract(resource, (int) amount, tx);
                if(!simulate)
                {
                    tx.commit();
                }
                return Pair.of(resource.getFluid(), (long) extracted);
            }
        }

        @Override
        public void load(ValueInput input)
        {
            this.tank.deserialize(input);
        }

        @Override
        public void save(ValueOutput output)
        {
            this.tank.serialize(output);
        }

        public FluidStacksResourceHandler getTank()
        {
            return this.tank;
        }
    }
}
