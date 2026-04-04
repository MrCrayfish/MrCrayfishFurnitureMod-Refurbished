package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
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
public class FreezerSolidifyingCategory extends FurnitureRecipeCategory<FreezerSolidifyingRecipe>
{
    public static final Supplier<IRecipeHolderType<FreezerSolidifyingRecipe>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.FREEZER_SOLIDIFYING::get);

    private final IGuiHelper helper;
    private IDrawable arrow;

    public FreezerSolidifyingCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "freezer_solidifying"),
            helper.createDrawable(Plugin.TEXTURES, 0, 0, 93, 36),
            helper.createDrawableItemStack(new ItemStack(ModItems.FRIDGE_LIGHT.get()))
        );
        this.helper = helper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<FreezerSolidifyingRecipe> holder, IFocusGroup focuses)
    {
        FreezerSolidifyingRecipe recipe = holder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 10).add(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 10).add(recipe.getResult().create());
        this.arrow = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES, 93, 0, 24, 17), recipe.getTime(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(RecipeHolder<FreezerSolidifyingRecipe> holder, IRecipeSlotsView view, GuiGraphicsExtractor extractor, double mouseX, double mouseY)
    {
        super.draw(holder, view, extractor, mouseX, mouseY);
        this.arrow.draw(extractor, 30, 9);
        this.drawSeconds(extractor, 42, 28, holder.value().getTime());
    }
}
