package com.mrcrayfish.furniture.refurbished.client.screen;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.AbstractMicrowaveScreen;
import com.mrcrayfish.furniture.refurbished.inventory.FabricMicrowaveMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class FabricMicrowaveScreen extends AbstractMicrowaveScreen<FabricMicrowaveMenu>
{
    public FabricMicrowaveScreen(FabricMicrowaveMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }
}
