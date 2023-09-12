package com.mrcrayfish.furniture.refurbished.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class OnOffSlider extends Button
{
    private static final ResourceLocation WIDGETS = new ResourceLocation(Constants.MOD_ID, "textures/gui/widgets.png");

    private boolean enabled;

    public OnOffSlider(int x, int y, Component label)
    {
        super(x, y, 22, 12, label, btn -> {
            ((OnOffSlider) btn).enabled = !((OnOffSlider) btn).enabled;
        }, DEFAULT_NARRATION);
    }

    public OnOffSlider(int x, int y, Component label, OnPress onPress)
    {
        super(x, y, 22, 12, label, onPress, DEFAULT_NARRATION);
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        graphics.blit(WIDGETS, this.getX(), this.getY(), 0, this.enabled ? this.getHeight() : 0, this.getWidth(), this.getHeight(), 64, 64);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
