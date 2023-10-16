package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface BlockColorsRegister
{
    void apply(BlockColor color, Block... blocks);
}
