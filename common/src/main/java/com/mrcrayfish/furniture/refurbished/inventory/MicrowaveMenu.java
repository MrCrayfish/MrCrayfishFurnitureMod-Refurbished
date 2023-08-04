package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.MicrowaveBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.inventory.slot.ResultSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;

/**
 * Author: MrCrayfish
 */
public class MicrowaveMenu extends SimpleContainerMenu
{
    private final ContainerData data;

    public MicrowaveMenu(int windowId, Inventory playerInventory)
    {
        this(ModMenuTypes.MICROWAVE.get(), windowId, playerInventory, new SimpleContainer(2), new SimpleContainerData(2));
    }

    public MicrowaveMenu(MenuType<?> type, int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        super(type, windowId, container);
        checkContainerSize(container, 2);
        checkContainerDataCount(data, 2);
        container.startOpen(playerInventory.player);
        this.data = data;
        this.addSlot(new Slot(container, 0, 48, 35));
        this.addSlot(new ResultSlot(container, 1, 108, 35));
        this.addPlayerInventorySlots(8, 84, playerInventory);
        this.addDataSlots(data);
    }

    public int getProcessTime()
    {
        return this.data.get(MicrowaveBlockEntity.DATA_PROCESS_TIME);
    }

    public int getMaxProcessTime()
    {
        return this.data.get(MicrowaveBlockEntity.DATA_MAX_PROCESS_TIME);
    }
}
