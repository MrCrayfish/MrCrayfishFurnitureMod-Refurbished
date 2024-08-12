package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.CreativeFilters;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.core.ModCreativeTabs;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin
{
    @Shadow
    private static int selectedTab;

    @Inject(method = "mouseScrolled", at = @At(value = "HEAD"), cancellable = true)
    private void refurbishedFurnitureMouseScrollHead(double mouseX, double mouseY, double scroll, CallbackInfoReturnable<Boolean> cir)
    {
        if(CreativeFilters.get().onMouseScroll(mouseX, mouseY, scroll))
        {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "renderLabels", at = @At(value = "HEAD"), cancellable = true)
    private void furnitureRefurbishedBeforeRenderLabels(PoseStack poseStack, int mouseX, int mouseY, CallbackInfo ci)
    {
        if(selectedTab == Services.PLATFORM.getCreativeModeTab().getId())
        {
            int contentStart = 8;
            int contentTop = 4;
            int contentHeight = 12;
            int contentWidth = Minecraft.getInstance().font.width(Services.PLATFORM.getCreativeModeTab().getDisplayName()) + 4;
            GuiComponent.fill(poseStack, contentStart, contentTop + 1, contentStart + 1, contentTop + contentHeight - 1, 0x77000000);
            GuiComponent.fill(poseStack, contentStart + 1, contentTop, contentStart + contentWidth - 1, contentTop + contentHeight, 0x77000000);
            GuiComponent.fill(poseStack, contentStart + contentWidth - 1, contentTop + 1, contentStart + contentWidth, contentTop + contentHeight - 1, 0x77000000);
            ScreenHelper.drawString(poseStack, Services.PLATFORM.getCreativeModeTab().getDisplayName(), 10, 6, 0xFFFFAA00, true);
            ci.cancel();
        }
    }
}
