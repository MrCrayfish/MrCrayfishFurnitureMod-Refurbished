package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardCombiningCategory extends FurnitureRecipeCategory<CuttingBoardCombiningRecipe>
{
    public static final Supplier<IRecipeHolderType<CuttingBoardCombiningRecipe>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.CUTTING_BOARD_COMBINING::get);

    public CuttingBoardCombiningCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "cutting_board_combining"),
            helper.createDrawable(Plugin.TEXTURES, 157, 156, 99, 100),
            helper.createDrawableItemStack(new ItemStack(ModBlocks.CUTTING_BOARD_OAK.get()))
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CuttingBoardCombiningRecipe> holder, IFocusGroup focuses)
    {
        CuttingBoardCombiningRecipe recipe = holder.value();
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for(int i = 0; i < ingredients.size(); i++)
        {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 69 - i * 16).add(ingredients.get(i));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 69).add(recipe.getResult().create()); // TODO 26.1.1 test
    }
}
