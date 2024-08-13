package com.mrcrayfish.furniture.refurbished.platform.services;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;

/**
 * Author: MrCrayfish
 */
public interface IMenuHelper
{
    AbstractContainerMenu createFreezerMenu(int windowId, Inventory playerInventory);

    AbstractContainerMenu createFreezerMenu(int windowId, Inventory playerInventory, Container container, ContainerData data);

    AbstractContainerMenu createStoveMenu(int windowId, Inventory playerInventory);

    AbstractContainerMenu createStoveMenu(int windowId, Inventory playerInventory, Container container, ContainerData data);

    AbstractContainerMenu createMicrowaveMenu(int windowId, Inventory playerInventory);

    AbstractContainerMenu createMicrowaveMenu(int windowId, Inventory playerInventory, Container container, ContainerData data);
}
