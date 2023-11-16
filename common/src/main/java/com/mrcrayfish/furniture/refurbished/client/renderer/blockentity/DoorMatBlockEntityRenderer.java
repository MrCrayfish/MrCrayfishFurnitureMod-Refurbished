package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.BathBlock;
import com.mrcrayfish.furniture.refurbished.block.DoorMatBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.DoorMatBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.util.SimpleFluidRenderer;
import com.mrcrayfish.furniture.refurbished.image.TextureCache;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlockEntityRenderer implements BlockEntityRenderer<DoorMatBlockEntity>
{
    public DoorMatBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(DoorMatBlockEntity doorMat, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        BlockState state = doorMat.getBlockState();
        if(!state.hasProperty(DoorMatBlock.DIRECTION))
            return;

        ResourceLocation id = TextureCache.get().getOrCacheImage(doorMat);
        if(id != null)
        {
            Direction direction = state.getValue(DoorMatBlock.DIRECTION);
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(direction.getRotation());
            poseStack.mulPose(Axis.YP.rotation(Mth.PI));
            poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
            poseStack.translate(-0.5, 0, -0.5);
            VertexConsumer consumer = source.getBuffer(RenderType.entityCutout(id));
            Matrix4f matrix = poseStack.last().pose();
            consumer.vertex(matrix, 0.0625F, 0.063F, 0.1875F).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, 1).endVertex();
            consumer.vertex(matrix, 0.0625F, 0.063F, 0.8125F).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, 1).endVertex();
            consumer.vertex(matrix, 0.9375F, 0.063F, 0.8125F).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, 1).endVertex();
            consumer.vertex(matrix, 0.9375F, 0.063F, 0.1875F).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 0, 1).endVertex();
        }
    }
}
