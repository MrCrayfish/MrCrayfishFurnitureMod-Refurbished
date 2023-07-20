package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public interface IBlockEntityHelper
{
    KitchenSinkBlockEntity createKitchenSinkBlockEntity(BlockPos pos, BlockState state);
}
