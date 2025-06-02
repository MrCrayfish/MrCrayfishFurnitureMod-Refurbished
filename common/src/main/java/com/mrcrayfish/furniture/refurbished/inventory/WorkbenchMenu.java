package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.framework.api.Environment;
import com.mrcrayfish.framework.api.menu.IMenuData;
import com.mrcrayfish.framework.api.util.TaskRunner;
import com.mrcrayfish.furniture.refurbished.blockentity.IWorkbench;
import com.mrcrayfish.furniture.refurbished.blockentity.WorkbenchBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.ClientWorkbench;
import com.mrcrayfish.furniture.refurbished.client.ClientRecipes;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private final DataSlot searchNeighbours;
    private final List<RecipeHolder<WorkbenchContructingRecipe>> recipes;
    private final Map<ResourceLocation, Boolean> recipeToCraftable = new HashMap<>();
    private final ResultContainer result = new ResultContainer();
    private final Slot resultSlot;
    private Map<Integer, Integer> counts = new Int2IntOpenHashMap();
    private long lastSoundTime;
    private @Nullable Runnable updateCallback;

    public WorkbenchMenu(int windowId, Inventory playerInventory, CustomData data)
    {
        this(windowId, playerInventory, new ClientWorkbench(new SimpleContainer(12)), new SimpleContainerData(1));
        this.selectedRecipe.set(data.selectedRecipe());
        this.searchNeighbours.set(data.searchNeighbours());
        this.data.set(WorkbenchBlockEntity.DATA_POWERED, data.powered());
    }

    public WorkbenchMenu(int windowId, Inventory playerInventory, IWorkbench workbench, ContainerData data)
    {
        super(ModMenuTypes.WORKBENCH.get(), windowId, workbench.getWorkbenchContainer());
        checkContainerSize(workbench.getWorkbenchContainer(), 12);
        checkContainerDataCount(data, 1);
        workbench.getWorkbenchContainer().startOpen(playerInventory.player);
        this.workbench = workbench;
        this.level = playerInventory.player.level();
        this.access = workbench.createLevelAccess();
        this.data = data;
        this.selectedRecipe = workbench.selectedRecipeDataSlot();
        this.searchNeighbours = workbench.searchNeighboursDataSlot();
        this.recipes = this.setupRecipes(this.level);
        this.addContainerSlots(8, 18, 2, 6, 0);
        this.resultSlot = this.addSlot(new WorkbenchResultSlot(this.result, 0, 188, 21));
        this.addPlayerInventorySlots(28, 147, playerInventory);
        this.addDataSlot(this.selectedRecipe);
        this.addDataSlot(this.searchNeighbours);
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
    public List<RecipeHolder<WorkbenchContructingRecipe>> getRecipes()
    {
        return this.recipes;
    }

    private List<RecipeHolder<WorkbenchContructingRecipe>> setupRecipes(Level level)
    {
        List<RecipeHolder<WorkbenchContructingRecipe>> recipes = new ArrayList<>(this.getWorkbenchRecipeHolders(level));
        recipes.sort(Comparator.comparing(holder -> holder.value().getResultId()));
        return recipes;
    }

    private Collection<RecipeHolder<WorkbenchContructingRecipe>> getWorkbenchRecipeHolders(Level level)
    {
        if(level instanceof ServerLevel serverLevel)
        {
            return Services.RECIPE.getWorkbenchRecipes(serverLevel);
        }
        else if(level instanceof ClientLevel)
        {
            return TaskRunner.callIf(Environment.CLIENT, () -> ClientRecipes::get).map(ClientRecipes::workbenchRecipes).orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }

    private void updateResultSlot()
    {
        if(!this.level.isClientSide())
        {
            int selectedRecipeIndex = this.selectedRecipe.get();
            if(this.isPowered() && selectedRecipeIndex >= 0 && selectedRecipeIndex < this.recipes.size())
            {
                RecipeHolder<WorkbenchContructingRecipe> recipe = this.recipes.get(selectedRecipeIndex);
                if(this.workbench.canCraft(recipe))
                {
                    ItemStack result = this.result.getItem(0);
                    ItemStack output = recipe.value().getResult().copy();
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
    public RecipeHolder<WorkbenchContructingRecipe> getSelectedRecipe()
    {
        int index = this.selectedRecipe.get();
        return index != -1 ? this.recipes.get(index) : null;
    }

    public int getSelectedRecipeIndex()
    {
        return this.selectedRecipe.get();
    }

    public boolean shouldSearchNeighbours()
    {
        return this.searchNeighbours.get() != 0;
    }

    public void toggleSearchNeighbours()
    {
        this.searchNeighbours.set(this.shouldSearchNeighbours() ? 0 : 1);
        this.broadcastChanges();
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

    public boolean canCraft(RecipeHolder<WorkbenchContructingRecipe> recipe)
    {
        return this.isPowered() && this.recipeToCraftable.computeIfAbsent(recipe.id().location(), id -> {
            Map<Integer, Integer> found = new HashMap<>();
            for(StackedIngredient material : recipe.value().getMaterials()) {
                if(!this.hasMaterials(material, found)) {
                    return false;
                }
            }
            return true;
        });
    }

    public boolean hasMaterials(StackedIngredient material, Map<Integer, Integer> counted)
    {
        final MutableInt remaining = new MutableInt(material.count());
        material.ingredient().items()
            .takeWhile(holder -> remaining.getValue() > 0)
            .forEach(holder -> {
                int itemId = Item.getId(holder.value());
                int count = this.counts.getOrDefault(itemId, 0);
                count -= counted.getOrDefault(itemId, 0); // Remove already counted items
                if(count > 0) {
                    if(count >= remaining.getValue()) {
                        counted.merge(itemId, remaining.getValue(), Integer::sum);
                        remaining.setValue(0);
                        return;
                    }
                    counted.merge(itemId, count, Integer::sum);
                    remaining.decrement();
                }
            });
        return remaining.getValue() <= 0;
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
        RecipeHolder<WorkbenchContructingRecipe> recipe = this.getSelectedRecipe();
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

    public record CustomData(int selectedRecipe, int searchNeighbours, int powered) implements IMenuData<CustomData>
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, CustomData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            CustomData::selectedRecipe,
            ByteBufCodecs.VAR_INT,
            CustomData::searchNeighbours,
            ByteBufCodecs.VAR_INT,
            CustomData::powered,
            CustomData::new
        );

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CustomData> codec()
        {
            return STREAM_CODEC;
        }
    }
}
