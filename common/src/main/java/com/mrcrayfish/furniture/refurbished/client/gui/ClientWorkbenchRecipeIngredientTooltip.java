package com.mrcrayfish.furniture.refurbished.client.gui;

import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbenchRecipeIngredientTooltip implements ClientTooltipComponent
{
    private final StackedIngredient ingredient;

    public ClientWorkbenchRecipeIngredientTooltip(StackedIngredient ingredient)
    {
        this.ingredient = ingredient;
    }

    @Override
    public int getHeight()
    {
        return 18;
    }

    @Override
    public int getWidth(Font font)
    {
        return 18 + font.width(this.getMaterial().getDisplayName());
    }

    @Override
    public void renderImage(Font font, int start, int top, GuiGraphics graphics)
    {
        ItemStack material = this.ingredient.ingredient().getItems()[0].copy();
        material.setCount(this.ingredient.count());
        graphics.renderFakeItem(material, start, top);
        graphics.renderItemDecorations(font, material, start, top);
        MutableComponent name = material.getHoverName().copy().withStyle(ChatFormatting.GRAY);
        graphics.drawString(font, name, start + 18 + 5, top + 4, 0xFFFFFFFF);
    }

    private ItemStack getMaterial()
    {
        return this.ingredient.ingredient().getItems()[0]; // TODO cycle
    }
}
