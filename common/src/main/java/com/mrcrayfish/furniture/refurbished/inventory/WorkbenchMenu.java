package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.IWorkbench;
import com.mrcrayfish.furniture.refurbished.blockentity.WorkbenchBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.ClientWorkbench;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class WorkbenchMenu extends SimpleContainerMenu implements IElectricityMenu
{
    private final IWorkbench workbench;
    private final Level level;
    private final ContainerLevelAccess access;
    private final ContainerData data;
    private final DataSlot selectedRecipe;
    private final List<WorkbenchContructingRecipe> recipes;
    private final Map<ResourceLocation, Boolean> recipeToCraftable = new HashMap<>();
    private final Slot resultSlot;
    private Map<Integer, Integer> counts = new Int2IntOpenHashMap();
    private long lastSoundTime;
    private @Nullable Runnable updateCallback;

    public WorkbenchMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data)
    {
        this(windowId, playerInventory, new ClientWorkbench(new SimpleContainer(13)), new SimpleContainerData(1));
        this.selectedRecipe.set(data.readVarInt());
        this.data.set(WorkbenchBlockEntity.DATA_POWERED, data.readVarInt());
    }

    public WorkbenchMenu(int windowId, Inventory playerInventory, IWorkbench workbench, ContainerData data)
    {
        super(ModMenuTypes.WORKBENCH.get(), windowId, workbench.getWorkbenchContainer());
        checkContainerSize(workbench.getWorkbenchContainer(), 13);
        checkContainerDataCount(data, 1);
        workbench.getWorkbenchContainer().startOpen(playerInventory.player);
        this.workbench = workbench;
        this.level = playerInventory.player.level();
        this.access = workbench.createLevelAccess();
        this.data = data;
        this.selectedRecipe = workbench.getSelectedRecipeData();
        this.recipes = this.setupRecipes(this.level);
        this.addContainerSlots(8, 18, 2, 6, 0);
        this.resultSlot = this.addSlot(new WorkbenchResultSlot(this.container, WorkbenchBlockEntity.RESULT_SLOT, 188, 21));
        this.addPlayerInventorySlots(28, 147, playerInventory);
        this.addDataSlot(this.selectedRecipe);
        this.addDataSlots(this.data);
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
    public List<WorkbenchContructingRecipe> getRecipes()
    {
        return this.recipes;
    }

    private List<WorkbenchContructingRecipe> setupRecipes(Level level)
    {
        List<WorkbenchContructingRecipe> recipes = new ArrayList<>(level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.WORKBENCH_CONSTRUCTING.get()));
        recipes.sort(Comparator.comparing(WorkbenchContructingRecipe::getResultId));
        return recipes;
    }

    private void updateResultSlot()
    {
        if(!this.level.isClientSide())
        {
            int selectedRecipeIndex = this.selectedRecipe.get();
            if(this.isPowered() && selectedRecipeIndex >= 0 && selectedRecipeIndex < this.recipes.size())
            {
                WorkbenchContructingRecipe recipe = this.recipes.get(selectedRecipeIndex);
                if(this.workbench.canCraft(recipe))
                {
                    ItemStack result = this.getSlot(WorkbenchBlockEntity.RESULT_SLOT).getItem();
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
        return this.data.get(WorkbenchBlockEntity.DATA_POWERED) != 0;
    }

    @Nullable
    public WorkbenchContructingRecipe getSelectedRecipe()
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
        this.recipeToCraftable.clear();
        if(this.updateCallback != null)
        {
            this.updateCallback.run();
        }
    }

    public boolean canCraft(WorkbenchContructingRecipe recipe)
    {
        return this.isPowered() && this.recipeToCraftable.computeIfAbsent(recipe.getId(), id -> {
            Map<Integer, Integer> found = new HashMap<>();
            for(StackedIngredient material : recipe.getMaterials()) {
                if(!this.hasMaterials(material, found)) {
                    return false;
                }
            }
            return true;
        });
    }

    public boolean hasMaterials(StackedIngredient material, Map<Integer, Integer> counted)
    {
        int remaining = material.count();
        for(ItemStack stack : material.ingredient().getItems())
        {
            int itemId = Item.getId(stack.getItem());
            int count = this.counts.getOrDefault(itemId, 0);
            count -= counted.getOrDefault(itemId, 0); // Remove already counted items
            if(count > 0)
            {
                if(count >= remaining)
                {
                    counted.merge(itemId, remaining, Integer::sum);
                    remaining = 0;
                    break;
                }
                counted.merge(itemId, count, Integer::sum);
                remaining -= count;
            }
        }
        return remaining <= 0;
    }

    public void setUpdateCallback(Runnable callback)
    {
        this.updateCallback = callback;
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
        WorkbenchContructingRecipe recipe = this.getSelectedRecipe();
        if(recipe != null && this.workbench.canCraft(recipe))
        {
            this.workbench.performCraft(recipe);
            WorkbenchMenu.this.updateResultSlot();
            this.access.execute((level, pos) -> {
                long time = level.getGameTime();
                if(this.lastSoundTime != (this.lastSoundTime = time)) {
                    level.playSound(null, pos, ModSounds.BLOCK_WORKBENCH_CRAFT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            });
        }
    }
}
