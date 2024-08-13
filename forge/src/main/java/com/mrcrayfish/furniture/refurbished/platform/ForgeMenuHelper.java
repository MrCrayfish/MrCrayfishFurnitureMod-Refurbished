package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.platform.services.IMenuHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;

/**
 * Author: MrCrayfish
 */
public class ForgeMenuHelper implements IMenuHelper
{
    @Override
    public AbstractContainerMenu createFreezerMenu(int windowId, Inventory playerInventory)
    {
        return new FreezerMenu(windowId, playerInventory);
    }

    @Override
    public AbstractContainerMenu createFreezerMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        return new FreezerMenu(windowId, playerInventory, container, data);
    }

    @Override
    public AbstractContainerMenu createStoveMenu(int windowId, Inventory playerInventory)
    {
        return new StoveMenu(windowId, playerInventory);
    }

    @Override
    public AbstractContainerMenu createStoveMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        return new StoveMenu(windowId, playerInventory, container, data);
    }

    @Override
    public AbstractContainerMenu createMicrowaveMenu(int windowId, Inventory playerInventory)
    {
        return new MicrowaveMenu(windowId, playerInventory);
    }

    @Override
    public AbstractContainerMenu createMicrowaveMenu(int windowId, Inventory playerInventory, Container container, ContainerData data)
    {
        return new MicrowaveMenu(windowId, playerInventory, container, data);
    }
}
