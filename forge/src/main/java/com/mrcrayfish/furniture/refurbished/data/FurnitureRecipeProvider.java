package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

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
        new CommonRecipeProvider(consumer, (modId, recipeName, builder) -> {
            // Need to accept a custom consumer to get access to advancement id
            builder.save(recipe -> {
                ConditionalRecipe.builder()
                    .addCondition(new ModLoadedCondition(modId))
                    .addRecipe(recipe)
                    .generateAdvancement(recipe.getAdvancementId())
                    .build(consumer, recipeName);
            }, recipeName);
        }, RecipeProvider::has, RecipeProvider::has).run();
    }
}
