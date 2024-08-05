package com.mrcrayfish.furniture.refurbished.client.screen;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.AbstractStoveScreen;
import com.mrcrayfish.furniture.refurbished.inventory.FabricStoveMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class FabricStoveScreen extends AbstractStoveScreen<FabricStoveMenu>
{
    public FabricStoveScreen(FabricStoveMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }
}
