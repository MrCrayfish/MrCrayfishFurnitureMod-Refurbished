package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.electricity.ElectricityTicker;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(BlockEntity.class)
public class BlockEntityMixin
{
    @Inject(method = "setLevel", at = @At(value = "TAIL"))
    private void refurbishedFurniture$SetLevelTail(Level level, CallbackInfo ci)
    {
        if(this instanceof IElectricityNode node)
        {
            ElectricityTicker.get(level).addElectricityNode(node);
        }
    }
}
