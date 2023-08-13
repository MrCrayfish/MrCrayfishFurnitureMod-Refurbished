package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.blockentity.FlipAnimation;
import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
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
public class FryingPanBlockEntityRenderer implements BlockEntityRenderer<FryingPanBlockEntity>
{
    private final ItemRenderer renderer;

    public FryingPanBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
    }

    @Override
    public void render(FryingPanBlockEntity fryingPan, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        ItemStack stack = fryingPan.getItem(0);
        if(!stack.isEmpty())
        {
            poseStack.pushPose();
            FlipAnimation animation = fryingPan.getAnimation();
            float time = animation.isPlaying() ? animation.getTime(partialTick) : 0;
            poseStack.translate(0.5, 0.125 + 0.015625, 0.5);
            poseStack.translate(0, Mth.sin(Mth.PI * time), 0);
            poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
            poseStack.mulPose(Axis.ZP.rotation(Mth.HALF_PI * fryingPan.getRotation()));
            poseStack.mulPose(Axis.XP.rotation(Mth.PI * -3 * time));
            poseStack.mulPose(Axis.XP.rotation(fryingPan.isFlipped() ? Mth.PI : 0));
            poseStack.scale(0.4375F, 0.4375F, 0.4375F);
            this.renderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, poseStack, source, fryingPan.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
