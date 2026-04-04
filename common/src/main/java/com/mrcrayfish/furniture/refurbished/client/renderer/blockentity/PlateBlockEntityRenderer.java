package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.blockentity.PlateBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.PlateRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateBlockEntity, PlateRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public PlateBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public PlateRenderState createRenderState()
    {
        return new PlateRenderState();
    }

    @Override
    public void extractRenderState(PlateBlockEntity entity, PlateRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);

        ItemStack stack = entity.getItem(0);
        if(stack.isEmpty())
            return;

        ItemStackRenderState itemState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.FIXED, entity.getLevel(), null, 0);
        renderState.item = itemState;
        renderState.direction = entity.getPlacedDirection();
    }

    @Override
    public void submit(PlateRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        if(renderState.item != null)
        {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.03125 + 0.015625, 0.5);
            poseStack.mulPose(renderState.direction.getRotation());
            poseStack.scale(0.499F, 0.499F, 0.499F);
            renderState.item.submit(poseStack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
