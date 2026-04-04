package com.mrcrayfish.furniture.refurbished.crafting;

import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SingleItemRecipe;

/**
 * Author: MrCrayfish
 */
public interface ISingleBuilder<T extends SingleItemRecipe>
{
    T create(Recipe.CommonInfo info, Ingredient ingredient, ItemStackTemplate result);
}
