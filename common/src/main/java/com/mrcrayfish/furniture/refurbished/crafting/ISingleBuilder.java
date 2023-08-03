package com.mrcrayfish.furniture.refurbished.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;

/**
 * Author: MrCrayfish
 */
public interface ISingleBuilder<T extends SingleItemRecipe>
{
    T create(ResourceLocation id, String group, Ingredient input, ItemStack result);
}
