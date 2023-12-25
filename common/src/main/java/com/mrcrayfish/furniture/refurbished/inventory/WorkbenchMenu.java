package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.IWorkbench;
import com.mrcrayfish.furniture.refurbished.client.ClientWorkbench;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class WorkbenchMenu extends SimpleContainerMenu implements IElectricityMenu
{
    private final IWorkbench workbench;
    private final Player player;
    private final Level level;
    private final List<WorkbenchCraftingRecipe> recipes;
    private final DataSlot selectedRecipe = DataSlot.standalone();
    private final Slot resultSlot;
    private Map<Integer, Integer> counts = new Int2IntOpenHashMap();
    private int updateTimer;

    public WorkbenchMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new ClientWorkbench(new SimpleContainer(9)));
    }

    public WorkbenchMenu(int windowId, Inventory playerInventory, IWorkbench workbench)
    {
        super(ModMenuTypes.WORKBENCH.get(), windowId, workbench.getWorkbenchContainer());
        checkContainerSize(workbench.getWorkbenchContainer(), 9);
        workbench.getWorkbenchContainer().startOpen(playerInventory.player);
        this.selectedRecipe.set(-1);
        this.workbench = workbench;
        this.player = playerInventory.player;
        this.level = playerInventory.player.level();
        this.recipes = this.setupRecipes(this.level);
        this.addContainerSlots(8, 18, 2, 4, 0);
        this.resultSlot = this.addSlot(new WorkbenchResultSlot(this.container, 8, 148, 21));
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

    private List<WorkbenchCraftingRecipe> setupRecipes(Level level)
    {
        List<WorkbenchCraftingRecipe> recipes = new ArrayList<>(level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.WORKBENCH_CRAFTING.get()));
        recipes.sort(Comparator.comparing(recipe -> Item.getId(recipe.getResultItem(level.registryAccess()).getItem())));
        return recipes;
    }

    private void updateResultSlot()
    {
        if(!this.level.isClientSide())
        {
            if(this.selectedRecipe.get() != -1)
            {
                WorkbenchCraftingRecipe recipe = this.recipes.get(this.selectedRecipe.get());
                if(this.workbench.canCraft(recipe))
                {
                    ItemStack result = this.getSlot(8).getItem();
                    ItemStack output = recipe.getResultItem(this.level.registryAccess());
                    if(!ItemStack.matches(result, output))
                    {
                        this.resultSlot.set(output.copy());
                    }
                }
                else
                {
                    this.resultSlot.set(ItemStack.EMPTY);
                }
            }
            else
            {
                this.resultSlot.set(ItemStack.EMPTY);
            }
            super.broadcastChanges();
        }
    }

    @Override
    public void broadcastChanges()
    {
        this.updateResultSlot();
        super.broadcastChanges();
    }

    @Override
    public boolean clickMenuButton(Player player, int button)
    {
        if(button >= 0 && button < this.recipes.size())
        {
            this.selectedRecipe.set(button);
            this.updateResultSlot();
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
            if(slotIndex == this.resultSlot.index)
            {
                Item item = slotStack.getItem();
                item.onCraftedBy(slotStack, player.level(), player);
                if(!this.moveItemStackTo(slotStack, this.container.getContainerSize(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, stack);
            }
            else if(slotIndex < this.container.getContainerSize())
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

            slot.setChanged();

            if(slotStack.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
            this.broadcastChanges();
        }
        return stack;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot)
    {
        return slot != this.resultSlot && super.canTakeItemForPickAll(stack, slot);
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
        public boolean mayPlace(ItemStack stack)
        {
            return false;
        }

        @Override
        public void onTake(Player player, ItemStack stack)
        {
            stack.onCraftedBy(player.level(), player, stack.getCount());
            WorkbenchMenu.this.onCraft();
            super.onTake(player, stack);
        }
    }

    private void onCraft()
    {
        WorkbenchCraftingRecipe recipe = this.getSelectedRecipe();
        if(recipe != null && this.workbench.canCraft(recipe))
        {
            this.workbench.performCraft(recipe);
            WorkbenchMenu.this.updateResultSlot();
        }
    }
}
