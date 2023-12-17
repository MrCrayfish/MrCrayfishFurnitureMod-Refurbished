package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public interface IFluidHelper
{
    long getBucketCapacity();

    SoundEvent getBucketEmptySound(Fluid fluid);

    FluidContainer createFluidContainer(long capacity, @Nullable Consumer<FluidContainer> onChange);

    InteractionResult performInteractionWithBlock(Player player, InteractionHand hand, Level level, BlockPos pos, Direction face);

    boolean isFluidContainerItem(ItemStack stack);
}
