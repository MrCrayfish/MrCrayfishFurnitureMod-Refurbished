package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
public class GrillCookingRecipe extends ProcessingRecipe.Item
{
    public GrillCookingRecipe(Category category, Ingredient ingredient, ItemStackTemplate result, int time)
    {
        super(ModRecipeTypes.GRILL_COOKING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<GrillCookingRecipe> getSerializer()
    {
        return ModRecipeSerializers.GRILL_RECIPE.get();
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return ModRecipeBookCategories.GRILL.get();
    }
}
