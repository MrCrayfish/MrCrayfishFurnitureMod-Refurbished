package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.crafting.RecipeBookCategory;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModRecipeBookCategories
{
    public static final RegistryEntry<RecipeBookCategory> FREEZER_BLOCKS = RegistryEntry.recipeBookCategory(Utils.resource("freezer_blocks"));
    public static final RegistryEntry<RecipeBookCategory> FREEZER_ITEMS = RegistryEntry.recipeBookCategory(Utils.resource("freezer_items"));
    public static final RegistryEntry<RecipeBookCategory> FREEZER_FOOD = RegistryEntry.recipeBookCategory(Utils.resource("freezer_food"));
    public static final RegistryEntry<RecipeBookCategory> FREEZER_MISC = RegistryEntry.recipeBookCategory(Utils.resource("freezer_misc"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_BLOCKS = RegistryEntry.recipeBookCategory(Utils.resource("microwave_blocks"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_ITEMS = RegistryEntry.recipeBookCategory(Utils.resource("microwave_items"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_FOOD = RegistryEntry.recipeBookCategory(Utils.resource("microwave_food"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_MISC = RegistryEntry.recipeBookCategory(Utils.resource("microwave_misc"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_BLOCKS = RegistryEntry.recipeBookCategory(Utils.resource("oven_blocks"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_ITEMS = RegistryEntry.recipeBookCategory(Utils.resource("oven_items"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_FOOD = RegistryEntry.recipeBookCategory(Utils.resource("oven_food"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_MISC = RegistryEntry.recipeBookCategory(Utils.resource("oven_misc"));
    public static final RegistryEntry<RecipeBookCategory> TOASTER = RegistryEntry.recipeBookCategory(Utils.resource("toaster"));
    public static final RegistryEntry<RecipeBookCategory> GRILL = RegistryEntry.recipeBookCategory(Utils.resource("grill"));
    public static final RegistryEntry<RecipeBookCategory> CUTTING_BOARD = RegistryEntry.recipeBookCategory(Utils.resource("cutting_board"));
}
