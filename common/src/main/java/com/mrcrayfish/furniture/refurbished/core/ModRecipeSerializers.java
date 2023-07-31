package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModRecipeSerializers
{
    public static final RegistryEntry<SimpleCookingSerializer<GrillCookingRecipe>> GRILL_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("grill_cooking"), () -> Services.RECIPE.createSimpleCookingSerializer(GrillCookingRecipe::new, 200));
    public static final RegistryEntry<SimpleCookingSerializer<FreezerSolidifyingRecipe>> FREEZER_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("freezer_solidifying"), () -> Services.RECIPE.createSimpleCookingSerializer(FreezerSolidifyingRecipe::new, 200));
}
