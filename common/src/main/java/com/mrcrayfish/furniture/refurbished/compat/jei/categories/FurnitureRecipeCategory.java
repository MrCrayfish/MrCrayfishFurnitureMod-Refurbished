package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import mezz.jei.api.recipe.category.IRecipeCategory;

/**
 * Author: MrCrayfish
 */
public abstract class FurnitureRecipeCategory<T> implements IRecipeCategory<T>
{
    protected void drawSeconds(PoseStack poseStack, int x, int y, int ticks)
    {
        float seconds = ticks / 20.0F;
        String formattedTime = Plugin.FORMATTER.format(seconds);
        int width = Plugin.getFont().width(formattedTime) / 2;
        ScreenHelper.drawString(poseStack, formattedTime, x - width, y, 0xFF808080, false);
    }
}
