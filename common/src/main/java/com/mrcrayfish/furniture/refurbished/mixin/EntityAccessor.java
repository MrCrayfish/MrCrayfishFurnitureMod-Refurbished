package com.mrcrayfish.furniture.refurbished.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Author: MrCrayfish
 */
@Mixin(Entity.class)
public interface EntityAccessor
{
    @Invoker("getBlockPosBelowThatAffectsMyMovement")
    BlockPos refurbishedFurnitureBlockPosAffectsMovement();
}
