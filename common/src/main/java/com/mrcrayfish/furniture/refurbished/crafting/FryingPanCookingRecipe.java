package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
public class FryingPanCookingRecipe extends ProcessingRecipe.Item
{
    public FryingPanCookingRecipe(ResourceLocation id, Category category, Ingredient ingredient, ItemStack result, int cookingTime)
    {
        super(ModRecipeTypes.FRYING_PAN_COOKING.get(), id, category, ingredient, result, cookingTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.FRYING_PAN_RECIPE.get();
    }
}
