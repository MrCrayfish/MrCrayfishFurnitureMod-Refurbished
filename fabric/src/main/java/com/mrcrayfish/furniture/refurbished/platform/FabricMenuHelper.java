package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.inventory.FabricFreezerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.FabricMicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.inventory.FabricStoveMenu;
import com.mrcrayfish.furniture.refurbished.platform.services.IMenuHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;

/**
 * Author: MrCrayfish
 */
public class FabricMenuHelper implements IMenuHelper
{
    @Override
    public AbstractContainerMenu createFreezerMenu(int windowId, Inventory playerInventory)
    {
        return new FabricFreezerMenu(windowId, playerInventory);
    }

    @Override
    public AbstractContainerMenu createFreezerMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        return new FabricFreezerMenu(ModMenuTypes.FREEZER.get(), windowId, playerInventory, container, data);
    }

    @Override
    public AbstractContainerMenu createStoveMenu(int windowId, Inventory playerInventory)
    {
        return new FabricStoveMenu(windowId, playerInventory);
    }

    @Override
    public AbstractContainerMenu createStoveMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        return new FabricStoveMenu(windowId, playerInventory, container, data);
    }

    @Override
    public AbstractContainerMenu createMicrowaveMenu(int windowId, Inventory playerInventory)
    {
        return new FabricMicrowaveMenu(windowId, playerInventory);
    }

    @Override
    public AbstractContainerMenu createMicrowaveMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        return new FabricMicrowaveMenu(windowId, playerInventory, container, data);
    }
}
