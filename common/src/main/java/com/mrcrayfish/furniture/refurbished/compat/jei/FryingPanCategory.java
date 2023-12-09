package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FryingPanCookingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
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
public class FryingPanCategory implements IRecipeCategory<FryingPanCookingRecipe>
{
    public static final RecipeType<FryingPanCookingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "frying_pan_cooking", FryingPanCookingRecipe.class);

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable arrow;

    public FryingPanCategory(IGuiHelper helper)
    {
        this.helper = helper;
        this.background = helper.createDrawable(Plugin.TEXTURES, 0, 72, 124, 82);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.FRYING_PAN.get()));
    }

    @Override
    public RecipeType<FryingPanCookingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "frying_pan_cooking");
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
    public void setRecipe(IRecipeLayoutBuilder builder, FryingPanCookingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 6).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 102, 36).addItemStack(Plugin.getResult(recipe));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 74, 8).addItemStack(new ItemStack(ModItems.SPATULA.get()));
        this.arrow = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES, 93, 0, 24, 17), recipe.getCookingTime(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(FryingPanCookingRecipe recipe, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY)
    {
        this.arrow.draw(graphics, 71, 36);

        float seconds = recipe.getCookingTime() / 20.0F;
        String formattedTime = Plugin.FORMATTER.format(seconds);
        graphics.drawString(Plugin.getFont(), formattedTime, 101, 59, 0xFF808080, false);
    }
}
