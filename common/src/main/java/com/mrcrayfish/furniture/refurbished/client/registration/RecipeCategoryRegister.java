package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public interface RecipeCategoryRegister
{
    void applyCategory(RecipeBookType type, RecipeBookCategory... categories);

    void applyAggregate(RecipeBookCategory category, RecipeBookCategory ... categories);

    void applyFinder(RecipeType<?> type, Function<Recipe<?>, RecipeBookCategory> function);
}
