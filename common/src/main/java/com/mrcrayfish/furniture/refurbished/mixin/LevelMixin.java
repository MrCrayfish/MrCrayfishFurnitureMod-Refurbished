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
    private final ElectricityTicker refurbished_furniture$electricityTicker = new ElectricityTicker((Level) (Object) this);

    @Override
    public ElectricityTicker refurbished_furniture$GetElectricityTicker()
    {
        return this.refurbished_furniture$electricityTicker;
    }

    @Inject(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 0))
    private void refurbished_furniture$TickBlockEntitiesHead(CallbackInfo ci)
    {
        this.refurbished_furniture$electricityTicker.tick();
    }
}
