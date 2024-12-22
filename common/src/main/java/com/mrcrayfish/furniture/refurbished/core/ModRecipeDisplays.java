package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.crafting.display.FreezerRecipeDisplay;
import com.mrcrayfish.furniture.refurbished.crafting.display.MicrowaveRecipeDisplay;
import com.mrcrayfish.furniture.refurbished.crafting.display.OvenRecipeDisplay;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModRecipeDisplays
{
    public static final RegistryEntry<RecipeDisplay.Type<FreezerRecipeDisplay>> FREEZER = RegistryEntry.recipeDisplay(Utils.resource("freezer"), () -> FreezerRecipeDisplay.TYPE);
    public static final RegistryEntry<RecipeDisplay.Type<MicrowaveRecipeDisplay>> MICROWAVE = RegistryEntry.recipeDisplay(Utils.resource("microwave"), () -> MicrowaveRecipeDisplay.TYPE);
    public static final RegistryEntry<RecipeDisplay.Type<OvenRecipeDisplay>> OVEN = RegistryEntry.recipeDisplay(Utils.resource("oven"), () -> OvenRecipeDisplay.TYPE);
}
