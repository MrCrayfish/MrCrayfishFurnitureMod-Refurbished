package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.CreativeFilters;
import com.mrcrayfish.furniture.refurbished.core.ModCreativeTabs;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin
{
    @Shadow
    private static CreativeModeTab selectedTab;

    @Inject(method = "mouseScrolled", at = @At(value = "HEAD"), cancellable = true)
    private void refurbishedFurnitureMouseScrollHead(double mouseX, double mouseY, double scroll, CallbackInfoReturnable<Boolean> cir)
    {
        if(CreativeFilters.get().onMouseScroll(mouseX, mouseY, scroll))
        {
            cir.setReturnValue(true);
        }
    }

    @ModifyArg(method = "renderLabels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"), index = 5)
    private boolean furnitureRefurbishedRenderLabels(boolean original)
    {
        return selectedTab == ModCreativeTabs.MAIN.get();
    }
}
