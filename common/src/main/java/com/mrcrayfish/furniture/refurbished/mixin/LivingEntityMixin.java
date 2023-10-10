package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.block.TrampolineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "jumpFromGround", at = @At(value = "TAIL"))
    private void refurbishedFurnitureOnJump(CallbackInfo ci)
    {
        LivingEntity entity = (LivingEntity) (Object) this;
        EntityAccessor accessor = (EntityAccessor) entity;
        BlockPos pos = accessor.refurbishedFurnitureBlockPosAffectsMovement();
        Level level = entity.level();
        BlockState state = level.getBlockState(pos);
        if(state.getBlock() instanceof TrampolineBlock block)
        {
            block.onLivingEntityJump(entity, level, state, pos);
        }
    }
}
