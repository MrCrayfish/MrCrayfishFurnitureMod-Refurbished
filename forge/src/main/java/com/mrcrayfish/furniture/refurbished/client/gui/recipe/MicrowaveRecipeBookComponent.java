package com.mrcrayfish.furniture.refurbished.client.gui.recipe;

import com.google.common.base.Suppliers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.crafting.display.MicrowaveRecipeDisplay;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MicrowaveRecipeBookComponent extends RecipeBookComponent<MicrowaveMenu>
{
    private static final Component ONLY_HEATABLE = Utils.translation("gui", "recipebook.toggleRecipes.heatable");
    private static final Supplier<List<TabInfo>> TABS = Suppliers.memoize(() -> List.of(
        new TabInfo(SearchRecipeBookCategory.CRAFTING),
        new TabInfo(Items.ICE, ModRecipeBookCategories.MICROWAVE_BLOCKS.get()),
        new TabInfo(Items.ROTTEN_FLESH, Items.HONEYCOMB, ModRecipeBookCategories.MICROWAVE_ITEMS.get()),
        new TabInfo(Items.PORKCHOP, ModRecipeBookCategories.MICROWAVE_FOOD.get()),
        new TabInfo(Items.LAVA_BUCKET, Items.FERN, ModRecipeBookCategories.MICROWAVE_MISC.get())
    ));
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
        Utils.resource("recipe_book/microwave_filter_enabled"),
        Utils.resource("recipe_book/microwave_filter_disabled"),
        Utils.resource("recipe_book/microwave_filter_enabled_highlighted"),
        Utils.resource("recipe_book/microwave_filter_disabled_highlighted")
    );

    public MicrowaveRecipeBookComponent(MicrowaveMenu menu)
    {
        super(menu, TABS.get());
    }

    @Override
    protected void initFilterButtonTextures()
    {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    protected boolean isCraftingSlot(Slot slot)
    {
        return slot.index == 0 || slot.index == 1;
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents contents)
    {
        collection.selectRecipes(contents, display -> display instanceof MicrowaveRecipeDisplay);
    }

    @Override
    protected Component getRecipeFilterName()
    {
        return ONLY_HEATABLE;
    }

    @Override
    protected void fillGhostRecipe(GhostSlots slots, RecipeDisplay display, ContextMap map)
    {
        slots.setResult(this.menu.getSlot(1), map, display.result());
        if(display instanceof MicrowaveRecipeDisplay microwaveDisplay)
        {
            slots.setInput(this.menu.getSlot(0), map, microwaveDisplay.ingredient());
        }
    }
}