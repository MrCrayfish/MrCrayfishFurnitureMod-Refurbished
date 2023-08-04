package com.mrcrayfish.furniture.refurbished.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public abstract class SimpleContainerMenu extends AbstractContainerMenu
{
    protected final Container container;

    protected SimpleContainerMenu(@Nullable MenuType<?> type, int windowId, Container container)
    {
        super(type, windowId);
        this.container = container;
    }

    public Container getContainer()
    {
        return this.container;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex)
    {
        return null;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        this.container.stopOpen(player);
    }

    /**
     *
     * @param x
     * @param y
     * @param playerInventory
     */
    protected void addPlayerInventorySlots(int x, int y, Inventory playerInventory)
    {
        for(int j = 0; j < 3; j++)
        {
            for(int i = 0; i < 9; i++)
            {
                int slotIndex = i + j * 9 + 9;
                int slotX = x + i * 18;
                int slotY = y + j * 18;
                this.addSlot(new Slot(playerInventory, slotIndex, slotX, slotY));
            }
        }
        for(int i = 0; i < 9; i++)
        {
            int slotX = x + i * 18;
            int slotY = y + 58;
            this.addSlot(new Slot(playerInventory, i, slotX, slotY));
        }
    }
}
