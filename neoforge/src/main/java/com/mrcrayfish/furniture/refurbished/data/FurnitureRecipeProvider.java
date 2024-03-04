package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class FurnitureRecipeProvider extends RecipeProvider
{
    public FurnitureRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output)
    {
        new CommonRecipeProvider(output, (modId, recipeName, builder) -> {
            builder.save(output.withConditions(new ModLoadedCondition(modId)), recipeName);
        }, RecipeProvider::has, RecipeProvider::has).run();
    }
}
