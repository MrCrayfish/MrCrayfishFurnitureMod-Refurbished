package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardSlicingRecipe extends SingleItemRecipe
{
    public CuttingBoardSlicingRecipe(Recipe.CommonInfo info, Ingredient ingredient, ItemStackTemplate result)
    {
        super(info, ingredient, result);
    }

    @Override
    public String group()
    {
        return "";
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
}
