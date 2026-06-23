package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.ToolAnimationRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC 26.2 removed LevelRenderer's `level` field entirely; there is no per-frame cached level
// reference on LevelRenderer anymore, so read it from Minecraft.getInstance() directly.
// See LevelExtractorMixin for the electricity-node cleanup that used to live alongside this in
// the now-removed extractLevel method (that responsibility moved to a separate LevelExtractor class).
@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    // MC 26.2 removed the public SubmitNodeStorage accessor this mod used to stash tool animation
    // draws into during extractLevel; submitBlockEntities is the level-render submission phase that
    // actually has a SubmitNodeCollector available, so tool animations are submitted directly here.
    @Inject(method = "submitBlockEntities", at = @At(value = "HEAD"))
    private void refurbished_furniture$SubmitToolAnimations(PoseStack poseStack, LevelRenderState renderState, SubmitNodeCollector collector, CallbackInfo ci)
    {
        ClientLevel level = Minecraft.getInstance().level;
        if(level != null)
        {
            float partialTick = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);
            ToolAnimationRenderer.get().submit(level, renderState.cameraRenderState.pos, collector, partialTick);
        }
    }
}
