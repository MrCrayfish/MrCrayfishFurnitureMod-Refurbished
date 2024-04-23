package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;
import java.util.function.Function;

public interface RecipeCategoryRegister
{
    void applyCategory(RecipeBookType freezerRecipeBookType, List<RecipeBookCategories> categories);

    void applyAggregate(RecipeBookCategories category, List<RecipeBookCategories> categories);

    void applyFinder(RecipeType<?> type, Function<Recipe<?>, RecipeBookCategories> function);
}
