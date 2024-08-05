package com.mrcrayfish.furniture.refurbished.client.screen;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.AbstractFreezerScreen;
import com.mrcrayfish.furniture.refurbished.inventory.FabricFreezerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class FabricFreezerScreen extends AbstractFreezerScreen<FabricFreezerMenu>
{
    public FabricFreezerScreen(FabricFreezerMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }
}
