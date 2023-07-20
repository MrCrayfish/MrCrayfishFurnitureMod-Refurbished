package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

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

    // TODO particle effect when filling
    @Override
    public InteractionResult interact(Player player, InteractionHand hand, BlockHitResult result)
    {
        // TODO allow this to be triggered with redstone
        if(player.getItemInHand(hand).isEmpty() && result.getDirection() != Direction.DOWN)
        {
            // Fills the sink with water when interacting with an empty hand. TODO make config option to disable free water
            if((this.tank.getAmount() <= 0 || this.tank.getResource().getFluid() == Fluids.WATER))
            {
                try(Transaction transaction = Transaction.openOuter())
                {
                    long filled = this.tank.insert(FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET / 81, transaction);
                    if(filled > 0)
                    {
                        transaction.commit();
                        Objects.requireNonNull(this.level).playSound(null, this.worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS);
                        return InteractionResult.SUCCESS;
                    }
                }
            }

            // If lava is in the sink, filling it with water will consume the lava and turn it into obsidian
            long bucketAmount = FluidConstants.BUCKET / 81L;
            if(this.tank.getAmount() >= bucketAmount && this.tank.getResource().getFluid() == Fluids.LAVA)
            {
                try(Transaction transaction = Transaction.openOuter())
                {
                    long drained = this.tank.extract(FluidVariant.of(Fluids.LAVA), bucketAmount, transaction);
                    if(drained == bucketAmount)
                    {
                        transaction.commit();
                        Vec3 pos = Vec3.atBottomCenterOf(this.worldPosition).add(0, 1, 0);
                        Level level = Objects.requireNonNull(this.level);
                        ItemEntity entity = new ItemEntity(level, pos.x, pos.y, pos.z, new ItemStack(Blocks.OBSIDIAN));
                        entity.setDefaultPickUpDelay();
                        level.addFreshEntity(entity);
                        level.playSound(null, this.worldPosition, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);
                        level.levelEvent(LevelEvent.LAVA_FIZZ, this.worldPosition, 0);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
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
