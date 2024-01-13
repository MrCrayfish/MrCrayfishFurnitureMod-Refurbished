package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.blockentity.PlateBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateBlockEntity>
{
    private final ItemRenderer renderer;

    public PlateBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
    }

    @Override
    public void render(PlateBlockEntity plate, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        ItemStack stack = plate.getItem(0);
        if(stack.isEmpty())
            return;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.03125 + 0.015625, 0.5);
        poseStack.mulPose(plate.getPlacedDirection().getRotation());
        poseStack.scale(0.499F, 0.499F, 0.499F);
        this.renderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, poseStack, source, plate.getLevel(), 0);
        poseStack.popPose();
    }
}
