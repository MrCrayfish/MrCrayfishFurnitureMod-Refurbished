package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mrcrayfish.furniture.refurbished.client.ToolAnimationRenderer;
import com.mrcrayfish.furniture.refurbished.client.electricity.CachedElectricityNodes;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public class LevelExtractorMixin
{
    @Shadow
    @Nullable
    private ClientLevel level;

    @Inject(method = "extract", at = @At(value = "HEAD"))
    private void refurbished_furniture$StartExtractLevel(DeltaTracker tracker, Camera camera, float deltaPartialTick, CallbackInfo ci)
    {
        if(this.level != null)
        {
            /* Before rendering the level, we need to clear out any cached electricity nodes that
             * are no longer valid, ensuring only the correct nodes and connection are drawn while
             * also preventing a potential memory leak. */
            // TODO dont do this every frame
            ((CachedElectricityNodes) this.level).refurbished_furniture$RemoveInvalidElectricityNodes();

            // Submits tool renders to the storage
            ToolAnimationRenderer.get().submit(this.level, camera.position(), deltaPartialTick);
        }
    }

    @Inject(method = "extract", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", ordinal = 0))
    private void refurbished_furniture$Extract(DeltaTracker deltaTracker, Camera camera, float deltaPartialTick, CallbackInfo ci, @Local(name = "profiler") ProfilerFiller profiler)
    {
        profiler.popPush("refurbished_furniture_electricity");
        ElectricityRenderer.get().extract(camera);
    }
}
