package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.CustomSheets;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

/**
 * Author: MrCrayfish
 */
public class TelevisionBlockEntityRenderer implements BlockEntityRenderer<TelevisionBlockEntity>
{
    public TelevisionBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TelevisionBlockEntity television, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        if(television.isNodePowered())
        {
            poseStack.pushPose();

            // Setup rotations
            Direction direction = television.getBlockState().getValue(CuttingBoardBlock.DIRECTION);
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Vector3f.YN.rotation(Mth.HALF_PI * direction.get2DDataValue()));
            poseStack.translate(-0.5, 0, -0.345);

            // Draw tv screen quad with current channel
            Material channelMaterial = CustomSheets.getTelevisionChannelMaterial(television.getCurrentChannel().id());
            Matrix4f matrix = poseStack.last().pose();
            VertexConsumer consumer = channelMaterial.buffer(source, ClientServices.PLATFORM::getTelevisionScreenRenderType);
            float offset = 0.003125F;

            // Weird hack needed for Fabric. Why lol?
            TextureAtlasSprite sprite = channelMaterial.sprite();
            float minU = sprite.getU0();
            float maxU = sprite.getU1();
            float minV = sprite.getV0();
            float maxV = sprite.getV1();

            consumer.vertex(matrix, 0.75F + offset, 0.625F + offset, 0).color(255, 255, 255, 255).uv(minU, minV).uv2(0xF000F0).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 0.75F + offset, 0.1875F - offset, 0).color(255, 255, 255, 255).uv(minU, maxV).uv2(0xF000F0).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 0.25F - offset, 0.1875F - offset, 0).color(255, 255, 255, 255).uv(maxU, maxV).uv2(0xF000F0).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 0.25F - offset, 0.625F + offset, 0).color(255, 255, 255, 255).uv(maxU, minV).uv2(0xF000F0).normal(0, 1, 0).endVertex();

            poseStack.popPose();
        }

        ElectricBlockEntityRenderer.drawNodeAndConnections(television);
    }
}
