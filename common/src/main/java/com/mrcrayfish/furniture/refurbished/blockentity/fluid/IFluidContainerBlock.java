package com.mrcrayfish.furniture.refurbished.blockentity.fluid;

import com.mrcrayfish.furniture.refurbished.blockentity.IWaterTap;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageWaterTapAnimation;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Implement on BlockEntities to add a fluid storage
 * <p>
 * Author: MrCrayfish
 */
public interface IFluidContainerBlock
{
    /**
     * @return The fluid container of this block, or null
     */
    @Nullable
    FluidContainer getFluidContainer();

    /**
     * Tries to fill this fluid container with a bucket worth's of the given fluid. If there is
     * already fluid in the container, and it does not match in the input fluid, or the container
     * is full, nothing will happen and this method will simply return a pass interaction result.
     * If the input fluid is water, a special case is included to summon splash particles at the
     * given splashPos.
     *
     * @param level     the level the fluid container is contained in
     * @param pos       the block position of the fluid container
     * @param fluid     the fluid to dispense into this fluid container
     * @param splashPos the position in the level to summon splash particles or null
     * @return An interaction result. Success if an action occurred, pass if nothing happened.
     */
    default InteractionResult tryAndFillWithFluid(Level level, BlockPos pos, Fluid fluid, @Nullable Vec3 splashPos)
    {
        FluidContainer container = this.getFluidContainer();
        if(container == null)
            return InteractionResult.PASS;

        // If tank has fluid as does not match the filling fluid, prevent filling
        if(!container.isEmpty() && !container.getStoredFluid().isSame(fluid))
            return InteractionResult.PASS;

        // If tank is full, prevent filling
        if(container.getStoredAmount() >= container.getCapacity())
            return InteractionResult.PASS;

        // Finally push a bucket worth of fluid into the tank
        long filled = container.push(fluid, FluidContainer.BUCKET_CAPACITY, false);
        if(filled > 0)
        {
            // Create splash particle if water
            if(fluid.isSame(Fluids.WATER))
            {
                if(splashPos != null && !level.isClientSide())
                {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SPLASH, splashPos.x, splashPos.y, splashPos.z, 10, 0, 0, 0, 0);
                }
                if(this instanceof BlockEntity entity && entity instanceof IWaterTap && !level.isClientSide())
                {
                    Network.getPlay().sendToTrackingBlockEntity(() -> entity, new MessageWaterTapAnimation(pos));
                    level.playSound(null, pos, ModSounds.BLOCK_KITCHEN_SINK_FILL.get(), SoundSource.BLOCKS);
                }
            }

            SoundEvent event = Services.FLUID.getBucketEmptySound(fluid);
            if(event != null && !level.isClientSide())
            {
                Vec3 soundPos = splashPos != null ? splashPos : new Vec3(pos);
                level.playSound(null, soundPos.x, soundPos.y, soundPos.z, event, SoundSource.BLOCKS);
            }

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * Tries to create obsidian if water is dispensed into this fluid container, and the fluid
     * in the container is lava. If successful, an obsidian item will pop out, and it will consume
     * one bucket worth of lava. If the returned interaction result was successful, it means
     * obsidian was created, otherwise the interaction will simply be passed.
     *
     * @param level           the level the fluid container is contained in
     * @param pos             the block position of the fluid container
     * @param dispensingFluid the fluid that is about to dispense into the fluid container
     * @param spawnPos        the position to spawn the itemstack of obsidian
     * @return An interaction result. Success if obsidian spawned, pass if nothing happened.
     */
    default InteractionResult tryAndCreateObsidian(Level level, BlockPos pos, Fluid dispensingFluid, Vec3 spawnPos)
    {
        FluidContainer container = this.getFluidContainer();
        if(container == null)
            return InteractionResult.PASS;

        // Only allow if dispensing fluid is water
        if(!dispensingFluid.isSame(Fluids.WATER))
            return InteractionResult.PASS;

        if(container.getStoredAmount() < FluidContainer.BUCKET_CAPACITY || !container.getStoredFluid().isSame(Fluids.LAVA))
            return InteractionResult.PASS;

        Pair<Fluid, Long> drained = container.pull(FluidContainer.BUCKET_CAPACITY, true);
        if(drained.right() != FluidContainer.BUCKET_CAPACITY)
            return InteractionResult.PASS;

        if(!level.isClientSide())
        {
            container.pull(FluidContainer.BUCKET_CAPACITY, false);

            ItemEntity entity = new ItemEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, new ItemStack(Blocks.OBSIDIAN));
            entity.setDefaultPickUpDelay();
            level.addFreshEntity(entity);

            level.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);
            level.levelEvent(LevelEvent.LAVA_FIZZ, pos, 0);

            if(this instanceof BlockEntity blockEntity)
            {
                Network.getPlay().sendToTrackingBlockEntity(() -> blockEntity, new MessageWaterTapAnimation(pos));
            }
        }

        return InteractionResult.SUCCESS;
    }

    /**
     * Perform a bottle specific interaction on this fluid container. This will handle filling
     * the container with water bottles or pulling water from the fluid container into an empty
     * bottle. If the player is not holding a bottle, the method will simply return a pass result.
     *
     * @param player the player interacting with the fluid container
     * @param hand   the hand used when interacting
     * @param pos    the block position of the fluid container
     * @return An interaction result. Success if an action occurred, pass if nothing happened.
     */
    default InteractionResult interactWithBottle(Player player, InteractionHand hand, BlockPos pos)
    {
        FluidContainer container = this.getFluidContainer();
        if(container == null)
            return InteractionResult.PASS;

        ItemStack heldItem = player.getItemInHand(hand);
        if(heldItem.is(Items.GLASS_BOTTLE))
        {
            if(container.getStoredAmount() >= FluidContainer.BOTTLE_CAPACITY && container.getStoredFluid().isSame(Fluids.WATER))
            {
                Level level = player.level();
                if(!level.isClientSide())
                {
                    container.pull(FluidContainer.BOTTLE_CAPACITY, false);

                    Item bottle = heldItem.getItem();
                    player.setItemInHand(hand, ItemUtils.createFilledResult(heldItem, player, PotionContents.createItemStack(Items.POTION, Potions.WATER)));
                    player.awardStat(Stats.ITEM_USED.get(bottle));
                    player.level().playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    player.level().gameEvent(null, GameEvent.FLUID_PICKUP, pos);
                }
                return InteractionResult.SUCCESS;
            }
        }
        else if(heldItem.is(Items.POTION))
        {
            PotionContents contents = heldItem.get(DataComponents.POTION_CONTENTS);
            if(contents != null && contents.is(Potions.WATER))
            {
                if(container.isEmpty() || container.getStoredAmount() < container.getCapacity() && container.getStoredFluid().isSame(Fluids.WATER))
                {
                    Level level = player.level();
                    if(!level.isClientSide())
                    {
                        container.push(Fluids.WATER, FluidContainer.BOTTLE_CAPACITY, false);

                        Item potion = heldItem.getItem();
                        player.setItemInHand(hand, ItemUtils.createFilledResult(heldItem, player, new ItemStack(Items.GLASS_BOTTLE)));
                        player.awardStat(Stats.ITEM_USED.get(potion));
                        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Performs the platform specific interaction with the fluid container. This method generally handles bucket interactions.
     *
     * @param player the player interacting with the fluid container
     * @param hand   the hand used when interacting
     * @param pos    the block position of the fluid container
     * @param face   the directional face of the block that the interaction occurred on
     * @return An interaction result. Success if an action occurred, pass if nothing happened.
     */
    default InteractionResult performPlatformInteraction(Player player, InteractionHand hand, BlockPos pos, Direction face)
    {
        return Services.FLUID.performInteractionWithBlock(player, hand, player.level(), pos, face);
    }
}
