package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.SinkFluidTransmutingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class SinkFluidTransmutingCategory implements IRecipeCategory<SinkFluidTransmutingRecipe>
{
    public static final RecipeType<SinkFluidTransmutingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "sink_fluid_transmuting", SinkFluidTransmutingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SinkFluidTransmutingCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(Plugin.TEXTURES_2, 0, 176, 126, 80);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.KITCHEN_SINK_OAK.get()));
    }

    @Override
    public RecipeType<SinkFluidTransmutingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "sink_fluid_transmuting");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SinkFluidTransmutingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 6).addItemStack(new ItemStack(recipe.getFluid().getBucket()));
        builder.addSlot(RecipeIngredientRole.INPUT, 67, 40).addIngredients(recipe.getCatalyst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 40).addItemStack(Plugin.getResult(recipe));
    }

    @Override
    public List<Component> getTooltipStrings(SinkFluidTransmutingRecipe recipe, IRecipeSlotsView view, double mouseX, double mouseY)
    {
        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 6, 26, 45, 45))
        {
            return Plugin.getItemTooltip(ModBlocks.KITCHEN_SINK_OAK.get());
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, view, mouseX, mouseY);
    }
}
