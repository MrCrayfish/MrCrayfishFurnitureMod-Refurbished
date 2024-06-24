package com.mrcrayfish.furniture.refurbished.client.gui.recipe;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class EmptyRecipeBookComponent extends RecipeBookComponent
{
    @Override
    public void setupGhostRecipe(RecipeHolder<?> holder, List<Slot> slots) {}

    @Override
    protected void setVisible(boolean visible) {}

    @Override
    public boolean isVisible()
    {
        return false;
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {}

    @Override
    public void renderTooltip(GuiGraphics graphics, int $$1, int $$2, int $$3, int $$4) {}

    @Override
    public void tick() {}

    @Override
    public void recipesUpdated() {}

    @Override
    public void recipesShown(List<RecipeHolder<?>> list) {}

    @Override
    public void addItemToSlot(Iterator<Ingredient> $$0, int $$1, int $$2, int $$3, int $$4) {}

    @Override
    protected void sendUpdateSettings() {}

    @Override
    public void updateNarration(NarrationElementOutput output) {}
}
