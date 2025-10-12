package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.block.StorageJarBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.StorageJarBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.StorageJarRenderState;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class StorageJarRenderer implements BlockEntityRenderer<StorageJarBlockEntity, StorageJarRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public StorageJarRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public StorageJarRenderState createRenderState()
    {
        return new StorageJarRenderState();
    }

    @Override
    public void extractRenderState(StorageJarBlockEntity entity, StorageJarRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        renderState.direction = entity.getBlockState().getValueOrElse(StorageJarBlock.DIRECTION, Direction.NORTH);
        renderState.items = new ItemStackRenderState[entity.getContainerSize()];
        for(int i = 0; i < entity.getContainerSize(); i++)
        {
            ItemStack stack = entity.getItem(i);
            if(!stack.isEmpty())
            {
                if(renderState.label == null)
                {
                    renderState.label = stack.getHoverName();
                }
                ItemStackRenderState itemState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.NONE, entity.getLevel(), null, 0);
                renderState.items[i] = itemState;
            }
        }
    }

    @Override
    public void submit(StorageJarRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        if(renderState.items == null || renderState.items.length == 0)
            return;

        stack.pushPose();
        stack.translate(0.5, 0.015625, 0.5);
        stack.scale(0.499F, 0.499F, 0.499F);
        for(int i = 0; i < renderState.items.length; i++)
        {
            ItemStackRenderState itemState = renderState.items[i];
            if(itemState == null)
                continue;

            // Draw item
            stack.pushPose();
            stack.mulPose(renderState.direction.getRotation());
            stack.mulPose(Axis.YP.rotation(Mth.PI));
            itemState.submit(stack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            stack.popPose();

            // Translate and rotate for next item
            stack.translate(0, 0.0625F, 0);
            stack.mulPose(Axis.YP.rotation(Mth.HALF_PI / 2.01F));
        }
        stack.popPose();

        if(renderState.label != null)
        {
            // TODO 1.21.10 figure this out
            collector.submitNameTag(stack, new Vec3(0, 0, 0), 0, renderState.label, false, 0, 0, cameraState);
        }
    }
}
