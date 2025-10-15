package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import com.mrcrayfish.furniture.refurbished.client.electricity.WrenchHandler;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    @Inject(method = "renderLevel", at = @At(value = "HEAD"))
    private void refurbishedFurnitureRenderLevelHead(DeltaTracker deltaTracker, CallbackInfo ci)
    {
        WrenchHandler.get().startRenderLevel(deltaTracker.getGameTimeDeltaPartialTick(true));
    }

    @Inject(method = "resize", at = @At(value = "TAIL"))
    private void refurbishedFurnitureOnResize(int width, int height, CallbackInfo ci)
    {
        ElectricityRenderer.get().resize(width, height);
    }
}
