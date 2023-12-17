package com.mrcrayfish.furniture.refurbished.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.SimpleParticleType;

/**
 * Author: MrCrayfish
 */
public class TapWaterParticle extends WaterDropParticle
{
    protected TapWaterParticle(ClientLevel level, double x, double y, double z)
    {
        super(level, x, y, z);
        this.xd *= 3;
        this.yd = -0.1;
        this.zd *= 3;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites)
        {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd)
        {
            TapWaterParticle particle = new TapWaterParticle(level, x, y, z);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}
