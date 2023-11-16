package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class ToiletBlockEntity extends BlockEntity implements IFluidContainerBlock
{
    protected final FluidContainer tank = FluidContainer.create(Config.SERVER.toilet.fluidCapacity.get(), container -> {
        this.setChanged();
        container.sync(this);
    });

    public ToiletBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.TOILET.get(), pos, state);
    }

    public ToiletBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    @Override
    public FluidContainer getFluidContainer()
    {
        return this.tank;
    }

    public InteractionResult interact(Player player, InteractionHand hand, BlockHitResult result)
    {
        if(Config.SERVER.toilet.dispenseWater.get() && player.getItemInHand(hand).isEmpty() && result.getDirection() != Direction.DOWN)
        {
            // Fills the sink with water
            if(this.tank.isEmpty() || this.tank.getStoredFluid().isSame(Fluids.WATER))
            {
                long filled = this.tank.push(Fluids.WATER, FluidContainer.BUCKET_CAPACITY, false);
                if(filled > 0)
                {
                    SoundEvent event = Services.FLUID.getBucketEmptySound(Fluids.WATER);
                    if(event != null)
                    {
                        Objects.requireNonNull(this.level).playSound(null, this.worldPosition, event, SoundSource.BLOCKS);
                        return InteractionResult.SUCCESS;
                    }
                }
            }

            // If lava is in the basin, filling it with water will consume the lava and turn it into obsidian
            if(this.tank.getStoredAmount() >= FluidContainer.BUCKET_CAPACITY && this.tank.getStoredFluid().isSame(Fluids.LAVA))
            {
                Pair<Fluid, Long> drained = this.tank.pull(FluidContainer.BUCKET_CAPACITY, true);
                if(drained.right() == FluidContainer.BUCKET_CAPACITY)
                {
                    this.tank.pull(FluidContainer.BUCKET_CAPACITY, false);
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
        return Services.FLUID.performInteractionWithBlock(player, hand, this.getLevel(), this.getBlockPos(), result.getDirection());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.tank.load(tag.getCompound("FluidTank"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        CompoundTag tankTag = new CompoundTag();
        this.tank.save(tankTag);
        tag.put("FluidTank", tankTag);
    }
}
