package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.inventory.MailboxMenu;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class MailboxScreen extends ContainerScreen
{
    public MailboxScreen(MailboxMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }
}
