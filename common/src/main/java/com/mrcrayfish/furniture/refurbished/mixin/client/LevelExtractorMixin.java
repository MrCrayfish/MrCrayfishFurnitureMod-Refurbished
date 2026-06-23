package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.electricity.CachedElectricityNodes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.extract.LevelExtractor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC 26.2 split the old per-frame extraction phase out of LevelRenderer entirely into this new
// LevelExtractor class, which now owns the `level` field and `extract(DeltaTracker, Camera, float)`
// entry point that LevelRenderer#extractLevel used to provide.
@Mixin(LevelExtractor.class)
public class LevelExtractorMixin
{
    @Shadow
    @Nullable
    private ClientLevel level;

    @Inject(method = "extract", at = @At(value = "HEAD"))
    private void refurbished_furniture$StartExtract(CallbackInfo ci)
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
}
