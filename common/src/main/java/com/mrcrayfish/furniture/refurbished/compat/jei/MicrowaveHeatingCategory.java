package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.MicrowaveHeatingRecipe;
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
public class MicrowaveHeatingCategory extends FurnitureRecipeCategory<MicrowaveHeatingRecipe>
{
    public static final RecipeType<MicrowaveHeatingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "microwave_heating", MicrowaveHeatingRecipe.class);

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable waveform;

    public MicrowaveHeatingCategory(IGuiHelper helper)
    {
        this.helper = helper;
        this.background = helper.createDrawable(Plugin.TEXTURES, 0, 154, 93, 36);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.MICROWAVE_LIGHT.get()));
    }

    @Override
    public RecipeType<MicrowaveHeatingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "microwave_heating");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MicrowaveHeatingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 10).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 10).addItemStack(Plugin.getResult(recipe));
        this.waveform = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES, 93, 154, 24, 17), recipe.getCookingTime(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(MicrowaveHeatingRecipe recipe, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY)
    {
        this.waveform.draw(graphics, 30, 9);
        this.drawSeconds(graphics, 42, 28, recipe.getCookingTime());
    }
}
