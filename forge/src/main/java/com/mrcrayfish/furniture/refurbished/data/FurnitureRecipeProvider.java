package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class FurnitureRecipeProvider extends RecipeProvider
{
    public FurnitureRecipeProvider(PackOutput output)
    {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer)
    {
        CommonRecipeProvider.accept(consumer);
    }
}
