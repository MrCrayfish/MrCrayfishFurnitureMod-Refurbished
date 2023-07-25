package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.Fluids;
import org.joml.Matrix4f;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkBlockEntityRenderer implements BlockEntityRenderer<KitchenSinkBlockEntity>
{
    public KitchenSinkBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(KitchenSinkBlockEntity sink, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        sink.getFluid().ifPresent(fluid ->
        {
            TextureAtlasSprite[] sprites = ClientServices.PLATFORM.getFluidSprites(fluid, sink.getLevel(), sink.getBlockPos(), fluid.defaultFluidState());
            TextureAtlasSprite still = sprites[0];
            int colour = fluid.isSame(Fluids.LAVA) ? 0xFFFFFF : BiomeColors.getAverageWaterColor(sink.getLevel(), sink.getBlockPos());
            float red = FastColor.ARGB32.red(colour) / 255F;
            float green = FastColor.ARGB32.green(colour) / 255F;
            float blue = FastColor.ARGB32.blue(colour) / 255F;
            float height = 0.5F + 0.4325F * ((float) sink.getFluidAmount() / sink.getTankCapacity());
            RenderType type = RenderType.translucent();
            VertexConsumer consumer = source.getBuffer(type);
            Matrix4f matrix = poseStack.last().pose();
            consumer.vertex(matrix, 0, height, 0).color(red, green, blue, 1).uv(still.getU1(), still.getV0()).uv2(light).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 0, height, 1).color(red, green, blue, 1).uv(still.getU0(), still.getV0()).uv2(light).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 1, height, 1).color(red, green, blue, 1).uv(still.getU0(), still.getV1()).uv2(light).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, 1, height, 0).color(red, green, blue, 1).uv(still.getU1(), still.getV1()).uv2(light).normal(0, 1, 0).endVertex();
        });
    }
}
