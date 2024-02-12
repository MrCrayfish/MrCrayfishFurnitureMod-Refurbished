package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.gui.IOverrideGetEntry;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(AbstractSelectionList.class)
public class AbstractSelectionListMixin
{
    @Inject(method = "getEntryAtPosition", at = @At(value = "HEAD"), cancellable = true)
    private void refurbishedFurnitureGetEntry(double mouseX, double mouseY, CallbackInfoReturnable<Object> cir)
    {
        AbstractSelectionList<?> list = (AbstractSelectionList<?>) (Object) this;
        if(list instanceof IOverrideGetEntry<?> getter)
        {
            cir.setReturnValue(getter.getEntry(mouseX, mouseY));
        }
    }
}
