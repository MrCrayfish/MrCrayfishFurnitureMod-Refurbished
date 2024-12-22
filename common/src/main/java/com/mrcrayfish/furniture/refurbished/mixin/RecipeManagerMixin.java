package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.core.ModRecipePropertySets;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.MicrowaveHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipePropertySet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@Mixin(RecipeManager.class)
public class RecipeManagerMixin
{
    @Shadow
    @Final
    @Mutable
    private static Map<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> RECIPE_PROPERTY_SETS;

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void afterStaticInit(CallbackInfo ci)
    {
        // Linked hash map to preserve original vanilla order
        Map<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> copy = new LinkedHashMap<>(RECIPE_PROPERTY_SETS);
        copy.put(ModRecipePropertySets.FREEZER_INPUT, recipe -> recipe instanceof FreezerSolidifyingRecipe f ? Optional.of(f.getIngredient()) : Optional.empty());
        copy.put(ModRecipePropertySets.MICROWAVE_INPUT, recipe -> recipe instanceof MicrowaveHeatingRecipe m ? Optional.of(m.getIngredient()) : Optional.empty());
        copy.put(ModRecipePropertySets.OVEN_INPUT, recipe -> recipe instanceof OvenBakingRecipe o ? Optional.of(o.getIngredient()) : Optional.empty());
        copy.put(ModRecipePropertySets.CUTTING_BOARD_INPUT, recipe -> recipe instanceof CuttingBoardSlicingRecipe o ? Optional.of(o.input()) : Optional.empty());
        RECIPE_PROPERTY_SETS = Map.copyOf(copy); // Restore original map type
    }
}
