package com.mrcrayfish.furniture.refurbished.client.gui;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.WorkbenchScreen;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbenchRecipeIngredientTooltip implements ClientTooltipComponent
{
    private final WorkbenchMenu menu;
    private final StackedIngredient material;
    private final Map<Integer, Integer> counted;
    private ItemStack display = ItemStack.EMPTY;
    private long lastUpdateTime = -1;

    public ClientWorkbenchRecipeIngredientTooltip(WorkbenchMenu menu, StackedIngredient material, Map<Integer, Integer> counted)
    {
        this.menu = menu;
        this.material = material;
        this.counted = counted;
    }

    @Override
    public int getHeight(Font font)
    {
        return 18;
    }

    @Override
    public int getWidth(Font font)
    {
        return 18 + font.width(this.getStack().getDisplayName());
    }

    @Override
    public void renderImage(Font font, int start, int top, int width, int height, GuiGraphics graphics)
    {
        ItemStack material = this.getStack().copy();
        material.setCount(this.material.count());
        graphics.renderFakeItem(material, start, top);
        graphics.renderItemDecorations(font, material, start, top);
        MutableComponent name = material.getHoverName().copy().withStyle(ChatFormatting.GRAY);
        graphics.drawString(font, name, start + 18 + 5, top + 4, 0xFFFFFFFF);

        // Draw check or cross depending on if we have the materials
        boolean checked = this.menu.hasMaterials(this.material, this.counted);
        graphics.blit(RenderPipelines.GUI_TEXTURED, WorkbenchScreen.WORKBENCH_TEXTURE, start, top, checked ? 246 : 240, 40, 6, 5, 256, 256);
    }

    private ItemStack getStack()
    {
        long time = Util.getMillis() / 1000;
        if(this.lastUpdateTime != time) // Only run once every second. We expensively collect items
        {
            List<Holder<Item>> items = this.material.ingredient().items().toList();
            int index = (int) time % items.size();
            this.display = new ItemStack(items.get(index));
            this.lastUpdateTime = time;
        }
        return this.display;
    }
}
