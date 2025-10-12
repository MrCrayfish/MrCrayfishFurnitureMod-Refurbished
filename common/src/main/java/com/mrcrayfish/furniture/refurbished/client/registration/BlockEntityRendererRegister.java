package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface BlockEntityRendererRegister
{
    <T extends BlockEntity, S extends BlockEntityRenderState> void apply(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T, S> provider);
}
