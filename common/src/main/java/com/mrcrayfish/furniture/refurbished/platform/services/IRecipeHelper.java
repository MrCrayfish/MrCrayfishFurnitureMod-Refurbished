package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.crafting.ICookingBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

/**
 * Author: MrCrayfish
 */
public interface IRecipeHelper
{
    <T extends AbstractCookingRecipe> SimpleCookingSerializer<T> createSimpleCookingSerializer(ICookingBuilder<T> builder, int defaultCookingTime);
}
