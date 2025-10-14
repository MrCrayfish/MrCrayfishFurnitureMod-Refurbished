package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.electricity.LinkHandler;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class ElectricBlockEntityRenderer<T extends BlockEntity & IElectricityNode> implements BlockEntityRenderer<T, BlockEntityRenderState>
{
    public ElectricBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public BlockEntityRenderState createRenderState()
    {
        return new BlockEntityRenderState();
    }

    @Override
    public void submit(BlockEntityRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {}

    @Override
    public boolean shouldRender(T entity, Vec3 camera)
    {
        return LinkHandler.isHoldingWrench() && BlockEntityRenderer.super.shouldRender(entity, camera);
    }

    @Override
    public boolean shouldRenderOffScreen()
    {
        return true;
    }

    @Override
    public int getViewDistance()
    {
        return Config.CLIENT.electricityViewDistance.get();
    }

    // @Override (from NeoForge's IBlockEntityRendererExtension)
    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox(T node)
    {
        return new AABB(node.getNodePosition()).inflate(Config.CLIENT.electricityViewDistance.get());
    }
}
