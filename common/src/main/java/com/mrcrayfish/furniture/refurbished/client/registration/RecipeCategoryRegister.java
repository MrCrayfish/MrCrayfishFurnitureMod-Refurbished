package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface RecipeCategoryRegister
{
    void apply(RecipeType<?> type, Function<Recipe<?>, RecipeBookCategories> function);
}
