package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.particles.SimpleParticleType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModParticleTypes
{
    public static final RegistryEntry<SimpleParticleType> BOUNCE = RegistryEntry.particleType(Utils.id("bounce"), () -> Services.ENTITY.createSimpleParticleType(true));
    public static final RegistryEntry<SimpleParticleType> SUPER_BOUNCE = RegistryEntry.particleType(Utils.id("super_bounce"), () -> Services.ENTITY.createSimpleParticleType(true));
    public static final RegistryEntry<SimpleParticleType> STEAM = RegistryEntry.particleType(Utils.id("steam"), () -> Services.ENTITY.createSimpleParticleType(true));
    public static final RegistryEntry<SimpleParticleType> TAP_WATER = RegistryEntry.particleType(Utils.id("tap_water"), () -> Services.ENTITY.createSimpleParticleType(true));
}
