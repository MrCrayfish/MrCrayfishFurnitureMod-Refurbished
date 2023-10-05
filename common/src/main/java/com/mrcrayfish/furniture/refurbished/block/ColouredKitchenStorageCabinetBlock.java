package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.item.DyeColor;

/**
 * Author: MrCrayfish
 */
public class ColouredKitchenStorageCabinetBlock extends KitchenStorageCabinetBlock
{
    private final DyeColor color;

    public ColouredKitchenStorageCabinetBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }
}
