package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.block.BasinBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.BasinBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.FluidEntityRenderState;
import com.mrcrayfish.furniture.refurbished.client.util.SimpleFluidRenderer;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.EmptyBlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class BasinBlockEntityRenderer implements BlockEntityRenderer<BasinBlockEntity, FluidEntityRenderState>
{
    public BasinBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public FluidEntityRenderState createRenderState()
    {
        return new FluidEntityRenderState();
    }

    @Override
    public void extractRenderState(BasinBlockEntity entity, FluidEntityRenderState state, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        // TODO 1.21.10 test
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTick, camera, overlay);
        FluidEntityRenderState.extract(state, entity, entity.getLevel(), entity.getBlockPos()); // TODO might be reusing the state, test 1.21.10
        BlockState blockState = entity.getBlockState();
        if(blockState.hasProperty(BasinBlock.DIRECTION))
        {
            Direction direction = blockState.getValue(BasinBlock.DIRECTION);
            state.box = SimpleFluidRenderer.createRotatedBox(direction, 4, 13, 2, 12, 15, 14);
        }
    }

    @Override
    public void submit(FluidEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        SimpleFluidRenderer.submit(renderState, poseStack, collector);
    }
}
