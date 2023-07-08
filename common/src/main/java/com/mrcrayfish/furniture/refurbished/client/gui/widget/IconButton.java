package com.mrcrayfish.furniture.refurbished.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * Author: MrCrayfish
 */
public class IconButton extends Button
{
    private static final ResourceLocation ICON_TEXTURES = new ResourceLocation(Constants.MOD_ID, "textures/gui/icons.png");

    private final Component label;
    private final int u, v;

    public IconButton(int x, int y, int u, int v, OnPress onPress)
    {
        this(x, y, u, v, 20, CommonComponents.EMPTY, onPress);
    }

    public IconButton(int x, int y, int u, int v, int width, Component label, OnPress onPress)
    {
        this(x, y, u, v, width, 20, label, onPress);
    }

    public IconButton(int x, int y, int u, int v, int width, int height, Component label, OnPress onPress)
    {
        super(x, y, width, height, CommonComponents.EMPTY, onPress, Button.DEFAULT_NARRATION);
        this.label = label;
        this.u = u;
        this.v = v;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        super.renderWidget(graphics, mouseX, mouseY, partialTicks);

        Minecraft mc = Minecraft.getInstance();
        int contentWidth = 10 + mc.font.width(this.label) + (!this.label.getString().isEmpty() ? 4 : 0);
        int contentLeft = (this.width - contentWidth) / 2;
        int iconX = this.getX() + contentLeft;
        int iconY = this.getY() + 5;
        float brightness = this.active ? 1.0F : 0.5F;
        RenderSystem.enableBlend();
        graphics.setColor(brightness, brightness, brightness, this.alpha);
        graphics.blit(ICON_TEXTURES, iconX, iconY, this.u, this.v, 10, 10, 64, 64);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        int labelColour = 0xFFFFFF | Mth.ceil(this.alpha * 255) << 24;
        renderScrollingString(graphics, mc.font, this.label, iconX + 14, iconY + 1, this.width, this.height, labelColour);
    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner()
    {
        return DefaultTooltipPositioner.INSTANCE;
    }
}
