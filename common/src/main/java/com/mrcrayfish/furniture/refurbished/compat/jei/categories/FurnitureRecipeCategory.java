package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Author: MrCrayfish
 */
public abstract class FurnitureRecipeCategory<T> implements IRecipeCategory<T>
{
    protected void drawSeconds(GuiGraphics graphics, int x, int y, int ticks)
    {
        float seconds = ticks / 20.0F;
        String formattedTime = Plugin.FORMATTER.format(seconds);
        int width = Plugin.getFont().width(formattedTime) / 2;
        graphics.drawString(Plugin.getFont(), formattedTime, x - width, y, 0xFF808080, false);
    }
}
