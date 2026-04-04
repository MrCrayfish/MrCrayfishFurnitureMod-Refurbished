package com.mrcrayfish.furniture.refurbished.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(LevelChunk.class)
public class LevelChunkMixin
{
    @Shadow
    @Final
    Level level;

    @Definition(id = "removeThis", local = @Local(name = "removeThis", type = BlockEntity.class))
    @Expression("removeThis != null")
    @Inject(method = "removeBlockEntity", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private void refurbishedFurniture$AfterRemoveBlockEntity(BlockPos pos, CallbackInfo ci, @Local(name = "removeThis") BlockEntity entity)
    {
        if(!this.level.isClientSide() && entity instanceof IElectricityNode node)
        {
            node.onNodeDestroyed();
        }
    }
}
