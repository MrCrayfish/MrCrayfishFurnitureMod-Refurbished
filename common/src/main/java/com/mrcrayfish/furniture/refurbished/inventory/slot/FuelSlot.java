package com.mrcrayfish.furniture.refurbished.inventory.slot;

import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class FuelSlot extends Slot
{
    private final Level level;

    public FuelSlot(Container container, Level level, int slot, int x, int y)
    {
        super(container, slot, x, y);
        this.level = level;
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return this.level.fuelValues().isFuel(stack);
    }
}
