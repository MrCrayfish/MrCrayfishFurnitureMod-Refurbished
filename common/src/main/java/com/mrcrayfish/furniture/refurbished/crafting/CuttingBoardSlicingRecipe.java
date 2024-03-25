package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardSlicingRecipe extends SingleItemRecipe
{
    public CuttingBoardSlicingRecipe(Ingredient ingredient, ItemStack result)
    {
        super(ModRecipeTypes.CUTTING_BOARD_SLICING.get(), ModRecipeSerializers.CUTTING_BOARD_SLICING_RECIPE.get(), "", ingredient, result);
    }

    public CuttingBoardSlicingRecipe(String group, Ingredient ingredient, ItemStack result)
    {
        this(ingredient, result);
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return this.ingredient.test(container.getItem(0));
    }

    public Ingredient getIngredient()
    {
        return this.ingredient;
    }

    public ItemStack getResult()
    {
        return this.result;
    }
}
