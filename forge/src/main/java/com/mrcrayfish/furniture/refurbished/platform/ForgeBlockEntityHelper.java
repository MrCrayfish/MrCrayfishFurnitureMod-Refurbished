package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.ForgeFreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.ForgeRecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.ForgeStoveBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StoveBlockEntity;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class ForgeBlockEntityHelper implements IBlockEntityHelper
{
    @Override
    public FreezerBlockEntity createFreezerBlockEntity(BlockPos pos, BlockState state)
    {
        return new ForgeFreezerBlockEntity(pos, state);
    }

    @Override
    public RecycleBinBlockEntity createRecycleBinBlockEntity(BlockPos pos, BlockState state)
    {
        return new ForgeRecycleBinBlockEntity(pos, state);
    }

    @Override
    public StoveBlockEntity createStoveBlockEntity(BlockPos pos, BlockState state)
    {
        return new ForgeStoveBlockEntity(pos, state);
    }

    @Override
    public <T extends BaseContainerBlockEntity & WorldlyContainer> void createForgeSidedWrapper(T container, @Nullable Direction side)
    {
        LazyOptional<IItemHandler> handler = LazyOptional.of(() -> new SidedInvWrapper(container, side));
        ObfuscationReflectionHelper.setPrivateValue(BaseContainerBlockEntity.class, container, handler, "itemHandler");
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void reviveForgeCapabilities(BlockEntity blockEntity)
    {
        ObfuscationReflectionHelper.setPrivateValue(CapabilityProvider.class, blockEntity, true, "valid");
    }
}
