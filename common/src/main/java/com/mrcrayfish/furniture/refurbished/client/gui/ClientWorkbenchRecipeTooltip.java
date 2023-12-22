package com.mrcrayfish.furniture.refurbished.client.gui;

import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbenchRecipeTooltip implements ClientTooltipComponent
{
    private final WorkbenchCraftingRecipe recipe;

    public ClientWorkbenchRecipeTooltip(WorkbenchCraftingRecipe recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public int getHeight()
    {
        return 20;
    }

    @Override
    public int getWidth(Font font)
    {
        // TODO wrap
        return this.recipe.getMaterials().size() * 18;
    }

    @Override
    public void renderImage(Font font, int start, int top, GuiGraphics graphics)
    {
        List<StackedIngredient> materials = this.recipe.getMaterials();
        for(int i = 0; i < materials.size(); i++)
        {
            StackedIngredient material = materials.get(i);
            ItemStack copy = material.ingredient().getItems()[0].copy();
            copy.setCount(material.count());
            graphics.renderFakeItem(copy, start + i * 16, top);
            graphics.renderItemDecorations(font, copy, start + i * 16, top);
        }

        // TODO render tick/cross if has materials
        //this.pose.translate(0.0F, 0.0F, 200.0F);
    }
}
