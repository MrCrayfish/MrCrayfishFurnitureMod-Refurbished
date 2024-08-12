package com.mrcrayfish.furniture.refurbished.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.WorkbenchScreen;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbenchRecipeTooltip implements ClientTooltipComponent
{
    private final WorkbenchMenu menu;
    private final WorkbenchContructingRecipe recipe;

    public ClientWorkbenchRecipeTooltip(WorkbenchMenu menu, WorkbenchContructingRecipe recipe)
    {
        this.menu = menu;
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
    public void renderImage(Font font, int start, int top, PoseStack poseStack, ItemRenderer renderer, int $$5)
    {
        Map<Integer, Integer> counted = new HashMap<>();
        List<StackedIngredient> materials = this.recipe.getMaterials();
        for(int i = 0; i < materials.size(); i++)
        {
            StackedIngredient material = materials.get(i);
            ItemStack copy = this.getStack(material).copy();
            copy.setCount(material.count());

            renderer.renderAndDecorateFakeItem(copy, start + i * 18, top);
            renderer.renderGuiItemDecorations(font, copy, start + i * 18, top);

            // Draw check or cross depending on if we have the materials
            poseStack.pushPose();
            poseStack.translate(0, 0, 200);
            boolean checked = this.menu.hasMaterials(material, counted);
            RenderSystem.setShaderTexture(0, WorkbenchScreen.WORKBENCH_TEXTURE);
            GuiComponent.blit(poseStack, start + i * 18, top, checked ? 246 : 240, 40, 6, 5, 256, 256);
            poseStack.popPose();
        }
    }

    private ItemStack getStack(StackedIngredient material)
    {
        ItemStack[] items = material.ingredient().getItems();
        int index = (int) ((Util.getMillis() / 1000) % items.length);
        return items[index];
    }
}
