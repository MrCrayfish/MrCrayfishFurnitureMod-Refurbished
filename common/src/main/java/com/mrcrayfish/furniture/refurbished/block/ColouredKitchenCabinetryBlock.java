package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.item.DyeColor;

/**
 * Author: MrCrayfish
 */
public class ColouredKitchenCabinetryBlock extends KitchenCabinetryBlock
{
    private final DyeColor color;

    public ColouredKitchenCabinetryBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }
}
