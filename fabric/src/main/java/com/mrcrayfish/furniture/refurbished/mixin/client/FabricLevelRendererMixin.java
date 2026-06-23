package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC 26.2 renamed LevelRenderer#renderLevel to #render (now taking a pre-extracted CameraRenderState
// instead of a raw Camera) and moved the block-outline-cancel + electricity camera-extract hooks to
// the new LevelExtractor class - see FabricLevelExtractorMixin for those.
@Mixin(LevelRenderer.class)
public class FabricLevelRendererMixin
{
    @Shadow
    @Final
    private LevelRenderState levelRenderState;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;addPass(Ljava/lang/String;)Lcom/mojang/blaze3d/framegraph/FramePass;", ordinal = 0))
    private void refurbished_furniture$SetupFrameGraph(GraphicsResourceAllocator resourceAllocator, DeltaTracker deltaTracker, boolean renderOutline, CameraRenderState cameraState, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, CallbackInfo ci, @Local(ordinal = 0) FrameGraphBuilder builder)
    {
        ElectricityRenderer.get().setupFramePass(builder, cameraState.pos);
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void refurbished_furniture$AfterRenderLevel(GraphicsResourceAllocator resourceAllocator, DeltaTracker deltaTracker, boolean renderOutline, CameraRenderState cameraState, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, CallbackInfo ci)
    {
        ElectricityRenderer.get().blitToScreen();
    }

    @Inject(method = "lambda$addWeatherPass$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldBorderRenderer;render(Lnet/minecraft/client/renderer/state/level/WorldBorderRenderState;Lnet/minecraft/world/phys/Vec3;DD)V"))
    private void refurbished_furniture$RenderPowerableArea(GpuBufferSlice fog, int renderDistance, CallbackInfo ci)
    {
        ElectricityRenderer.get().renderPowerableArea(this.levelRenderState.cameraRenderState.pos);
    }

    // MC 26.2 removed MultiBufferSource/manual endBatch entirely - the deferred SubmitNodeCollector
    // pipeline flushes RenderTypes automatically, so this manual flush (formerly needed before the
    // pose-stack validation check) is obsolete and was removed along with its injection.
}
