package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface IScreenBuilder<T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>>
{
    U create(T menu, Inventory playerInventory, Component title);
}
