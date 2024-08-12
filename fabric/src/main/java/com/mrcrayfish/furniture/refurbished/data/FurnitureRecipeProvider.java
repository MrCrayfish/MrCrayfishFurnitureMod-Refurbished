package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
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
    public FurnitureRecipeProvider(FabricDataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer)
    {
        new CommonRecipeProvider(consumer, (modId, recipeName, builder) -> {
            builder.save(this.withConditions(consumer, DefaultResourceConditions.allModsLoaded(modId)), recipeName);
        }, RecipeProvider::has, RecipeProvider::has).run();
    }
}
