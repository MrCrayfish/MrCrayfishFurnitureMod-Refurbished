package com.mrcrayfish.furniture.refurbished.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class ItemFlushParticle extends Particle
{
    private static final int ANIMATION_LENGTH = 40;

    private final EntityRenderDispatcher dispatcher;
    private final RenderBuffers buffers;
    private final ItemEntity entity;

    public ItemFlushParticle(EntityRenderDispatcher dispatcher, RenderBuffers buffers, ClientLevel level, ItemEntity entity, Vec3 pos)
    {
        super(level, pos.x, pos.y, pos.z);
        this.dispatcher = dispatcher;
        this.buffers = buffers;
        this.entity = entity.copy();
        this.lifetime = ANIMATION_LENGTH;
        this.gravity = 0F;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTick)
    {
        float progress = (this.age + partialTick) / (float) ANIMATION_LENGTH;
        float rotation = progress * progress * 1080F;
        MultiBufferSource.BufferSource source = this.buffers.bufferSource();
        int light = this.dispatcher.getPackedLightCoords(this.entity, partialTick);
        Vec3 pos = new Vec3(this.x, this.y - 0.35 * progress - 0.1, this.z).subtract(camera.getPosition());
        PoseStack stack = new PoseStack();
        stack.translate(pos.x, pos.y, pos.z);
        stack.mulPose(Axis.YP.rotationDegrees(rotation));
        stack.translate(-pos.x, -pos.y, -pos.z);
        this.dispatcher.render(this.entity, pos.x, pos.y, pos.z, this.entity.getYRot(), 0, stack, source, light);
        source.endBatch();
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.CUSTOM;
    }
}
