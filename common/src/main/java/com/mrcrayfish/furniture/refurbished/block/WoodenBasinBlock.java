package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Author: MrCrayfish
 */
public class WoodenBasinBlock extends BasinBlock
{
    private final WoodType type;

    public WoodenBasinBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }
}
