package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public interface IBlockEntityHelper
{
    KitchenSinkBlockEntity createKitchenSinkBlockEntity(BlockPos pos, BlockState state);

    <T extends BaseContainerBlockEntity & WorldlyContainer> void createForgeSidedWrapper(T container, @Nullable Direction side);

    void reviveForgeCapabilities(BlockEntity blockEntity);
}