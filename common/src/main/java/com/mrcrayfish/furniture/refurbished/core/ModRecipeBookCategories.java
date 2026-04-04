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
    public static final RegistryEntry<RecipeBookCategory> FREEZER_BLOCKS = RegistryEntry.recipeBookCategory(Utils.id("freezer_blocks"));
    public static final RegistryEntry<RecipeBookCategory> FREEZER_ITEMS = RegistryEntry.recipeBookCategory(Utils.id("freezer_items"));
    public static final RegistryEntry<RecipeBookCategory> FREEZER_FOOD = RegistryEntry.recipeBookCategory(Utils.id("freezer_food"));
    public static final RegistryEntry<RecipeBookCategory> FREEZER_MISC = RegistryEntry.recipeBookCategory(Utils.id("freezer_misc"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_BLOCKS = RegistryEntry.recipeBookCategory(Utils.id("microwave_blocks"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_ITEMS = RegistryEntry.recipeBookCategory(Utils.id("microwave_items"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_FOOD = RegistryEntry.recipeBookCategory(Utils.id("microwave_food"));
    public static final RegistryEntry<RecipeBookCategory> MICROWAVE_MISC = RegistryEntry.recipeBookCategory(Utils.id("microwave_misc"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_BLOCKS = RegistryEntry.recipeBookCategory(Utils.id("oven_blocks"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_ITEMS = RegistryEntry.recipeBookCategory(Utils.id("oven_items"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_FOOD = RegistryEntry.recipeBookCategory(Utils.id("oven_food"));
    public static final RegistryEntry<RecipeBookCategory> OVEN_MISC = RegistryEntry.recipeBookCategory(Utils.id("oven_misc"));
    public static final RegistryEntry<RecipeBookCategory> TOASTER = RegistryEntry.recipeBookCategory(Utils.id("toaster"));
    public static final RegistryEntry<RecipeBookCategory> GRILL = RegistryEntry.recipeBookCategory(Utils.id("grill"));
    public static final RegistryEntry<RecipeBookCategory> CUTTING_BOARD = RegistryEntry.recipeBookCategory(Utils.id("cutting_board"));
}
