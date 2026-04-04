package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.ToasterBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.ToasterBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.ToasterRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class ToasterBlockEntityRenderer implements BlockEntityRenderer<ToasterBlockEntity, ToasterRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public ToasterBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public ToasterRenderState createRenderState()
    {
        return new ToasterRenderState();
    }

    @Override
    public void extractRenderState(ToasterBlockEntity entity, ToasterRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        renderState.direction = entity.getBlockState().getValueOrElse(ToasterBlock.DIRECTION, Direction.NORTH);
        for(int i = 0; i < 2; i++)
        {
            ItemStack stack = entity.getItem(i);
            if(!stack.isEmpty())
            {
                ItemStackRenderState itemState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.NONE, entity.getLevel(), null, 0);
                renderState.items[i] = itemState;
            }
        }
        renderState.heating = entity.isHeating();
    }

    @Override
    public void submit(ToasterRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        stack.pushPose();
        stack.translate(0.5, renderState.heating ? 0.375 : 0.4375, 0.5);
        this.drawItem(renderState.items[0], renderState, 1, stack, collector);
        this.drawItem(renderState.items[1], renderState, -1, stack, collector);
        stack.popPose();
    }

    private void drawItem(@Nullable ItemStackRenderState itemState, ToasterRenderState renderState, int offset, PoseStack poseStack, SubmitNodeCollector collector)
    {
        if(itemState != null)
        {
            Vec3i normal = renderState.direction.getUnitVec3i();
            poseStack.pushPose();
            poseStack.translate(0.095 * normal.getX() * offset, 0, 0.095 * normal.getZ() * offset);
            poseStack.mulPose(renderState.direction.getRotation());
            poseStack.mulPose(Axis.XN.rotation(Mth.HALF_PI));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            itemState.submit(poseStack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
