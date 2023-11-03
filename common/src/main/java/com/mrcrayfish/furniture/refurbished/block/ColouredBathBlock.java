package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.item.DyeColor;

/**
 * Author: MrCrayfish
 */
public class ColouredBathBlock extends BathBlock
{
    private final DyeColor color;

    public ColouredBathBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }
}
