package com.mrcrayfish.furniture.refurbished.client.gui.recipe;

import com.google.common.base.Suppliers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.crafting.display.OvenRecipeDisplay;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
public class OvenRecipeBookComponent extends RecipeBookComponent<StoveMenu>
{
    private static final Component ONLY_COOKABLE = Utils.translation("gui", "recipebook.toggleRecipes.cookable");
    private static final Supplier<List<TabInfo>> TABS = Suppliers.memoize(() -> List.of(
        new RecipeBookComponent.TabInfo(SearchRecipeBookCategory.CRAFTING),
        new RecipeBookComponent.TabInfo(Items.ICE, ModRecipeBookCategories.OVEN_BLOCKS.get()),
        new RecipeBookComponent.TabInfo(Items.ROTTEN_FLESH, Items.HONEYCOMB, ModRecipeBookCategories.OVEN_ITEMS.get()),
        new RecipeBookComponent.TabInfo(Items.PORKCHOP, ModRecipeBookCategories.OVEN_FOOD.get()),
        new RecipeBookComponent.TabInfo(Items.LAVA_BUCKET, Items.FERN, ModRecipeBookCategories.OVEN_MISC.get())
    ));
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
        Identifier.withDefaultNamespace("recipe_book/filter_enabled"),
        Identifier.withDefaultNamespace("recipe_book/filter_disabled"),
        Identifier.withDefaultNamespace("recipe_book/filter_enabled_highlighted"),
        Identifier.withDefaultNamespace("recipe_book/filter_disabled_highlighted")
    );

    public OvenRecipeBookComponent(StoveMenu menu)
    {
        super(menu, TABS.get());
    }

    @Override
    protected WidgetSprites getFilterButtonTextures()
    {
        return FILTER_SPRITES;
    }

    @Override
    protected boolean isCraftingSlot(Slot slot)
    {
        return false;
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents contents)
    {
        collection.selectRecipes(contents, display -> display instanceof OvenRecipeDisplay);
    }

    @Override
    protected Component getRecipeFilterName()
    {
        return ONLY_COOKABLE;
    }

    @Override
    protected void fillGhostRecipe(GhostSlots slots, RecipeDisplay display, ContextMap map)
    {
        slots.setResult(this.menu.getSlot(3), map, display.result());
        if(display instanceof OvenRecipeDisplay ovenDisplay)
        {
            slots.setInput(this.menu.getSlot(0), map, ovenDisplay.ingredient());
        }
    }
}
