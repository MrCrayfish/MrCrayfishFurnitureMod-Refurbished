package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.crafting.ICookingBuilder;
import com.mrcrayfish.furniture.refurbished.crafting.ISingleBuilder;
import com.mrcrayfish.furniture.refurbished.platform.services.IRecipeHelper;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;

/**
 * Author: MrCrayfish
 */
public class FabricRecipeHelper implements IRecipeHelper
{
    @Override
    public <T extends AbstractCookingRecipe> SimpleCookingSerializer<T> createSimpleCookingSerializer(ICookingBuilder<T> builder, int defaultCookingTime)
    {
        return new SimpleCookingSerializer<>(builder::create, defaultCookingTime);
    }

    @Override
    public <T extends SingleItemRecipe> SingleItemRecipe.Serializer<T> createSingleItemSerializer(ISingleBuilder<T> builder)
    {
        return new SingleItemRecipe.Serializer<>(builder::create){};
    }

    @Override
    public RecipeBookType getFreezerRecipeBookType()
    {
        // Fabric doesn't have the ability to add new types without third party libs
        // We are just returning this to avoid crashes, but essentially crafting book
        // is disabled on Fabric.
        return RecipeBookType.CRAFTING;
    }
}
