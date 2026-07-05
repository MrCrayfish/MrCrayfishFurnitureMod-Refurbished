package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    @SuppressWarnings("NameDoesntMatchTargetClass")
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;addPass(Ljava/lang/String;)Lcom/mojang/blaze3d/framegraph/FramePass;", ordinal = 0))
    private void refurbished_furniture$AddFramePass(GraphicsResourceAllocator resourceAllocator, DeltaTracker deltaTracker, boolean renderOutline, CameraRenderState cameraState, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, CallbackInfo ci, @Local(name = "frame") FrameGraphBuilder frameGraphBuilder, @Local(name = "featureFrame") FeatureRenderDispatcher.PreparedFrame preparedFrame)
    {
        ElectricityRenderer.get().addFramePass(frameGraphBuilder, preparedFrame);
    }

    @Inject(method = "submitFeatures", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;finalizeGizmoCollection()V"))
    private void refurbished_furniture$SubmitElectricity(LevelRenderState levelRenderState, SubmitNodeCollector submitNodeCollector, boolean renderOutline, CallbackInfo ci)
    {
        ElectricityRenderer.get().submit(levelRenderState, submitNodeCollector);
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void refurbished_furniture$AfterRenderLevel(GraphicsResourceAllocator resourceAllocator, DeltaTracker deltaTracker, boolean renderOutline, CameraRenderState cameraState, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, CallbackInfo ci)
    {
        ElectricityRenderer.get().blitToScreen();
    }

    @Inject(method = "resize", at = @At(value = "TAIL"))
    private void refurbished_furniture$Resize(int width, int height, CallbackInfo ci)
    {
        ElectricityRenderer.get().resize(width, height);
    }

    @Inject(method = "close", at = @At(value = "TAIL"))
    private void refurbished_furniture$Close(CallbackInfo ci)
    {
        ElectricityRenderer.get().close();
    }
}
