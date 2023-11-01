package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Author: MrCrayfish
 */
public class WoodenBathBlock extends BathBlock
{
    private final WoodType type;

    public WoodenBathBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }
}
