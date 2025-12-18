package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.block.BasinBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.BathBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.FluidEntityRenderState;
import com.mrcrayfish.furniture.refurbished.client.util.SimpleFluidRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class BathBlockEntityRenderer implements BlockEntityRenderer<BathBlockEntity, FluidEntityRenderState>
{
    public BathBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public FluidEntityRenderState createRenderState()
    {
        return new FluidEntityRenderState();
    }

    @Override
    public void extractRenderState(BathBlockEntity entity, FluidEntityRenderState state, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTick, camera, overlay);
        FluidEntityRenderState.extract(state, entity, entity.getLevel(), entity.getBlockPos());
        BlockState blockState = entity.getBlockState();
        if(blockState.hasProperty(BasinBlock.DIRECTION))
        {
            state.box = this.createBathFluidBox(entity, blockState.getValue(BasinBlock.DIRECTION));
        }
    }

    private AABB createBathFluidBox(BathBlockEntity bath, Direction direction)
    {
        if(bath.isHead())
        {
            return SimpleFluidRenderer.createRotatedBox(direction, 0, 4, 2, 12, 15, 14);
        }
        return SimpleFluidRenderer.createRotatedBox(direction, 2, 4, 2, 16, 15, 14);
    }

    @Override
    public void submit(FluidEntityRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        SimpleFluidRenderer.submit(renderState, stack, collector);
    }
}
