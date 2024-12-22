package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.crafting.ICookingBuilder;
import com.mrcrayfish.furniture.refurbished.crafting.ISingleBuilder;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleItemRecipe;

import java.util.Collection;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public interface IRecipeHelper
{
    <T extends SingleItemRecipe> SingleItemRecipe.Serializer<T> createSingleItemSerializer(ISingleBuilder<T> builder);

    Collection<RecipeHolder<WorkbenchContructingRecipe>> getWorkbenchRecipes(ServerLevel level);
}
