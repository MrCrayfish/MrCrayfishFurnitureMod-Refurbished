package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.crafting.ISingleBuilder;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;

import java.util.Collection;

/**
 * Author: MrCrayfish
 */
public interface IRecipeHelper
{
    <T extends SingleItemRecipe> RecipeSerializer<T> createSingleItemSerializer(ISingleBuilder<T> builder);

    Collection<RecipeHolder<WorkbenchContructingRecipe>> getWorkbenchRecipes(ServerLevel level);
}
