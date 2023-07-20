package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.FabricKitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class FabricBlockEntityHelper implements IBlockEntityHelper
{
    @Override
    public KitchenSinkBlockEntity createKitchenSinkBlockEntity(BlockPos pos, BlockState state)
    {
        return new FabricKitchenSinkBlockEntity(pos, state);
    }
}
