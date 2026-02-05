package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.ToasterHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ToasterToastingCategory extends FurnitureRecipeCategory<ToasterHeatingRecipe>
{
    public static final Supplier<IRecipeHolderType<ToasterHeatingRecipe>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.TOASTER_HEATING::get);

    private final IGuiHelper helper;
    private IDrawable arrow;

    public ToasterToastingCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "toaster_heating"),
            helper.createDrawable(Plugin.TEXTURES, 151, 0, 105, 57),
            helper.createDrawableItemStack(new ItemStack(ModBlocks.TOASTER_LIGHT.get()))
        );
        this.helper = helper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ToasterHeatingRecipe> holder, IFocusGroup focuses)
    {
        ToasterHeatingRecipe recipe = holder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 18, 4).add(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 83, 19).add(recipe.getResult());
        this.arrow = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES, 93, 0, 24, 17), recipe.getTime(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(RecipeHolder<ToasterHeatingRecipe> holder, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY)
    {
        super.draw(holder, view, graphics, mouseX, mouseY);
        this.arrow.draw(graphics, 52, 19);
        this.drawSeconds(graphics, 64, 38, holder.value().getTime());
    }
}
