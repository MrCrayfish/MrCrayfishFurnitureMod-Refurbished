package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Author: MrCrayfish
 */
public class WoodenKitchenDrawerBlock extends KitchenDrawerBlock
{
    private final WoodType type;

    public WoodenKitchenDrawerBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }
}
