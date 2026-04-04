package com.mrcrayfish.furniture.refurbished.client.gui.toast;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class ItemToast implements Toast
{
    public static final Identifier TOAST_SPRITE = Identifier.withDefaultNamespace("toast/advancement");

    private final Component title;
    private final Component description;
    private final ItemStack icon;
    private Toast.Visibility visibility = Toast.Visibility.HIDE;

    public ItemToast(Component title, Component description, ItemStack icon)
    {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, Font font, long time)
    {
        extractor.blitSprite(RenderPipelines.GUI_TEXTURED, TOAST_SPRITE, 0, 0, this.width(), this.height());
        extractor.text(font, this.title, 30, 7, 0xFF500050, false);
        extractor.text(font, this.description, 30, 18, 0xFF000000, false);
        extractor.fakeItem(this.icon, 8, 8);
    }

    @Override
    public Visibility getWantedVisibility()
    {
        return this.visibility;
    }

    @Override
    public void update(ToastManager manager, long time)
    {
        this.visibility = time < 5000 ? Visibility.SHOW : Visibility.HIDE;
    }
}
