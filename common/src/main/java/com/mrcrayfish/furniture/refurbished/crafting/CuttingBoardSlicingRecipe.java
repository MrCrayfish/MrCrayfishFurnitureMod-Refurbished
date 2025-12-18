package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardSlicingRecipe extends SingleItemRecipe
{
    public CuttingBoardSlicingRecipe(Ingredient ingredient, ItemStack result)
    {
        super("", ingredient, result);
    }

    public CuttingBoardSlicingRecipe(String group, Ingredient ingredient, ItemStack result)
    {
        this(ingredient, result);
    }

    @Override
    public RecipeSerializer<CuttingBoardSlicingRecipe> getSerializer()
    {
        return ModRecipeSerializers.CUTTING_BOARD_SLICING_RECIPE.get();
    }

    @Override
    public RecipeType<CuttingBoardSlicingRecipe> getType()
    {
        return ModRecipeTypes.CUTTING_BOARD_SLICING.get();
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return ModRecipeBookCategories.CUTTING_BOARD.get();
    }

    @Override
    public ItemStack result()
    {
        return super.result();
    }
}
