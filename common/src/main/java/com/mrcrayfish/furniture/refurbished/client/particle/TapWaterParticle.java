package com.mrcrayfish.furniture.refurbished.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/**
 * Author: MrCrayfish
 */
public class TapWaterParticle extends WaterDropParticle
{
    protected TapWaterParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite)
    {
        super(level, x, y, z, sprite);
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
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd, RandomSource source)
        {
            return new TapWaterParticle(level, x, y, z, this.sprites.get(source));
        }
    }
}
