package com.mrcrayfish.furniture.refurbished.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

/**
 * Author: MrCrayfish
 */
public class SuperBounceParticle extends FlatParticle
{
    protected SuperBounceParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites)
    {
        super(level, x, y, z, sprites);
        this.hasPhysics = false;
        this.quadSize = 0.75F;
        this.setLifetime(6);
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick()
    {
        super.tick();
        this.setSpriteFromAge(this.sprites);
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
            return new SuperBounceParticle(level, x, y, z, this.sprites);
        }
    }
}
