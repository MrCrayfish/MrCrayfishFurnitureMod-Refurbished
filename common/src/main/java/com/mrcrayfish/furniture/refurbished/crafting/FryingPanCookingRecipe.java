package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;

/**
 * Author: MrCrayfish
 */
public class FryingPanCookingRecipe extends ProcessingRecipe.Item
{
    public FryingPanCookingRecipe(Category category, Ingredient ingredient, ItemStack result, int time)
    {
        super(ModRecipeTypes.FRYING_PAN_COOKING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<FryingPanCookingRecipe> getSerializer()
    {
        return ModRecipeSerializers.FRYING_PAN_RECIPE.get();
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
