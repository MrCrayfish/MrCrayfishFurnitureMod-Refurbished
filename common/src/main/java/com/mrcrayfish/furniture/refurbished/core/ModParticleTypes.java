package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.particles.SimpleParticleType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModParticleTypes
{
    public static final RegistryEntry<SimpleParticleType> BOUNCE = RegistryEntry.particleType(Utils.resource("bounce"), () -> ClientServices.PLATFORM.createSimpleParticleType(true));
    public static final RegistryEntry<SimpleParticleType> SUPER_BOUNCE = RegistryEntry.particleType(Utils.resource("super_bounce"), () -> ClientServices.PLATFORM.createSimpleParticleType(true));
}
