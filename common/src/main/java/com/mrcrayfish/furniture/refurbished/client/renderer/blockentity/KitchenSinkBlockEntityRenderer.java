package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.block.KitchenSinkBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.FluidEntityRenderState;
import com.mrcrayfish.furniture.refurbished.client.util.SimpleFluidRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkBlockEntityRenderer implements BlockEntityRenderer<KitchenSinkBlockEntity, FluidEntityRenderState>
{
    public KitchenSinkBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public FluidEntityRenderState createRenderState()
    {
        return new FluidEntityRenderState();
    }

    @Override
    public void extractRenderState(KitchenSinkBlockEntity entity, FluidEntityRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        FluidEntityRenderState.extract(renderState, entity, entity.getLevel(), entity.getBlockPos());
        BlockState blockState = entity.getBlockState();
        if(blockState.hasProperty(KitchenSinkBlock.DIRECTION))
        {
            Direction direction = blockState.getValue(KitchenSinkBlock.DIRECTION);
            renderState.box = SimpleFluidRenderer.createRotatedBox(direction, 2, 8, 2, 12, 15, 14);
        }
    }

    @Override
    public void submit(FluidEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        SimpleFluidRenderer.submit(renderState, poseStack, collector);
    }
}
