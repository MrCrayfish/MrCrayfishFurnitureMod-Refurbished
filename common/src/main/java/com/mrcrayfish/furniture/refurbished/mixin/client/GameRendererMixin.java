package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.Constants;
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
    // TEMPORARY diagnostic throttle for the missing-overlay investigation; remove once resolved.
    private long debugLastLogMillis;

    @Inject(method = "renderLevel", at = @At(value = "HEAD"))
    private void refurbished_furniture$RenderLevelHead(DeltaTracker deltaTracker, CallbackInfo ci)
    {
        if(System.currentTimeMillis() - this.debugLastLogMillis > 1000)
        {
            this.debugLastLogMillis = System.currentTimeMillis();
            Constants.LOG.info("[ElectricityDebug] GameRenderer#renderLevel mixin fired, isHoldingWrench={}", WrenchHandler.isHoldingWrench());
        }
        WrenchHandler.get().startRenderLevel(deltaTracker.getGameTimeDeltaPartialTick(true));
    }

    @Inject(method = "resize", at = @At(value = "TAIL"))
    private void refurbished_furniture$OnWindowResize(int width, int height, CallbackInfo ci)
    {
        ElectricityRenderer.get().resize(width, height);
    }
}
