package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.block.TrampolineBlock;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Author: MrCrayfish
 */
public interface IBlockHelper
{
    TrampolineBlock createTrampolineBlock(DyeColor color, BlockBehaviour.Properties properties);

    DamageSource createDamageSource(String name);
}
