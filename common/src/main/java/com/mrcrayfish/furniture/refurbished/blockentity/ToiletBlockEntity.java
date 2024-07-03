package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageFlushItem;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
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
        Level level = Objects.requireNonNull(this.level);
        ItemStack heldItem = player.getItemInHand(hand);
        if(!Services.FLUID.isFluidContainerItem(heldItem))
        {
            Vec3 hit = result.getLocation().subtract(Vec3.atLowerCornerOf(this.worldPosition));
            if(hit.y() > 0.625)
            {
                if(!this.tank.isEmpty())
                {
                    if(this.flushItems(level) != InteractionResult.PASS)
                    {
                        return InteractionResult.SUCCESS;
                    }
                }
                if(Config.SERVER.toilet.dispenseWater.get())
                {
                    if(this.fillWithWater(level) != InteractionResult.PASS)
                    {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.PASS;
        }
        Services.FLUID.performInteractionWithBlock(player, hand, this.getLevel(), this.getBlockPos(), result.getDirection());
        return InteractionResult.SUCCESS;
    }

    private InteractionResult fillWithWater(Level level)
    {
        if((this.tank.isEmpty() || this.tank.getStoredFluid().isSame(Fluids.WATER)))
        {
            if(this.tank.getStoredAmount() < this.tank.getCapacity())
            {
                this.tank.push(Fluids.WATER, FluidContainer.BUCKET_CAPACITY, false);
            }
            SoundEvent event = Services.FLUID.getBucketEmptySound(Fluids.WATER);
            if(event != null)
            {
                Vec3 splashPos = Vec3.atCenterOf(this.worldPosition);
                ((ServerLevel) level).sendParticles(ParticleTypes.SPLASH, splashPos.x, splashPos.y, splashPos.z, 10, 0, 0, 0, 0);
                level.playSound(null, this.worldPosition, event, SoundSource.BLOCKS);
                return InteractionResult.SUCCESS;
            }
        }

        if(this.tank.getStoredFluid().isSame(Fluids.LAVA))
        {
            if(this.tank.getStoredAmount() >= FluidContainer.BUCKET_CAPACITY && this.tank.getStoredFluid().isSame(Fluids.LAVA))
            {
                Pair<Fluid, Long> drained = this.tank.pull(FluidContainer.BUCKET_CAPACITY, true);
                if(drained.right() != FluidContainer.BUCKET_CAPACITY)
                    return InteractionResult.PASS;

                this.tank.pull(FluidContainer.BUCKET_CAPACITY, false);
                Vec3 pos = Vec3.atCenterOf(this.worldPosition);
                ItemEntity entity = new ItemEntity(level, pos.x, pos.y, pos.z, new ItemStack(Blocks.OBSIDIAN));
                entity.setDefaultPickUpDelay();
                level.addFreshEntity(entity);
                level.playSound(null, this.worldPosition, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);
                level.levelEvent(LevelEvent.LAVA_FIZZ, this.worldPosition, 0);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private InteractionResult flushItems(Level level)
    {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition));
        entities.forEach(entity -> {
            Network.getPlay().sendToTrackingBlockEntity(() -> this, new MessageFlushItem(entity.getId(), this.worldPosition));
            entity.discard();
        });
        if(!entities.isEmpty())
        {
            level.scheduleTick(this.worldPosition, this.getBlockState().getBlock(), 40);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
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
