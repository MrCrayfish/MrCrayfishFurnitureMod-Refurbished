package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.block.BasinBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModParticleTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageWaterTapAnimation;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
public class BasinBlockEntity extends BlockEntity implements IFluidContainerBlock, IWaterTap
{
    protected final FluidContainer tank = FluidContainer.create(Config.SERVER.basin.fluidCapacity.get(), container -> {
        this.setChanged();
        container.sync(this);
    });

    // Client only
    private int animationTime;

    public BasinBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.BASIN.get(), pos, state);
    }

    public BasinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        return this.saveWithoutMetadata(provider);
    }

    @Override
    public FluidContainer getFluidContainer()
    {
        return this.tank;
    }

    public ItemInteractionResult interact(Player player, InteractionHand hand, BlockHitResult result)
    {
        if(Config.SERVER.basin.dispenseWater.get() && player.getItemInHand(hand).isEmpty() && result.getDirection() != Direction.DOWN)
        {
            // Fills the sink with water
            if(this.tank.isEmpty() || this.tank.getStoredFluid().isSame(Fluids.WATER))
            {
                long filled = this.tank.push(Fluids.WATER, FluidContainer.BUCKET_CAPACITY, false);
                if(filled > 0)
                {
                    Network.getPlay().sendToTrackingBlockEntity(() -> this, new MessageWaterTapAnimation(this.worldPosition));
                    Objects.requireNonNull(this.level).playSound(null, this.worldPosition, ModSounds.BLOCK_KITCHEN_SINK_FILL.get(), SoundSource.BLOCKS);
                    return ItemInteractionResult.SUCCESS;
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
                    Network.getPlay().sendToTrackingBlockEntity(() -> this, new MessageWaterTapAnimation(this.worldPosition));
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return Services.FLUID.performInteractionWithBlock(player, hand, this.getLevel(), this.getBlockPos(), result.getDirection());
    }

    @Override
    public void playWaterAnimation()
    {
        this.animationTime = 4;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BasinBlockEntity basin)
    {
        if(basin.animationTime > 0)
        {
            Vec3 tap = Vec3.atBottomCenterOf(pos).add(0, Utils.pixels(18), 0);
            tap = tap.relative(state.getValue(BasinBlock.DIRECTION), Utils.pixels(2));
            for(int i = 0; i < 5; i++)
            {
                double x = tap.x + Utils.pixels(0.5) * level.random.nextGaussian();
                double z = tap.z + Utils.pixels(0.5) * level.random.nextGaussian();
                level.addParticle(ModParticleTypes.TAP_WATER.get(), x, tap.y, z, 0, 0, 0);
            }
            basin.animationTime--;
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        this.tank.load(tag.getCompound("FluidTank"), provider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        CompoundTag tankTag = new CompoundTag();
        this.tank.save(tankTag, provider);
        tag.put("FluidTank", tankTag);
    }
}
