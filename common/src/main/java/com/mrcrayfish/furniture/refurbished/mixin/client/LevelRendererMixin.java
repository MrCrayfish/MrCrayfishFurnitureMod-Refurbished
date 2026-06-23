package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.ToolAnimationRenderer;
import com.mrcrayfish.furniture.refurbished.client.electricity.CachedElectricityNodes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    @Shadow
    @Nullable
    private ClientLevel level;

    @Inject(method = "extractLevel", at = @At(value = "HEAD"))
    private void refurbished_furniture$StartExtractLevel(CallbackInfo ci)
    {
        if(this.level != null)
        {
            /* Before rendering the level, we need to clear out any cached electricity nodes that
             * are no longer valid, ensuring only the correct nodes and connection are drawn while
             * also preventing a potential memory leak. */
            // TODO dont do this every frame
            ((CachedElectricityNodes) this.level).refurbished_furniture$RemoveInvalidElectricityNodes();
        }
    }

    // MC 26.2 removed the public SubmitNodeStorage accessor this mod used to stash tool animation
    // draws into during extractLevel; submitBlockEntities is the level-render submission phase that
    // actually has a SubmitNodeCollector available, so tool animations are submitted directly here instead.
    @Inject(method = "submitBlockEntities", at = @At(value = "HEAD"))
    private void refurbished_furniture$SubmitToolAnimations(PoseStack poseStack, LevelRenderState renderState, SubmitNodeCollector collector, CallbackInfo ci)
    {
        if(this.level != null)
        {
            float partialTick = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);
            ToolAnimationRenderer.get().submit(this.level, renderState.cameraRenderState.pos, collector, partialTick);
        }
    }
}
