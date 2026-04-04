package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.Block;

import java.util.List;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface BlockTintSourceRegister
{
    void apply(List<BlockTintSource> layers, Block... blocks);
}
