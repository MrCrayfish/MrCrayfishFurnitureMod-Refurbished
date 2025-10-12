package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.DoorMatBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.DoorMatBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.DoorMatRenderState;
import com.mrcrayfish.furniture.refurbished.image.TextureCache;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlockEntityRenderer implements BlockEntityRenderer<DoorMatBlockEntity, DoorMatRenderState>
{
    public DoorMatBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public DoorMatRenderState createRenderState()
    {
        return new DoorMatRenderState();
    }

    @Override
    public void extractRenderState(DoorMatBlockEntity entity, DoorMatRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        renderState.direction = entity.getBlockState().getValueOrElse(DoorMatBlock.DIRECTION, Direction.NORTH);
        renderState.renderType = TextureCache.get().getRenderType(entity);
    }

    @Override
    public void submit(DoorMatRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        if(renderState.renderType == null)
            return;

        stack.translate(0.5, 0, 0.5);
        stack.mulPose(Axis.YN.rotation(Mth.HALF_PI * renderState.direction.get2DDataValue()));
        stack.mulPose(Axis.YP.rotation(Mth.PI));
        stack.translate(-0.5, 0, -0.5);
        collector.submitCustomGeometry(stack, renderState.renderType, (pose, consumer) -> {
            consumer.addVertex(pose, 0.0625F, 0.063F, 0.1875F).setColor(255, 255, 255, 255).setUv(0, 0).setLight(renderState.lightCoords).setNormal(0, 1, 0);
            consumer.addVertex(pose, 0.0625F, 0.063F, 0.8125F).setColor(255, 255, 255, 255).setUv(0, 1).setLight(renderState.lightCoords).setNormal(0, 1, 0);
            consumer.addVertex(pose, 0.9375F, 0.063F, 0.8125F).setColor(255, 255, 255, 255).setUv(1, 1).setLight(renderState.lightCoords).setNormal(0, 1, 0);
            consumer.addVertex(pose, 0.9375F, 0.063F, 0.1875F).setColor(255, 255, 255, 255).setUv(1, 0).setLight(renderState.lightCoords).setNormal(0, 1, 0);
        });
    }

    @Override
    public int getViewDistance()
    {
        return 24;
    }
}
