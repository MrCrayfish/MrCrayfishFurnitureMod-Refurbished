package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.IPaintable;
import com.mrcrayfish.furniture.refurbished.client.ClientPaintable;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class DoorMatMenu extends SimpleContainerMenu
{
    private final IPaintable paintable;

    public DoorMatMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new ClientPaintable(playerInventory.player));
    }

    public DoorMatMenu(int windowId, Inventory playerInventory, IPaintable paintable)
    {
        super(ModMenuTypes.DOOR_MAT.get(), windowId, new SimpleContainer(0));
        this.paintable = paintable;
        paintable.setPainter(playerInventory.player);
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        this.paintable.setPainter(null);
    }

    @Override
    public ItemStack quickMoveStack(Player var1, int var2)
    {
        // Unused
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.paintable.isValid(player);
    }

    public IPaintable getPaintable()
    {
        return this.paintable;
    }
}
