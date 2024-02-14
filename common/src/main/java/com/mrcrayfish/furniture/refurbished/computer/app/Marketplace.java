package com.mrcrayfish.furniture.refurbished.computer.app;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

/**
 * Author: MrCrayfish
 */
public class Marketplace extends Program
{
    public Marketplace(ResourceLocation id, IComputer computer)
    {
        super(id, computer);
    }

    @Override
    public void tick()
    {
        /*ComputerMenu menu = this.computer.getMenu();
        Player player = this.computer.getUser();
        if(player != null && menu != null)
        {
            Container enderChest = player.getEnderChestInventory();
            int count = Utils.countItem(Items.EMERALD, enderChest);
            menu.setProgramData(count);
        }*/
    }
}
