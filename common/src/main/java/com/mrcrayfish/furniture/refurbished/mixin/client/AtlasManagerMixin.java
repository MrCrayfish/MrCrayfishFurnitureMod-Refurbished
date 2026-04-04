package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.CustomSheets;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mixin(AtlasManager.class)
public class AtlasManagerMixin
{
    @Shadow
    @Final
    @Mutable
    private static List<AtlasManager.AtlasConfig> KNOWN_ATLASES;

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void refurbishedFurnitureInit(CallbackInfo ci)
    {
        List<AtlasManager.AtlasConfig> atlases = new ArrayList<>(KNOWN_ATLASES);
        atlases.add(new AtlasManager.AtlasConfig(CustomSheets.TV_CHANNELS_SHEET, Utils.id("tv_channels"), false));
        KNOWN_ATLASES = List.copyOf(atlases); // Restore the immutability of the original map
    }
}
