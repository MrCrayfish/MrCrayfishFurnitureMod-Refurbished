package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.inventory.slot.ResultSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class FreezerMenu extends SimpleContainerMenu implements IElectricityMenu
{
    private final ContainerData data;
    private final Level level;

    public FreezerMenu(int windowId, Inventory playerInventory)
    {
        this(ModMenuTypes.FREEZER.get(), windowId, playerInventory, new SimpleContainer(2), new SimpleContainerData(3));
    }

    public FreezerMenu(MenuType<?> type, int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        super(type, windowId, container);
        checkContainerSize(container, 2);
        checkContainerDataCount(data, 3);
        container.startOpen(playerInventory.player);
        this.data = data;
        this.level = playerInventory.player.level();
        this.addSlot(new Slot(container, 0, 48, 35));
        this.addSlot(new ResultSlot(container, 1, 108, 35));
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
            else if(this.isRecipe(slotStack))
            {
                if(!this.moveItemStackTo(slotStack, 0, this.container.getContainerSize(), false))
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

    private boolean isRecipe(ItemStack stack)
    {
        return this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.FREEZER_SOLIDIFYING.get(), new SimpleContainer(stack), this.level).isPresent();
    }

    public int getProcessTime()
    {
        return this.data.get(FreezerBlockEntity.DATA_PROCESS_TIME);
    }

    public int getMaxProcessTime()
    {
        return this.data.get(FreezerBlockEntity.DATA_MAX_PROCESS_TIME);
    }

    @Override
    public boolean isPowered()
    {
        return this.data.get(FreezerBlockEntity.DATA_POWERED) != 0;
    }
}
