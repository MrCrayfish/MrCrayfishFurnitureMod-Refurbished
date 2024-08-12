package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IEntityHelper;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class FabricEntityHelper implements IEntityHelper
{
    @Override
    public boolean isFakePlayer(Player player)
    {
        // Nothing we can do
        return false;
    }

    @Override
    public void spawnFoodParticles(Player player, ItemStack stack)
    {
        player.spawnItemParticles(stack, 10);
    }

    @Override
    public SimpleParticleType createSimpleParticleType(boolean ignoreLimit)
    {
        return FabricParticleTypes.simple(ignoreLimit);
    }
}
