package com.mrcrayfish.furniture.refurbished.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class OnOffSlider extends Button
{
    private static final ResourceLocation WIDGETS = Utils.resource("textures/gui/widgets.png");

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
        graphics.blit(RenderPipelines.GUI_TEXTURED, WIDGETS, this.getX(), this.getY(), 0, this.enabled ? this.getHeight() : 0, this.getWidth(), this.getHeight(), 64, 64);
    }
}
