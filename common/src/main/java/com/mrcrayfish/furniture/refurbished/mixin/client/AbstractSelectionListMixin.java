package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.gui.ICustomSelectionList;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(AbstractSelectionList.class)
public class AbstractSelectionListMixin
{
    @Inject(method = "getEntryAtPosition", at = @At(value = "HEAD"), cancellable = true)
    private void refurbished_furniture$GetEntry(double mouseX, double mouseY, CallbackInfoReturnable<Object> cir)
    {
        AbstractSelectionList<?> list = (AbstractSelectionList<?>) (Object) this;
        if(list instanceof ICustomSelectionList<?> custom)
        {
            cir.setReturnValue(custom.getEntry(mouseX, mouseY));
        }
    }

    @Inject(method = "getFirstEntryY", at = @At(value = "HEAD"), cancellable = true)
    private void refurbished_furniture$ModifyFirstY(CallbackInfoReturnable<Integer> cir)
    {
        AbstractSelectionList<?> list = (AbstractSelectionList<?>) (Object) this;
        if(list instanceof ICustomSelectionList<?> custom)
        {
            cir.setReturnValue(custom.getStartEntryY());
        }
    }

    @Inject(method = "repositionEntries", at = @At(value = "HEAD"), cancellable = true)
    private void refurbished_furniture$RepositionEntries(CallbackInfo ci)
    {
        AbstractSelectionList<?> list = (AbstractSelectionList<?>) (Object) this;
        if(list instanceof ICustomSelectionList<?> custom)
        {
            custom.arrangeEntries();
            ci.cancel();
        }
    }
}
