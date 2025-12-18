package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.Identifier;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface ConditionalModConsumer
{
    void apply(String modId, Identifier recipeName, RecipeBuilder builder);
}
