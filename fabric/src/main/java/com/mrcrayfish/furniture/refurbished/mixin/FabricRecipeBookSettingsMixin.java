package com.mrcrayfish.furniture.refurbished.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mrcrayfish.furniture.refurbished.FurnitureMod;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookTypes;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
@Mixin(RecipeBookSettings.class)
public class FabricRecipeBookSettingsMixin
{
    @Shadow
    @Final
    @Mutable
    private static Map<RecipeBookType, Pair<String, String>> TAG_FIELDS;

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void refurbishedFurnitureRecipeBookSettingInit(CallbackInfo ci)
    {
        Map<RecipeBookType, Pair<String, String>> map = new HashMap<>(TAG_FIELDS);
        map.put(ModRecipeBookTypes.FREEZER.get(), Pair.of("isRefurbishedFurnitureFreezerGuiOpen", "isRefurbishedFurnitureFreezerFilteringCraftable"));
        map.put(ModRecipeBookTypes.MICROWAVE.get(), Pair.of("isRefurbishedFurnitureMicrowaveGuiOpen", "isRefurbishedFurnitureMicrowaveFilteringCraftable"));
        map.put(ModRecipeBookTypes.OVEN.get(), Pair.of("isRefurbishedFurnitureOvenGuiOpen", "isRefurbishedFurnitureOvenFilteringCraftable"));
        TAG_FIELDS = ImmutableMap.copyOf(map); // Update and respect immutability of original map
    }
}
