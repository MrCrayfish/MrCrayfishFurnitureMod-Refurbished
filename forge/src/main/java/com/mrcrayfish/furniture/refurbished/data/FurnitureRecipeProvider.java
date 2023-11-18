package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;

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
        new CommonRecipeProvider(consumer, RecipeProvider::has).run();
        SpecialRecipeBuilder.special(ModRecipeSerializers.DOOR_MAT_COPY_RECIPE.get()).save(consumer, "door_mat_copy");
    }
}
