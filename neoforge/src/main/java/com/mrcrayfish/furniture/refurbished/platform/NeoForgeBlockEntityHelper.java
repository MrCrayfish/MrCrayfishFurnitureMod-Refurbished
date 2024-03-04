package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.NeoForgeStoveBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StoveBlockEntity;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class NeoForgeBlockEntityHelper implements IBlockEntityHelper
{
    @Override
    public RecycleBinBlockEntity createRecycleBinBlockEntity(BlockPos pos, BlockState state)
    {
        return new RecycleBinBlockEntity(pos, state);
    }

    @Override
    public StoveBlockEntity createStoveBlockEntity(BlockPos pos, BlockState state)
    {
        return new NeoForgeStoveBlockEntity(pos, state);
    }
}
