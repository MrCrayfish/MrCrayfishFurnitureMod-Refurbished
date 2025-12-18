package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.IPowerSwitch;
import com.mrcrayfish.furniture.refurbished.blockentity.StoveBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipePropertySets;
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.slot.ResultSlot;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class StoveMenu extends SimpleRecipeContainerMenu implements IPowerSwitchMenu, IElectricityMenu, IContainerHolder, IBakingMenu
{
    private final ContainerData data;
    private final Level level;
    private final RecipePropertySet recipeTest;

    public StoveMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new SimpleContainer(6), new SimpleContainerData(8));
    }

    public StoveMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        super(ModMenuTypes.STOVE.get(), windowId, container);
        checkContainerSize(container, 6);
        checkContainerDataCount(data, 8);
        container.startOpen(playerInventory.player);
        this.data = data;
        this.level = playerInventory.player.level();
        this.recipeTest = this.level.recipeAccess().propertySet(ModRecipePropertySets.OVEN_INPUT);
        this.addContainerSlots(85, 18, 3, 1, 0);
        this.addContainerSlots(85, 54, 3, 1, 3, ResultSlot::new);
        this.addPlayerInventorySlots(8, 84, playerInventory);
        this.addDataSlots(data);
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
            else if(this.recipeTest.test(slotStack))
            {
                if(!this.moveItemStackTo(slotStack, 0, 3, false))
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
    public int getBakingProgress(int index)
    {
        return switch(index) {
            case 0 -> this.data.get(StoveBlockEntity.DATA_PROGRESS_1);
            case 1 -> this.data.get(StoveBlockEntity.DATA_PROGRESS_2);
            case 2 -> this.data.get(StoveBlockEntity.DATA_PROGRESS_3);
            default -> 0;
        };
    }

    @Override
    public int getTotalBakingProgress(int index)
    {
        return switch(index) {
            case 0 -> this.data.get(StoveBlockEntity.DATA_TOTAL_PROGRESS_1);
            case 1 -> this.data.get(StoveBlockEntity.DATA_TOTAL_PROGRESS_2);
            case 2 -> this.data.get(StoveBlockEntity.DATA_TOTAL_PROGRESS_3);
            default -> 0;
        };
    }

    @Override
    public boolean isPowered()
    {
        return this.data.get(StoveBlockEntity.DATA_POWERED) != 0;
    }

    @Override
    public boolean isEnabled()
    {
        return this.data.get(StoveBlockEntity.DATA_ENABLED) != 0;
    }

    @Override
    public void toggle()
    {
        if(this.container instanceof IPowerSwitch powerSwitch)
        {
            powerSwitch.togglePower();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PostPlaceAction handlePlacement(boolean useMax, boolean creativeMode, RecipeHolder<?> holder, ServerLevel level, Inventory inventory)
    {
        RecipeHolder<OvenBakingRecipe> recipeHolder = (RecipeHolder<OvenBakingRecipe>) holder;
        final List<Slot> inputSlots = List.of(this.getSlot(0));
        final List<Slot> craftingSlots = List.of(this.getSlot(0), this.getSlot(3));
        ServerPlaceRecipe.CraftingMenuAccess<OvenBakingRecipe> access = new ServerPlaceRecipe.CraftingMenuAccess<>()
        {
            @Override
            public void fillCraftSlotsStackedContents(StackedItemContents contents)
            {
                StoveMenu.this.fillCraftSlotsStackedContents(contents);
            }

            @Override
            public void clearCraftingContent()
            {
                craftingSlots.forEach(slot -> slot.set(ItemStack.EMPTY));
            }

            @Override
            public boolean recipeMatches(RecipeHolder<OvenBakingRecipe> holder)
            {
                return holder.value().matches(new SingleRecipeInput(StoveMenu.this.container.getItem(0)), StoveMenu.this.level);
            }
        };
        return ServerPlaceRecipe.placeRecipe(access, 1, 1, inputSlots, craftingSlots, inventory, recipeHolder, useMax, creativeMode);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents contents)
    {
        if(this.container instanceof StackedContentsCompatible)
        {
            ((StackedContentsCompatible) this.container).fillStackedContents(contents);
        }
    }

    @Override
    public RecipeBookType getRecipeBookType()
    {
        return ModRecipeBookTypes.OVEN.get();
    }

    @Override
    public Container container()
    {
        return this.container;
    }
}
