package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class WorkbenchMenu extends SimpleContainerMenu implements IElectricityMenu
{
    private final Level level;
    private final List<WorkbenchCraftingRecipe> recipes;

    public WorkbenchMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new SimpleContainer(9));
    }

    public WorkbenchMenu(int windowId, Inventory playerInventory, Container container)
    {
        super(ModMenuTypes.WORKBENCH.get(), windowId, container);
        checkContainerSize(container, 9);
        container.startOpen(playerInventory.player);
        this.level = playerInventory.player.level();
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.WORKBENCH_CRAFTING.get());
        this.addContainerSlots(8, 18, 2, 4, 0);
        this.addSlot(new WorkbenchResultSlot(container, 8, 148, 21));
        this.addPlayerInventorySlots(8, 111, playerInventory);
    }

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
    }
}
