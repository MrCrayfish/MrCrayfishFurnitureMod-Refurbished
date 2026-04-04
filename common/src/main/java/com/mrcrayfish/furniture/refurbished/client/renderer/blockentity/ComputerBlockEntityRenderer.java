package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.block.ComputerBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.ComputerBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.ComputerRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class ComputerBlockEntityRenderer implements BlockEntityRenderer<ComputerBlockEntity, ComputerRenderState>
{
    public ComputerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public ComputerRenderState createRenderState()
    {
        return new ComputerRenderState();
    }

    @Override
    public void extractRenderState(ComputerBlockEntity entity, ComputerRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        renderState.powered = entity.isNodePowered();
        renderState.direction = entity.getBlockState().getValueOrElse(ComputerBlock.DIRECTION, Direction.NORTH);
    }

    @Override
    public void submit(ComputerRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {

    }
}
