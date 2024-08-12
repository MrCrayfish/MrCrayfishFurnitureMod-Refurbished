package com.mrcrayfish.furniture.refurbished.client.gui.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class ItemToast implements Toast
{
    private final Component title;
    private final Component description;
    private final ItemStack icon;

    public ItemToast(Component title, Component description, ItemStack icon)
    {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public Visibility render(PoseStack poseStack, ToastComponent component, long time)
    {
        RenderSystem.setShaderTexture(0, TEXTURE);
        component.blit(poseStack, 0, 0, 0, 32, this.width(), this.height());
        ScreenHelper.drawString(poseStack, this.title, 30, 7, 0xFF500050, false);
        ScreenHelper.drawString(poseStack, this.description, 30, 18, 0xFF000000, false);
        ScreenHelper.drawItem(this.icon, 8, 8);
        return time < 5000 ? Visibility.SHOW : Visibility.HIDE;
    }
}
