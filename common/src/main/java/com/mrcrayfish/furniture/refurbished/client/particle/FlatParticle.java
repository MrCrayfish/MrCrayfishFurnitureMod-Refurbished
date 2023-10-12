package com.mrcrayfish.furniture.refurbished.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/**
 * Author: MrCrayfish
 */
public abstract class FlatParticle extends TextureSheetParticle
{
    protected final SpriteSet sprites;

    protected FlatParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites)
    {
        super(level, x, y, z);
        this.sprites = sprites;
        this.quadSize = 0.75F;
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
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
        consumer.vertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();

    }
}
