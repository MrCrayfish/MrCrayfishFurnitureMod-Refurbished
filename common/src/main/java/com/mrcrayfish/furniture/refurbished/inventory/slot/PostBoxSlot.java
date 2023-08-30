package com.mrcrayfish.furniture.refurbished.inventory.slot;

import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class PostBoxSlot extends Slot
{
    public PostBoxSlot(Container container, int slot, int x, int y)
    {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return !DeliveryService.isBannedItem(stack);
    }
}
