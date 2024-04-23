package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
public class ToasterHeatingRecipe extends ProcessingRecipe.Item
{
    public ToasterHeatingRecipe(ResourceLocation id, Category category, Ingredient ingredient, ItemStack result, int time)
    {
        super(ModRecipeTypes.TOASTER_HEATING.get(), id, category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.TOASTER_RECIPE.get();
    }
}
