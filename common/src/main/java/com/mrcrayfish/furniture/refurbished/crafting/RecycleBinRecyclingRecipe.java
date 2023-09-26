package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class RecycleBinRecyclingRecipe extends SingleItemRecipe
{
    public RecycleBinRecyclingRecipe(ResourceLocation $$2, String $$3, Ingredient $$4, ItemStack $$5)
    {
        super(ModRecipeTypes.RECYCLE_BIN_RECYCLING.get(), ModRecipeSerializers.RECYCLE_BIN_RECIPE.get(), $$2, $$3, $$4, $$5);
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return this.ingredient.test(container.getItem(0));
    }
}
