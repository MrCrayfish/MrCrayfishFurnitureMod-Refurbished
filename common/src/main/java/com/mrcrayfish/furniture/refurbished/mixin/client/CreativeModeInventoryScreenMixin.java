package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mrcrayfish.furniture.refurbished.client.CreativeFilters;
import com.mrcrayfish.furniture.refurbished.core.ModCreativeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
    private void refurbished_furniture$MouseScrollHead(double mouseX, double mouseY, double scrollX, double scrollY, CallbackInfoReturnable<Boolean> cir)
    {
        if(CreativeFilters.get().onMouseScroll(mouseX, mouseY, scrollY))
        {
            cir.setReturnValue(true);
        }
    }

    @WrapOperation(method = "extractLabels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"))
    private void refurbished_furniture$DrawCreativeTabTitle(GuiGraphicsExtractor graphics, Font font, Component component, int x, int y, int color, int shadow, Operation<Void> original)
    {
        if(selectedTab == ModCreativeTabs.MAIN.get())
        {
            int contentStart = 8;
            int contentTop = 4;
            int contentHeight = 12;
            int contentWidth = Minecraft.getInstance().font.width(selectedTab.getDisplayName()) + 4;
            graphics.fill(contentStart, contentTop + 1, contentStart + 1, contentTop + contentHeight - 1, 0x77000000);
            graphics.fill(contentStart + 1, contentTop, contentStart + contentWidth - 1, contentTop + contentHeight, 0x77000000);
            graphics.fill(contentStart + contentWidth - 1, contentTop + 1, contentStart + contentWidth, contentTop + contentHeight - 1, 0x77000000);
            graphics.text(font, component, 10, 6, color, false);
        }
        else
        {
            original.call(graphics, font, component, x, y, color, shadow);
        }
    }
}
