package com.mrcrayfish.furniture.refurbished.mixin.client;

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
    private void refurbished_furniture$RenderLevelHead(DeltaTracker deltaTracker, CallbackInfo ci)
    {
        WrenchHandler.get().startRenderLevel(deltaTracker.getGameTimeDeltaPartialTick(true));
    }
}
