package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.blockentity.ForgeKitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
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
    public KitchenSinkBlockEntity createKitchenSinkBlockEntity(BlockPos pos, BlockState state)
    {
        return new ForgeKitchenSinkBlockEntity(pos, state);
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
