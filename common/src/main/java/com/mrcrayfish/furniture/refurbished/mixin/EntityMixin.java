package com.mrcrayfish.furniture.refurbished.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mrcrayfish.furniture.refurbished.block.TrampolineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Author: MrCrayfish
 */
@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow
    private Level level;

    @Unique
    private float refurbishedFurniture$fallPower;

    @Shadow
    public abstract BlockPos getBlockPosBelowThatAffectsMyMovement();

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

    @SuppressWarnings({"DataFlowIssue", "deprecation"})
    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getOnPosLegacy()Lnet/minecraft/core/BlockPos;", ordinal = 0))
    private void refurbishedFurnitureTrampolinePhysics(MoverType type, Vec3 motion, CallbackInfo info, @Local(ordinal = 1) Vec3 moved)
    {
        Entity entity = (Entity) (Object) this;
        if(entity.onGround())
        {
            if(this.refurbishedFurniture$fallPower > 0)
            {
                BlockPos pos = entity.getOnPosLegacy();
                BlockState state = entity.level().getBlockState(pos);
                if(entity.isLocalInstanceAuthoritative())
                {
                    if(state.getBlock() instanceof TrampolineBlock trampoline)
                    {
                        trampoline.applyPhysics(pos, state, entity, this.refurbishedFurniture$fallPower);
                    }
                }
                this.refurbishedFurniture$fallPower = 0;
            }
        }
        else if(moved.y < 0.0)
        {
            this.refurbishedFurniture$fallPower -= (float) moved.y;
        }
    }
}
