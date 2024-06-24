package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import net.minecraft.client.RecipeBookCategories;

/**
 * Author: MrCrayfish
 */
public class ClientFurnitureMod
{
    public static void init()
    {
        registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_SEARCH);
        registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_BLOCKS);
        registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_ITEMS);
        registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_FOOD);
        registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_MISC);
        registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_SEARCH);
        registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_BLOCKS);
        registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_ITEMS);
        registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_FOOD);
        registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_MISC);
    }

    private static void registerRecipeBookCategory(RecipeBookCategoryHolder holder)
    {
        RecipeBookCategories.create(holder.getConstantName(), holder.getIcons().get());
    }
}
