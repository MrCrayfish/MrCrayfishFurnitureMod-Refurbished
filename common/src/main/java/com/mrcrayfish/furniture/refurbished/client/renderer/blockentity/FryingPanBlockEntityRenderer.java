package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.blockentity.FlipAnimation;
import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.FryingPanRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class FryingPanBlockEntityRenderer implements BlockEntityRenderer<FryingPanBlockEntity, FryingPanRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public FryingPanBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public FryingPanRenderState createRenderState()
    {
        return new FryingPanRenderState();
    }

    @Override
    public void extractRenderState(FryingPanBlockEntity entity, FryingPanRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        ItemStack stack = entity.getItem(0);
        if(!stack.isEmpty())
        {
            ItemStackRenderState itemState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.FIXED, entity.getLevel(), null, 0);
            renderState.item = itemState;
        }
        renderState.itemFlipped = entity.isFlipped();
        renderState.itemRotation = entity.getRotation();
        FlipAnimation animation = entity.getAnimation();
        renderState.animationPlaying = animation.isPlaying();
        renderState.animationTime = animation.isPlaying() ? animation.getTime(partialTick) : 0;
    }

    @Override
    public void submit(FryingPanRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        float flipProgress = this.calculateFlipProgress(renderState.animationTime);
        float flipYOffset = 0.75F;
        poseStack.pushPose();
        poseStack.translate(0.5, 0.125 + 0.015625, 0.5);
        poseStack.translate(0, flipProgress * flipYOffset, 0);
        poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
        poseStack.mulPose(Axis.ZP.rotation(Mth.HALF_PI * renderState.itemRotation));
        poseStack.mulPose(Axis.XP.rotation(Mth.PI * -3 * renderState.animationTime));
        poseStack.mulPose(Axis.XP.rotation(!renderState.animationPlaying && renderState.itemFlipped ? Mth.PI : 0));
        poseStack.scale(0.4375F, 0.4375F, 0.4375F);
        renderState.item.submit(poseStack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
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
