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
        // TODO 1.21.10 test
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.lookAlong(new Vector3f(0, 1, 0), new Vector3f(0, 0, 1)).normalize();
        this.extractRotatedQuad(state, camera, quaternionf, partialTick);
    }

    /*@Override
    public void render(VertexConsumer consumer, Camera camera, float partialTick)
    {
        Vec3 cameraPos = camera.getPosition();
        float posX = (float) (this.x - cameraPos.x());
        float posY = (float) (this.y - cameraPos.y());
        float posZ = (float) (this.z - cameraPos.z());
        float size = this.getQuadSize(partialTick);
        Vector3f[] vertices = new Vector3f[]{
            new Vector3f(posX - size, posY, posZ - size),
            new Vector3f(posX - size, posY, posZ + size),
            new Vector3f(posX + size, posY, posZ + size),
            new Vector3f(posX + size, posY, posZ - size)
        };
        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int light = this.getLightColor(partialTick);
        consumer.addVertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).setUv(u1, v1).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
        consumer.addVertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).setUv(u1, v0).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
        consumer.addVertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).setUv(u0, v0).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
        consumer.addVertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).setUv(u0, v1).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
    }*/
}
