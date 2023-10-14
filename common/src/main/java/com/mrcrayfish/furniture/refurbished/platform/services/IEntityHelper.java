package com.mrcrayfish.furniture.refurbished.platform.services;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public interface IEntityHelper
{
    boolean isFakePlayer(Player player);

    void spawnFoodParticles(Player player, ItemStack stack);
}
