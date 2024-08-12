package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.core.ModCreativeTabs;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin
{
    /* For consistency during backport */
    @Inject(method = "fillItemList", at = @At(value = "HEAD"), cancellable = true)
    private void refurbishedFurniture$CustomFillList(NonNullList<ItemStack> items, CallbackInfo ci)
    {
        // We only run logic on Forge, Fabric has its own handling
        if(!Services.PLATFORM.getPlatform().isForge())
            return;

        // Don't run if not out creative tab
        if(Services.PLATFORM.getCreativeModeTab() != (Object) this)
            return;

        // Build the creative tab items with our custom ordering
        ModCreativeTabs.buildCreativeModeTab(itemLike -> items.add(new ItemStack(itemLike)));
        ci.cancel();
    }
}
