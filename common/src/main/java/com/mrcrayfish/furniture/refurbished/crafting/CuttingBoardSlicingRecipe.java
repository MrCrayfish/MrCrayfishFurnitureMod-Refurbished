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
public class CuttingBoardSlicingRecipe extends SingleItemRecipe
{
    public CuttingBoardSlicingRecipe(ResourceLocation id, String group, Ingredient input, ItemStack result)
    {
        super(ModRecipeTypes.CUTTING_BOARD_SLICING.get(), ModRecipeSerializers.CUTTING_BOARD_SLICING_RECIPE.get(), id, group, input, result);
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return this.ingredient.test(container.getItem(0));
    }

    public Ingredient getInput()
    {
        return this.ingredient;
    }

    public ItemStack getOutput()
    {
        return this.result;
    }
}
