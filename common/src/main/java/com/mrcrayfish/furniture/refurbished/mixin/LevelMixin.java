package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.electricity.ElectricityTicker;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(Level.class)
public abstract class LevelMixin implements ElectricityTicker.Access
{
    @Unique
    private ElectricityTicker refurbishedFurniture$electricityTicker;

    @Override
    public ElectricityTicker refurbishedFurniture$GetElectricityTicker()
    {
        if(this.refurbishedFurniture$electricityTicker == null)
        {
            this.refurbishedFurniture$electricityTicker = new ElectricityTicker((Level) (Object) this);
        }
        return this.refurbishedFurniture$electricityTicker;
    }

    @Inject(method = "tickBlockEntities", at = @At(value = "HEAD"))
    private void refurbishedFurniture$TickBlockEntitiesHead(CallbackInfo ci)
    {
        ElectricityTicker.get((Level) (Object) this).tick();
    }
}
