package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.block.TrampolineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(LivingEntity.class)
public class FabricLivingEntityMixin
{
    @Inject(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"), cancellable = true)
    private void refurbishedFurnitureBeforeLandingParticle(double d, boolean bl, BlockState state, BlockPos pos, CallbackInfo ci)
    {
        // Prevents spawning landing particles when landing on a trampoline
        if(state.getBlock() instanceof TrampolineBlock)
        {
            ci.cancel();
        }
    }
}
