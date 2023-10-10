package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.block.TrampolineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow
    private Level level;

    @Shadow
    protected abstract BlockPos getBlockPosBelowThatAffectsMyMovement();

    @Inject(method = "getBlockJumpFactor", at = @At(value = "RETURN"), cancellable = true)
    private void refurbishedFurnitureJumpFactor(CallbackInfoReturnable<Float> cir)
    {
        BlockPos pos = this.getBlockPosBelowThatAffectsMyMovement();
        BlockState state = this.level.getBlockState(pos);
        if(state.getBlock() instanceof TrampolineBlock block)
        {
            cir.setReturnValue(block.getJumpModifier(this.level, state, pos));
        }
    }
}
