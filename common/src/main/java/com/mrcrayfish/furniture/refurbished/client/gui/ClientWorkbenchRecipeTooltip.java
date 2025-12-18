package com.mrcrayfish.furniture.refurbished.client.gui;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.WorkbenchScreen;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbenchRecipeTooltip implements ClientTooltipComponent
{
    private final WorkbenchMenu menu;
    private final WorkbenchContructingRecipe recipe;
    private final List<DisplayStack> displayStacks;

    public ClientWorkbenchRecipeTooltip(WorkbenchMenu menu, WorkbenchContructingRecipe recipe)
    {
        this.menu = menu;
        this.recipe = recipe;
        this.displayStacks = recipe.getMaterials().stream()
            .map(i -> new DisplayStack(-1, ItemStack.EMPTY)).collect(Collectors.toList());
    }

    @Override
    public int getHeight(Font font)
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
    public void renderImage(Font font, int start, int top, int width, int height, GuiGraphics graphics)
    {
        Map<Integer, Integer> counted = new HashMap<>();
        List<StackedIngredient> materials = this.recipe.getMaterials();
        for(int i = 0; i < materials.size(); i++)
        {
            StackedIngredient material = materials.get(i);
            ItemStack copy = this.getDisplayStack(i, material);
            graphics.renderFakeItem(copy, start + i * 18, top);
            graphics.renderItemDecorations(font, copy, start + i * 18, top);

            // Draw check or cross depending on if we have the materials
            boolean checked = this.menu.hasMaterials(material, counted);
            graphics.blit(RenderPipelines.GUI_TEXTURED, WorkbenchScreen.WORKBENCH_TEXTURE, start + i * 18, top, checked ? 246 : 240, 40, 6, 5, 256, 256);
        }
    }

    private ItemStack getDisplayStack(int index, StackedIngredient material)
    {
        long time = Util.getMillis() / 1000;
        DisplayStack display = this.displayStacks.get(index);
        if(display.lastUpdate != time) // Only run once every second. We expensively collect items
        {
            List<Holder<Item>> items = material.ingredient().items().toList();
            int itemIndex = (int) time % items.size();
            ItemStack stack = new ItemStack(items.get(itemIndex));
            stack.setCount(material.count());
            this.displayStacks.set(index, new DisplayStack(time, stack));
            return stack;
        }
        return display.stack;
    }

    private record DisplayStack(long lastUpdate, ItemStack stack) {}
}
