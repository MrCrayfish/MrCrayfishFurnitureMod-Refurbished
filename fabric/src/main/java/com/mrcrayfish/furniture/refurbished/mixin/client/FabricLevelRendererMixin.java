package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mrcrayfish.furniture.refurbished.client.renderer.electricity.ElectricityRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class FabricLevelRendererMixin
{
    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", ordinal = 0))
    private void refurbishedFurnitureExtract(GraphicsResourceAllocator graphicsResourceAllocator, DeltaTracker deltaTracker, boolean bl, Camera camera, Matrix4f matrix4f, Matrix4f matrix4f2, Matrix4f matrix4f3, GpuBufferSlice gpuBufferSlice, Vector4f vector4f, boolean bl2, CallbackInfo ci, @Local(ordinal = 0) ProfilerFiller profiler)
    {
        profiler.popPush("refurbished_furniture_electricity");
        ElectricityRenderer.get().extract();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;addPass(Ljava/lang/String;)Lcom/mojang/blaze3d/framegraph/FramePass;", ordinal = 0))
    private void refurbishedFurnitureSetupFrameGraph(GraphicsResourceAllocator graphicsResourceAllocator, DeltaTracker deltaTracker, boolean bl, Camera camera, Matrix4f matrix4f, Matrix4f matrix4f2, Matrix4f matrix4f3, GpuBufferSlice gpuBufferSlice, Vector4f vector4f, boolean bl2, CallbackInfo ci, @Local(ordinal = 0) FrameGraphBuilder builder)
    {
        ElectricityRenderer.get().setupFramePass(builder, camera.getPosition());
    }

    @Inject(method = "renderLevel", at = @At(value = "RETURN"))
    private void refurbishedFurnitureRenderLevel(GraphicsResourceAllocator p_361796_, DeltaTracker p_348530_, boolean p_109603_, Camera camera, Matrix4f projMatrix, Matrix4f p_323920_, Matrix4f p_449678_, GpuBufferSlice p_425977_, Vector4f p_425544_, boolean p_426302_, CallbackInfo ci)
    {
        ElectricityRenderer.get().blitToScreen();
    }

    @Inject(method = "method_62214", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;checkPoseStack(Lcom/mojang/blaze3d/vertex/PoseStack;)V", ordinal = 0))
    private void refurbishedFurnitureRenderPowerableArea(GpuBufferSlice slice, LevelRenderState renderState, ProfilerFiller profilerFiller, Matrix4f projMatrix, ResourceHandle resourcehandle2, ResourceHandle resourcehandle3, boolean p_363964_, Frustum p_366590_, ResourceHandle resourcehandle1, ResourceHandle resourcehandle, CallbackInfo ci)
    {
        ElectricityRenderer.get().renderPowerableArea(renderState.cameraRenderState.pos);
    }
}
