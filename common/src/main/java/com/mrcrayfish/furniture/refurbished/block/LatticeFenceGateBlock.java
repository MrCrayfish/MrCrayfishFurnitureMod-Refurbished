package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Author: MrCrayfish
 */
public class LatticeFenceGateBlock extends FenceGateBlock
{
    private final WoodType type;

    public LatticeFenceGateBlock(WoodType type, Properties properties)
    {
        super(properties, type);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }
}
