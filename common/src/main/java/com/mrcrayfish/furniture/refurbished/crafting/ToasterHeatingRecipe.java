package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
public class ToasterHeatingRecipe extends ProcessingRecipe.Item
{
    public ToasterHeatingRecipe(Category category, Ingredient ingredient, ItemStackTemplate result, int time)
    {
        super(ModRecipeTypes.TOASTER_HEATING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<ToasterHeatingRecipe> getSerializer()
    {
        return ModRecipeSerializers.TOASTER_RECIPE.get();
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return ModRecipeBookCategories.TOASTER.get();
    }
}
