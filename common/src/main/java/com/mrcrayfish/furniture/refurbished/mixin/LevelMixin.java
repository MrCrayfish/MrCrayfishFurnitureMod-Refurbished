package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.electricity.ElectricityTicker;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

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
}
