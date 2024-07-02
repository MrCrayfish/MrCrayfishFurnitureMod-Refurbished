package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.ComputerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

/**
 * Author: MrCrayfish
 */
public class ComputerBlockEntityRenderer implements BlockEntityRenderer<ComputerBlockEntity>
{
    public ComputerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ComputerBlockEntity computer, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        if(computer.isNodePowered())
        {
            poseStack.pushPose();

            // Setup rotations
            Direction direction = computer.getBlockState().getValue(CuttingBoardBlock.DIRECTION);
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YN.rotation(Mth.HALF_PI * direction.get2DDataValue()));
            poseStack.translate(-0.5, 0, -0.345);

            // Draw tv screen quad with current channel
            /*Material channelMaterial = CustomSheets.getTelevisionChannelMaterial(computer.getCurrentChannel().id());
            Matrix4f matrix = poseStack.last().pose();
            VertexConsumer consumer = channelMaterial.buffer(source, ClientServices.PLATFORM::getTelevisionScreenRenderType);
            float offset = 0.003125F;
            consumer.vertex(matrix, 0.75F + offset, 0.625F + offset, 0).color(255, 255, 255, 255).uv(0, 0).uv2(0xF000F0).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 0.75F + offset, 0.1875F - offset, 0).color(255, 255, 255, 255).uv(0, 1).uv2(0xF000F0).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 0.25F - offset, 0.1875F - offset, 0).color(255, 255, 255, 255).uv(1, 1).uv2(0xF000F0).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 0.25F - offset, 0.625F + offset, 0).color(255, 255, 255, 255).uv(1, 0).uv2(0xF000F0).normal(0, 1, 0).endVertex();
            */
            poseStack.popPose();
        }

        ElectricBlockEntityRenderer.drawNodeAndConnections(computer);
    }
}
