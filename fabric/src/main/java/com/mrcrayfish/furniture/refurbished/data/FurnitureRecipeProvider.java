package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

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
    public void buildRecipes(RecipeOutput output)
    {
        new CommonRecipeProvider(output, (modId, recipeName, builder) -> {
            builder.save(this.withConditions(output, DefaultResourceConditions.allModsLoaded(modId)), recipeName);
        }, RecipeProvider::has, RecipeProvider::has).run();
    }
}
