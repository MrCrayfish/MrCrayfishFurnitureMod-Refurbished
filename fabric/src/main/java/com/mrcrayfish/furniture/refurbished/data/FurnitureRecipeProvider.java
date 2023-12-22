package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class FurnitureRecipeProvider extends FabricRecipeProvider
{
    public FurnitureRecipeProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer)
    {
        new CommonRecipeProvider(consumer, (modId, recipeName, builder) -> {
            builder.save(this.withConditions(consumer, DefaultResourceConditions.allModsLoaded(modId)), recipeName);
        }, RecipeProvider::has, RecipeProvider::has).run();
    }
}
