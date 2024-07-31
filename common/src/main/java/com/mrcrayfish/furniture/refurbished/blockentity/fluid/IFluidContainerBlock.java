package com.mrcrayfish.furniture.refurbished.blockentity.fluid;

import org.jetbrains.annotations.Nullable;

/**
 * Implement on BlockEntities to add a fluid storage
 *
 * Author: MrCrayfish
 */
public interface IFluidContainerBlock
{
    @Nullable
    FluidContainer getFluidContainer();
}
