package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.ForgeKitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class ForgeBlockEntityHelper implements IBlockEntityHelper
{
    @Override
    public KitchenSinkBlockEntity createKitchenSinkBlockEntity(BlockPos pos, BlockState state)
    {
        return new ForgeKitchenSinkBlockEntity(pos, state);
    }
}
