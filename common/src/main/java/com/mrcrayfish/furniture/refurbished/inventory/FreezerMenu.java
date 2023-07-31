package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
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
public class FreezerMenu extends SimpleContainerMenu
{
    private final ContainerData data;

    public FreezerMenu(int windowId, Inventory playerInventory)
    {
        this(ModMenuTypes.FREEZER.get(), windowId, playerInventory, new SimpleContainer(2), new SimpleContainerData(2));
    }

    public FreezerMenu(MenuType<?> type, int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        super(type, windowId, container);
        checkContainerSize(container, 2);
        checkContainerDataCount(data, 2);
        this.data = data;
        this.addSlot(new Slot(container, 0, 48, 35));
        this.addSlot(new ResultSlot(container, 1, 108, 35));
        this.addPlayerInventorySlots(8, 84, playerInventory);
        this.addDataSlots(data);
    }

    public int getProcessTime()
    {
        return this.data.get(FreezerBlockEntity.DATA_PROCESS_TIME);
    }

    public int getMaxProcessTime()
    {
        return this.data.get(FreezerBlockEntity.DATA_MAX_PROCESS_TIME);
    }
}
