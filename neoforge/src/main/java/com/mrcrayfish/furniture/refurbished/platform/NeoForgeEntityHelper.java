package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IEntityHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class NeoForgeEntityHelper implements IEntityHelper
{
    @Override
    public boolean isFakePlayer(Player player)
    {
        // TODO figure out what to do about fake player
        return false;
    }

    @Override
    public void spawnFoodParticles(Player player, ItemStack stack)
    {
        player.spawnItemParticles(stack, 10);
    }
}
