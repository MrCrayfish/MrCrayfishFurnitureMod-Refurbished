package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.IPowerSwitch;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class RecycleBinMenu extends SimpleContainerMenu implements IPowerSwitchMenu, IElectricityMenu
{
    private final ContainerData data;

    public RecycleBinMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public RecycleBinMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        super(ModMenuTypes.RECYCLE_BIN.get(), windowId, container);
        checkContainerSize(container, 3);
        checkContainerDataCount(data, 4);
        container.startOpen(playerInventory.player);
        this.data = data;
        this.addContainerSlots(26, 29, 3, 1, 0);
        this.addPlayerInventorySlots(8, 111, playerInventory);
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
            else if(!this.moveItemStackTo(slotStack, 0, 1, false))
            {
                return ItemStack.EMPTY;
            }
            if(slotStack.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }

            if(slotStack.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }
        return stack;
    }

    @Override
    public boolean isEnabled()
    {
        return this.data.get(RecycleBinBlockEntity.DATA_ENABLED) != 0;
    }

    @Override
    public boolean isPowered()
    {
        return this.data.get(RecycleBinBlockEntity.DATA_POWERED) != 0;
    }

    public int getProcessTime()
    {
        return this.data.get(RecycleBinBlockEntity.DATA_PROCESSING_TIME);
    }

    public int getRecycledCount()
    {
        return this.data.get(RecycleBinBlockEntity.DATA_RECYCLED);
    }

    @Override
    public void toggle()
    {
        if(this.container instanceof IPowerSwitch powerSwitch)
        {
            powerSwitch.togglePower();
        }
    }
}
