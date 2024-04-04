package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.ElectricityGeneratorBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.IPowerSwitch;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.inventory.slot.FuelSlot;
import com.mrcrayfish.furniture.refurbished.platform.Services;
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
public class ElectricityGeneratorMenu extends SimpleContainerMenu implements IPowerSwitchMenu
{
    private final ContainerData data;

    public ElectricityGeneratorMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new SimpleContainer(1), new SimpleContainerData(6));
    }

    public ElectricityGeneratorMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        super(ModMenuTypes.ELECTRICITY_GENERATOR.get(), windowId, container);
        checkContainerSize(container, 1);
        checkContainerDataCount(data, 6);
        container.startOpen(playerInventory.player);
        this.data = data;
        this.addSlot(new FuelSlot(container, 0, 26, 42));
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
            else if(this.isFuel(slotStack))
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

    private boolean isFuel(ItemStack stack)
    {
        return Services.ITEM.getBurnTime(stack, null) > 0;
    }

    public int getEnergy()
    {
        return this.data.get(ElectricityGeneratorBlockEntity.DATA_ENERGY);
    }

    public int getTotalEnergy()
    {
        return this.data.get(ElectricityGeneratorBlockEntity.DATA_TOTAL_ENERGY);
    }

    public boolean isEnabled()
    {
        return this.data.get(ElectricityGeneratorBlockEntity.DATA_ENABLED) != 0;
    }

    public boolean isOverloaded()
    {
        return this.data.get(ElectricityGeneratorBlockEntity.DATA_OVERLOADED) != 0;
    }

    public boolean isPowered()
    {
        return this.data.get(ElectricityGeneratorBlockEntity.DATA_POWERED) != 0;
    }

    public int getNodeCount()
    {
        return this.data.get(ElectricityGeneratorBlockEntity.DATA_NODE_COUNT);
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
