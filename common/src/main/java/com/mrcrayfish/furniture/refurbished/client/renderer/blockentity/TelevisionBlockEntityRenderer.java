package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.CustomSheets;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

/**
 * Author: MrCrayfish
 */
public class TelevisionBlockEntityRenderer implements BlockEntityRenderer<TelevisionBlockEntity>
{
    public TelevisionBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TelevisionBlockEntity television, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay, Vec3 camera)
    {
        if(television.isNodePowered())
        {
            poseStack.pushPose();

            // Setup rotations
            Direction direction = television.getBlockState().getValue(CuttingBoardBlock.DIRECTION);
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YN.rotation(Mth.HALF_PI * direction.get2DDataValue()));
            poseStack.translate(-0.5, 0, -0.345);

            // Draw tv screen quad with current channel
            float offset = 0.003125F;
            Matrix4f matrix = poseStack.last().pose();
            Material channelMaterial = CustomSheets.getTelevisionChannelMaterial(television.getCurrentChannel().id());
            VertexConsumer consumer = channelMaterial.buffer(source, ClientServices.PLATFORM::getTelevisionScreenRenderType);

            Vec3i normal = direction.getUnitVec3i();

            // No method chaining due to bug in SpriteCoordinateExpander
            consumer.addVertex(matrix, 0.75F + offset, 0.625F + offset, 0);
            consumer.setColor(255, 255, 255, 255);
            consumer.setUv(0, 0);
            consumer.setLight(0xF000F0);
            consumer.setNormal(normal.getX(), normal.getY(), normal.getZ());
            consumer.addVertex(matrix, 0.75F + offset, 0.1875F - offset, 0);
            consumer.setColor(255, 255, 255, 255);
            consumer.setUv(0, 1);
            consumer.setLight(0xF000F0);
            consumer.setNormal(normal.getX(), normal.getY(), normal.getZ());
            consumer.addVertex(matrix, 0.25F - offset, 0.1875F - offset, 0);
            consumer.setColor(255, 255, 255, 255);
            consumer.setUv(1, 1);
            consumer.setLight(0xF000F0);
            consumer.setNormal(normal.getX(), normal.getY(), normal.getZ());
            consumer.addVertex(matrix, 0.25F - offset, 0.625F + offset, 0);
            consumer.setColor(255, 255, 255, 255);
            consumer.setUv(1, 0);
            consumer.setLight(0xF000F0);
            consumer.setNormal(normal.getX(), normal.getY(), normal.getZ());

            poseStack.popPose();
        }

        ElectricBlockEntityRenderer.drawNodeAndConnections(television);
    }
}
