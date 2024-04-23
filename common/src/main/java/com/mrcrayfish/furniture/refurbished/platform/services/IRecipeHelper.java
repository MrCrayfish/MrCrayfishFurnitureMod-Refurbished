package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.crafting.ICookingBuilder;
import com.mrcrayfish.furniture.refurbished.crafting.ISingleBuilder;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;

/**
 * Author: MrCrayfish
 */
public interface IRecipeHelper
{
    <T extends AbstractCookingRecipe> SimpleCookingSerializer<T> createSimpleCookingSerializer(ICookingBuilder<T> builder, int defaultCookingTime);

    <T extends SingleItemRecipe> SingleItemRecipe.Serializer<T> createSingleItemSerializer(ISingleBuilder<T> builder);

    RecipeBookType getFreezerRecipeBookType();
}
