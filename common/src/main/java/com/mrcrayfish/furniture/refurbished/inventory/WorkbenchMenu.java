package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class WorkbenchMenu extends SimpleContainerMenu implements IElectricityMenu
{
    private final Level level;
    private final List<WorkbenchCraftingRecipe> recipes;
    private final DataSlot selectedRecipe = DataSlot.standalone();
    private Map<Integer, Integer> counts = new Int2IntOpenHashMap();

    public WorkbenchMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new SimpleContainer(9));
    }

    public WorkbenchMenu(int windowId, Inventory playerInventory, Container container)
    {
        super(ModMenuTypes.WORKBENCH.get(), windowId, container);
        checkContainerSize(container, 9);
        container.startOpen(playerInventory.player);
        this.selectedRecipe.set(-1);
        this.level = playerInventory.player.level();
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.WORKBENCH_CRAFTING.get());
        this.addContainerSlots(8, 18, 2, 4, 0);
        this.addSlot(new WorkbenchResultSlot(container, 8, 148, 21));
        this.addPlayerInventorySlots(8, 111, playerInventory);
        this.addDataSlot(this.selectedRecipe);
    }

    /**
     * @return The level of the player
     */
    public Level getLevel()
    {
        return this.level;
    }

    /**
     * @return An immutable list of all workbench crafting recipes
     */
    public List<WorkbenchCraftingRecipe> getRecipes()
    {
        return this.recipes;
    }

    @Override
    public boolean clickMenuButton(Player player, int button)
    {
        if(button >= 0 && button < this.recipes.size())
        {
            this.selectedRecipe.set(button);
            return true;
        }
        return false;
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
            else if(slotIndex < this.container.getContainerSize() + 27)
            {
                if(!this.moveItemStackTo(slotStack, this.container.getContainerSize() + 27, this.slots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slotStack, this.container.getContainerSize(), this.slots.size() - 9, false))
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

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Nullable
    public WorkbenchCraftingRecipe getSelectedRecipe()
    {
        int index = this.selectedRecipe.get();
        return index != -1 ? this.recipes.get(index) : null;
    }

    public int getSelectedRecipeIndex()
    {
        return this.selectedRecipe.get();
    }

    public void updateItemCounts(Map<Integer, Integer> counts)
    {
        this.counts = counts;
    }

    public Map<Integer, Integer> getCounts()
    {
        return this.counts;
    }

    public boolean canCraft(WorkbenchCraftingRecipe recipe)
    {
        for(StackedIngredient material : recipe.getMaterials())
        {
            if(!this.hasMaterials(material))
            {
                return false;
            }
        }
        return true;
    }

    public boolean hasMaterials(StackedIngredient material)
    {
        for(ItemStack stack : material.ingredient().getItems())
        {
            Integer count = this.counts.get(Item.getId(stack.getItem()));
            if(count == null || count < material.count())
            {
                return false;
            }
        }
        return true;
    }

    private class WorkbenchResultSlot extends Slot
    {
        public WorkbenchResultSlot(Container container, int slot, int x, int y)
        {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPickup(Player player)
        {
            return WorkbenchMenu.this.isPowered();
        }

        @Override
        public void onTake(Player player, ItemStack stack)
        {
            super.onTake(player, stack);
        }
    }
}
