package com.mrcrayfish.furniture.refurbished.client.gui.widget;

import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

/**
 * Author: MrCrayfish
 */
public class IconButton extends Button
{
    public static final Identifier ICON_TEXTURES = Utils.id("textures/gui/icons.png");

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
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {
        this.extractDefaultSprite(graphics);
        Minecraft minecraft = Minecraft.getInstance();
        int contentWidth = 10 + minecraft.font.width(this.label) + (!this.label.getString().isEmpty() ? 4 : 0);
        int iconX = this.getX() + (this.width - contentWidth) / 2;
        int iconY = this.getY() + (this.height - 10) / 2;
        int brightness = ARGB.scaleRGB(0xFFFFFFFF, this.active ? 1.0F : 0.5F);
        graphics.blit(RenderPipelines.GUI_TEXTURED, ICON_TEXTURES, iconX, iconY, this.u, this.v, 10, 10, 64, 64, brightness);
        int textColor = (this.active ? 16777215 : 10526880) | 0xFF000000;
        //TODO fix
        //renderScrollingString(graphics, minecraft.font, this.label, iconX + 14, this.getY(), iconX + contentWidth, this.getY() + this.getHeight(), textColor);
    }
}
