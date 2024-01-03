package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardCombiningCategory implements IRecipeCategory<CuttingBoardCombiningRecipe>
{
    public static final RecipeType<CuttingBoardCombiningRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "cutting_board_combining", CuttingBoardCombiningRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CuttingBoardCombiningCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(Plugin.TEXTURES, 157, 156, 99, 100);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.CUTTING_BOARD_OAK.get()));
    }

    @Override
    public RecipeType<CuttingBoardCombiningRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "cutting_board_combining");
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CuttingBoardCombiningRecipe recipe, IFocusGroup focuses)
    {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for(int i = 0; i < ingredients.size(); i++)
        {
            builder.addSlot(RecipeIngredientRole.INPUT, 25, 69 - i * 16).addIngredients(ingredients.get(i));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 69).addItemStack(Plugin.getResult(recipe));
    }
}
