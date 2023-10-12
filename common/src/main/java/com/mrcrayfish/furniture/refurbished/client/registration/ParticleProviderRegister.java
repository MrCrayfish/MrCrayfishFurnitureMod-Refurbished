package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface ParticleProviderRegister
{
    <T extends ParticleOptions> void apply(ParticleType<T> type, SpriteProvider<T> provider);

    @FunctionalInterface
    interface SpriteProvider<T extends ParticleOptions>
    {
        ParticleProvider<T> apply(SpriteSet sprites);
    }
}
