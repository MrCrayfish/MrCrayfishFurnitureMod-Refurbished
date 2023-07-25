package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.TradeTrigger;
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
        new CommonRecipeProvider(consumer, RecipeProvider::has).run();
    }
}
