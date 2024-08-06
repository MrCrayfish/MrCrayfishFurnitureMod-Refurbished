package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardSlicingCategory implements IRecipeCategory<CuttingBoardSlicingRecipe>
{
    public static final RecipeType<CuttingBoardSlicingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "cutting_board_slicing", CuttingBoardSlicingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final List<ItemStack> knives;

    public CuttingBoardSlicingCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(Plugin.TEXTURES, 0, 36, 133, 36);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModItems.KNIFE.get()));
        this.knives = Plugin.getTagItems(Services.TAG.getToolKnivesTag());
    }

    @Override
    public RecipeType<CuttingBoardSlicingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "cutting_board_slicing");
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
    public void setRecipe(IRecipeLayoutBuilder builder, CuttingBoardSlicingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 6).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 111, 10).addItemStack(Plugin.getResult(recipe));
        builder.addSlot(RecipeIngredientRole.CATALYST, 73, 11).addItemStacks(this.knives);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, CuttingBoardSlicingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY)
    {
        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 5, 16, 55, 15) && !ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 25, 6, 16, 16))
        {
            tooltip.addAll(Plugin.getItemTooltip(ModBlocks.CUTTING_BOARD_OAK.get()));
        }
    }
}
