package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class FreezerSolidifyingCategory implements IRecipeCategory<FreezerSolidifyingRecipe>
{
    public static final RecipeType<FreezerSolidifyingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "freezer_solidifying", FreezerSolidifyingRecipe.class);

    private final IDrawable background;
    private final IDrawable arrow;
    private final IDrawable icon;

    public FreezerSolidifyingCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(Plugin.TEXTURES, 0, 0, 93, 36);
        this.arrow = helper.createAnimatedDrawable(helper.createDrawable(Plugin.TEXTURES, 93, 0, 24, 17), 40, IDrawableAnimated.StartDirection.LEFT, false);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModItems.FRIDGE_LIGHT.get()));
    }

    @Override
    public RecipeType<FreezerSolidifyingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "freezer_solidifying");
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
    public void setRecipe(IRecipeLayoutBuilder builder, FreezerSolidifyingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 10).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 10).addItemStack(Plugin.getResult(recipe));
    }

    @Override
    public void draw(FreezerSolidifyingRecipe recipe, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY)
    {
        this.arrow.draw(graphics, 30, 9);
    }
}
