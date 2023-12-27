package com.mrcrayfish.furniture.refurbished.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.WorkbenchScreen;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbenchRecipeIngredientTooltip implements ClientTooltipComponent
{
    private final WorkbenchMenu menu;
    private final StackedIngredient material;
    private final Map<Integer, Integer> counted;

    public ClientWorkbenchRecipeIngredientTooltip(WorkbenchMenu menu, StackedIngredient material, Map<Integer, Integer> counted)
    {
        this.menu = menu;
        this.material = material;
        this.counted = counted;
    }

    @Override
    public int getHeight()
    {
        return 18;
    }

    @Override
    public int getWidth(Font font)
    {
        return 18 + font.width(this.getStack().getDisplayName());
    }

    @Override
    public void renderImage(Font font, int start, int top, GuiGraphics graphics)
    {
        ItemStack material = this.getStack().copy();
        material.setCount(this.material.count());
        graphics.renderFakeItem(material, start, top);
        graphics.renderItemDecorations(font, material, start, top);
        MutableComponent name = material.getHoverName().copy().withStyle(ChatFormatting.GRAY);
        graphics.drawString(font, name, start + 18 + 5, top + 4, 0xFFFFFFFF);

        // Draw check or cross depending on if we have the materials
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 200);
        boolean checked = this.menu.hasMaterials(this.material, this.counted);
        graphics.blit(WorkbenchScreen.WORKBENCH_TEXTURE, start, top, checked ? 246 : 240, 40, 6, 5);
        pose.popPose();
    }

    private ItemStack getStack()
    {
        ItemStack[] items = this.material.ingredient().getItems();
        int index = (int) ((Util.getMillis() / 1000) % items.length);
        return items[index];
    }
}
