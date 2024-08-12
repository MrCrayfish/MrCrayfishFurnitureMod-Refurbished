package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.client.LinkHandler;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(Minecraft.class)
public class FabricMinecraftMixin
{
    /**
     * Prevents from attacking while holding the Wrench
     */
    @Inject(method = "startAttack", at = @At(value = "HEAD"), cancellable = true)
    private void refurbishedFurniture$BeforeAttack(CallbackInfoReturnable<Boolean> cir)
    {
        Minecraft mc = (Minecraft) (Object) this;
        if(mc.player != null && mc.level != null)
        {
            if(mc.player.getMainHandItem().is(ModItems.WRENCH.get()))
            {
                if(LinkHandler.get().onWrenchLeftClick(mc.level))
                {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
