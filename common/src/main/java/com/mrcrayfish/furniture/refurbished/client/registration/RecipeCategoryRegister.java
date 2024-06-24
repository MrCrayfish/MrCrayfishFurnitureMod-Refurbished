package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public interface RecipeCategoryRegister
{
    void applyCategory(RecipeBookType type, RecipeBookCategories ... categories);

    void applyAggregate(RecipeBookCategories category, RecipeBookCategories ... categories);

    void applyFinder(RecipeType<?> type, Function<Recipe<?>, RecipeBookCategories> function);
}
