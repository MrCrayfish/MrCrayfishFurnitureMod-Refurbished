package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.client.CustomSheets;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
@Mixin(ModelManager.class)
public class ModelManagerMixin
{
    @Shadow
    @Final
    @Mutable
    private static Map<ResourceLocation, ResourceLocation> VANILLA_ATLASES;

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void refurbishedFurnitureInit(CallbackInfo ci)
    {
        // Respect the immutability of the original map
        ImmutableMap.Builder<ResourceLocation, ResourceLocation> builder = ImmutableMap.builder();
        builder.putAll(VANILLA_ATLASES);
        builder.put(CustomSheets.TV_CHANNELS_SHEET, Utils.resource("tv_channels"));
        VANILLA_ATLASES = builder.build();
    }
}
