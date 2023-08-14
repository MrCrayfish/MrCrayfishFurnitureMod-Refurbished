package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;

/**
 * Author: MrCrayfish
 */
public class MailboxMenu extends ChestMenu
{
    public MailboxMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new SimpleContainer(9));
    }

    protected MailboxMenu(int windowId, Inventory playerInventory, Container container)
    {
        super(ModMenuTypes.MAIL_BOX.get(), windowId, playerInventory, container, 1);
    }
}
