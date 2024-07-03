package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Author: MrCrayfish
 */
@Mixin(LevelChunk.class)
public class LevelChunkMixin
{
    @Shadow
    @Final
    Level level;

    @Inject(method = "removeBlockEntity", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.BY, by = 2), locals = LocalCapture.CAPTURE_FAILHARD)
    private void refurbishedFurniture$AfterRemoveBlockEntity(BlockPos pos, CallbackInfo ci, BlockEntity entity)
    {
        if(!this.level.isClientSide() && entity instanceof IElectricityNode node)
        {
            node.onNodeDestroyed();
        }
    }
}
