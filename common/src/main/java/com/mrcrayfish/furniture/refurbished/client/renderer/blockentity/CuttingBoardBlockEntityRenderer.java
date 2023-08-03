package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlockEntityRenderer implements BlockEntityRenderer<CuttingBoardBlockEntity>
{
    private final ItemRenderer renderer;

    public CuttingBoardBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
    }

    @Override
    public void render(CuttingBoardBlockEntity cuttingBoard, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0625, 0.5);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        this.renderer.renderStatic(cuttingBoard.getItem(0), ItemDisplayContext.NONE, light, overlay, poseStack, source, cuttingBoard.getLevel(), 0);
        poseStack.popPose();
    }
}
