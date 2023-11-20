package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface ConditionalModConsumer
{
    void apply(String modId, ResourceLocation recipeName, RecipeBuilder builder);
}
