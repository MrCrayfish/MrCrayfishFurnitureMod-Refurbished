package com.mrcrayfish.furniture.refurbished.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Author: MrCrayfish
 */
public interface ICookingBuilder<T extends AbstractCookingRecipe>
{
    T create(String name, CookingBookCategory category, Ingredient ingredient, ItemStack stack, float experience, int cookingTime);
}
