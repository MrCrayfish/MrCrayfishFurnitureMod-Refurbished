package com.mrcrayfish.furniture.refurbished.inventory.slot;

import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class FuelSlot extends Slot
{
    public FuelSlot(Container container, int slot, int x, int y)
    {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return Services.ITEM.getBurnTime(stack, null) > 0;
    }
}
