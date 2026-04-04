package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.MicrowaveHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MicrowaveHeatingCategory extends FurnitureRecipeCategory<MicrowaveHeatingRecipe>
{
    public static final Supplier<IRecipeHolderType<MicrowaveHeatingRecipe>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.MICROWAVE_HEATING::get);

    private final IGuiHelper helper;
    private IDrawable waveform;

    public MicrowaveHeatingCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "microwave_heating"),
            helper.createDrawable(Plugin.TEXTURES, 0, 154, 93, 36),
            helper.createDrawableItemStack(new ItemStack(ModBlocks.MICROWAVE_LIGHT.get()))
        );
        this.helper = helper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MicrowaveHeatingRecipe> holder, IFocusGroup focuses)
    {
        MicrowaveHeatingRecipe recipe = holder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 10).add(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 10).add(recipe.getResult().create()); // TODO 26.1.1 test
        this.waveform = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES, 93, 154, 24, 17), recipe.getTime(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(RecipeHolder<MicrowaveHeatingRecipe> recipe, IRecipeSlotsView view, GuiGraphicsExtractor extractor, double mouseX, double mouseY)
    {
        super.draw(recipe, view, extractor, mouseX, mouseY);
        this.waveform.draw(extractor, 30, 9);
        this.drawSeconds(extractor, 42, 28, recipe.value().getTime());
    }
}
