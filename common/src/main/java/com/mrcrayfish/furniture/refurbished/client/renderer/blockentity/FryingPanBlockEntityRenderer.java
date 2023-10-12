package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.blockentity.FlipAnimation;
import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
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
            float flipProgress = this.calculateFlipProgress(time);
            float flipHeight = 0.75F;
            poseStack.translate(0, flipProgress * flipHeight, 0);
            poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
            poseStack.mulPose(Axis.ZP.rotation(Mth.HALF_PI * fryingPan.getRotation()));
            poseStack.mulPose(Axis.XP.rotation(Mth.PI * -3 * time));
            poseStack.mulPose(Axis.XP.rotation(!animation.isPlaying() && fryingPan.isFlipped() ? Mth.PI : 0));
            poseStack.scale(0.4375F, 0.4375F, 0.4375F);
            this.renderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, poseStack, source, fryingPan.getLevel(), 0);
            poseStack.popPose();
        }
    }

    /**
     * Calculates the progress for the flip animation.
     *
     * @param time the animation time
     * @return the calculated flip progress
     */
    private float calculateFlipProgress(float time)
    {
        if(time <= 0.5)
        {
            time /= 0.5F;
            return 1.0F - (float) Math.pow(1.0F - time, 4);
        }
        time -= 0.5F;
        time /= 0.5F;
        return 1.0F - (time * time * time * time);
    }
}
