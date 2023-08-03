package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.block.ToasterBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
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
        Direction direction = cuttingBoard.getBlockState().getValue(CuttingBoardBlock.DIRECTION);
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0625 + 0.015625, 0.5);
        poseStack.mulPose(direction.getRotation());
        poseStack.mulPose(Axis.YP.rotation(Mth.PI));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        this.renderer.renderStatic(cuttingBoard.getItem(0), ItemDisplayContext.NONE, light, overlay, poseStack, source, cuttingBoard.getLevel(), 0);
        poseStack.popPose();
    }
}
