package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.CustomSheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

/**
 * Author: MrCrayfish
 */
@Mixin(ModelBakery.class)
public class ModelBakeryMixin
{
    @Mutable
    @Shadow
    @Final
    private static Set<Material> UNREFERENCED_TEXTURES;

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void refurbishedFurnitureInit(CallbackInfo ci)
    {
        UNREFERENCED_TEXTURES.addAll(CustomSheets.TV_CHANNEL_MATERIALS.values());
    }
}
