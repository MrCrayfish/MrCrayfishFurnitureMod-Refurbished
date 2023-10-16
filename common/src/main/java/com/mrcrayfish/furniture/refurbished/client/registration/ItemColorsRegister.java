package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface ItemColorsRegister
{
    void apply(ItemColor color, ItemLike... items);
}
