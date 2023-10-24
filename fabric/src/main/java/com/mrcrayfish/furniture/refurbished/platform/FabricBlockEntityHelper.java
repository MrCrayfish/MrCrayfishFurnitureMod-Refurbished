package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class FabricBlockEntityHelper implements IBlockEntityHelper
{
    @Override
    public RecycleBinBlockEntity createRecycleBinBlockEntity(BlockPos pos, BlockState state)
    {
        return new RecycleBinBlockEntity(pos, state);
    }

    @Override
    public <T extends BaseContainerBlockEntity & WorldlyContainer> void createForgeSidedWrapper(T container, Direction side)
    {
        // Ignored
    }

    @Override
    public void reviveForgeCapabilities(BlockEntity blockEntity)
    {
        // Ignored
    }
}
