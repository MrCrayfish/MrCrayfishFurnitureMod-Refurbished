package com.mrcrayfish.furniture.refurbished.client.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Author: MrCrayfish
 */
public abstract class FlatParticle extends SingleQuadParticle
{
    protected final SpriteSet sprites;

    protected FlatParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites)
    {
        super(level, x, y, z, sprites.first());
        this.sprites = sprites;
        this.quadSize = 0.75F;
    }

    @Override
    protected Layer getLayer()
    {
        return Layer.OPAQUE;
    }

    @Override
    public void extract(QuadParticleRenderState state, Camera camera, float partialTick)
    {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.lookAlong(new Vector3f(0, 1, 0), new Vector3f(0, 0, 1)).normalize();
        this.extractRotatedQuad(state, camera, quaternionf, partialTick);
    }
}
