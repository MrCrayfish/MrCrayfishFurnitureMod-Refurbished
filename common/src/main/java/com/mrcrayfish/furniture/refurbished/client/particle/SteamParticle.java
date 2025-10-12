package com.mrcrayfish.furniture.refurbished.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/**
 * Author: MrCrayfish
 */
public class SteamParticle extends SingleQuadParticle
{
    protected final SpriteSet sprites;

    protected SteamParticle(ClientLevel level, double x, double y, double z, double yd, SpriteSet sprites)
    {
        super(level, x, y, z, sprites.first());
        this.sprites = sprites;
        this.setLifetime(30);
        this.setSpriteFromAge(sprites);
        this.setSize(0.0625F, 0.0625F);
        this.yd = yd;
    }

    @Override
    public void tick()
    {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    protected Layer getLayer()
    {
        return Layer.TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites)
        {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd, RandomSource source)
        {
            return new SteamParticle(level, x, y, z, yd, this.sprites);
        }
    }
}
