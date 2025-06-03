package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mrcrayfish.furniture.refurbished.client.DeferredElectricRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    /*
     * When Iris is installed, deferred electricity rendering calls need to blit to screen
     * after the Iris rendering pipeline finishes, which just happens slightly before the return.
     */
    @Inject(method = "renderLevel", at = @At(value = "RETURN"))
    private void refurbishedFurnitureRenderLevel(GraphicsResourceAllocator allocator, DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, Matrix4f projMatrix, Matrix4f matrix4f2, CallbackInfo ci)
    {
        DeferredElectricRenderer renderer = DeferredElectricRenderer.get();
        if(renderer.isIrisShadersEnabled())
        {
            renderer.blitToScreen(projMatrix, camera);
        }
    }
}
