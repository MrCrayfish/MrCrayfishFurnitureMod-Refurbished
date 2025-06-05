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
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
        Vec3 hit = result.getLocation().subtract(Vec3.atLowerCornerOf(this.worldPosition));
        if(hit.y() > 0.625) // Player hit the top half of the toilet
        {
            if(!this.tank.isEmpty() && this.flushItems(player.level()).consumesAction())
            {
                return ItemInteractionResult.SUCCESS;
            }
        }

        if(this.interactWithBottle(player, hand, this.worldPosition).consumesAction())
            return ItemInteractionResult.SUCCESS;

        if(this.performPlatformInteraction(player, hand, this.worldPosition, result.getDirection()).consumesAction())
            return ItemInteractionResult.SUCCESS;

        if(Config.SERVER.toilet.dispenseWater.get() && result.getDirection() != Direction.DOWN)
        {
            if(this.tryAndFillWithFluid(this.level, this.worldPosition, Fluids.WATER, Vec3.atCenterOf(this.worldPosition)).consumesAction())
                return ItemInteractionResult.SUCCESS;

            if(this.tryAndCreateObsidian(this.level, this.worldPosition, Fluids.WATER, Vec3.atBottomCenterOf(this.worldPosition).add(0, 1, 0)).consumesAction())
                return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.CONSUME;
    }

    private InteractionResult flushItems(Level level)
    {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition));
        if(!entities.isEmpty())
        {
            if(!level.isClientSide())
            {
                entities.forEach(entity -> {
                    Network.getPlay().sendToTrackingBlockEntity(() -> this, new MessageFlushItem(entity.getId(), this.worldPosition));
                    entity.discard();
                });
                level.scheduleTick(this.worldPosition, this.getBlockState().getBlock(), 40);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
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
