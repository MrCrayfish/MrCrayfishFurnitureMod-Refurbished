package com.mrcrayfish.furniture.refurbished.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * Author: MrCrayfish
 */
public abstract class SimpleRecipeContainerMenu<I extends RecipeInput, R extends Recipe<I>> extends RecipeBookMenu<I, R>
{
    protected final Container container;

    protected SimpleRecipeContainerMenu(MenuType<?> type, int windowId, Container container)
    {
        super(type, windowId);
        this.container = container;
    }

    public Container getContainer()
    {
        return this.container;
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

    protected void addContainerSlots(int x, int y, int width, int height, int startIndex)
    {
        this.addContainerSlots(x, y, width, height, startIndex, Slot::new);
    }

    protected void addContainerSlots(int x, int y, int width, int height, int startIndex, SlotBuilder builder)
    {
        for(int j = 0; j < height; j++)
        {
            for(int i = 0; i < width; i++)
            {
                int slotIndex = startIndex + i + j * width;
                int slotX = x + i * 18;
                int slotY = y + j * 18;
                this.addSlot(builder.apply(this.container, slotIndex, slotX, slotY));
            }
        }
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

    @FunctionalInterface
    public interface SlotBuilder
    {
        Slot apply(Container container, int index, int x, int y);
    }
}
