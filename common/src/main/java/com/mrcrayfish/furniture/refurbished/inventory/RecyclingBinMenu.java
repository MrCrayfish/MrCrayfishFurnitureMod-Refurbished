package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.inventory.slot.ResultSlot;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class RecyclingBinMenu extends SimpleContainerMenu
{
    private static Map<ResourceLocation, WeakReference<CraftingRecipe>> recipeLookup;

    private final Player player;
    private final Level level;

    public RecyclingBinMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new SimpleContainer(18));
    }

    public RecyclingBinMenu(int windowId, Inventory playerInventory, Container container)
    {
        super(ModMenuTypes.RECYCLING_BIN.get(), windowId, container);
        checkContainerSize(container, 18);
        container.startOpen(playerInventory.player);
        refreshRecipeLookup(playerInventory.player.level(), false);
        this.player = playerInventory.player;
        this.level = playerInventory.player.level();
        this.addContainerSlots(17, 20, 3, 3, 0);
        this.addContainerSlots(107, 20, 3, 3, 9, ResultSlot::new);
        this.addPlayerInventorySlots(8, 90, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if(slot.hasItem())
        {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();
            if(slotIndex < this.container.getContainerSize())
            {
                if(!this.moveItemStackTo(slotStack, this.container.getContainerSize(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slotStack, 0, 9, false))
            {
                return ItemStack.EMPTY;
            }
            if(slotStack.isEmpty())
            {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }
        }
        return stack;
    }

    public void recycleItems()
    {
        if(recipeLookup == null)
            return;

        // Create a simple container with the current output
        SimpleContainer output = new SimpleContainer(9);
        for(int i = 0; i < 9; i++)
        {
            output.setItem(i, this.container.getItem(i + 9));
        }

        for(int i = 0; i < 9; i++)
        {
            ItemStack result = this.container.getItem(i);
            if(result.isEmpty())
                continue;

            this.container.setItem(i, ItemStack.EMPTY);

            CraftingRecipe recipe = this.getRecipe(result.getItem());
            if(recipe == null)
                continue;

            int scale = recipe.getResultItem(this.level.registryAccess()).getCount();
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            if(ingredients.isEmpty())
                continue;

            for(int j = 0; j < result.getCount(); j++)
            {
                if(this.level.random.nextInt(2 * scale) != 0)
                    continue;

                Ingredient randomIngredient = ingredients.get(this.level.random.nextInt(ingredients.size()));
                ItemStack[] items = randomIngredient.getItems();
                if(items.length == 0)
                    continue;

                ItemStack randomStack = items[this.level.random.nextInt(items.length)];
                ItemStack copy = randomStack.copy();
                ItemStack remaining = output.addItem(copy);
                if(remaining.isEmpty())
                    continue;

                if(this.player.addItem(remaining))
                    continue;

                ItemEntity entity = this.player.drop(remaining, true);
                if(entity != null)
                {
                    entity.setNoPickUpDelay();
                }
            }
        }

        for(int i = 0; i < output.getContainerSize(); i++)
        {
            this.container.setItem(i + 9, output.getItem(i));
        }

        this.broadcastChanges();
    }

    private CraftingRecipe getRecipe(Item item)
    {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        WeakReference<CraftingRecipe> recipeRef = recipeLookup.get(id);
        if(recipeRef == null)
            return null;

        CraftingRecipe recipe = recipeRef.get();
        if(recipe != null)
            return recipe;

        refreshRecipeLookup(this.level, true);
        recipeRef = recipeLookup.get(id);
        return recipeRef != null ? recipeRef.get() : null;
    }

    private static void refreshRecipeLookup(Level level, boolean force)
    {
        if(!level.isClientSide() && (recipeLookup == null || force))
        {
            recipeLookup = new HashMap<>();
            List<CraftingRecipe> recipes = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
            recipes.forEach(recipe -> {
                if(!recipe.isSpecial() && !recipe.isIncomplete()) {
                    ItemStack result = recipe.getResultItem(level.registryAccess());
                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(result.getItem());
                    recipeLookup.putIfAbsent(id, new WeakReference<>(recipe));
                }
            });
        }
    }

    public static void clearRecipeLookup()
    {
        recipeLookup = null;
    }
}
