package com.mrcrayfish.furniture.refurbished.computer.client.widget;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.computer.client.Icon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class ProgramShortcutButton extends ComputerButton
{
    private final ComputerScreen screen;
    private final int index;
    private final Icon icon;

    public ProgramShortcutButton(ComputerScreen screen, int index, int width, int height, Component label, Icon icon, OnPress onPress)
    {
        super(width, height, label.copy(), onPress);
        this.screen = screen;
        this.index = index;
        this.icon = icon;
        this.setOutlineColour(0x00000000);
        this.setBackgroundColour(0x00000000);
        this.setBackgroundHighlightColour(0xFF59504E);
        this.setBackgroundDisabledColour(0x00000000);
        this.setOutlineDisabledColour(0x00000000);
        this.setTextDisabledColour(0x5559504E);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        this.active = this.screen.getOrCreateWindow() == null;

        // Draw button
        extractor.fill(this.getX() + 1, this.getY(), this.getX() + this.getWidth() - 1, this.getY() + this.getHeight(), this.getOutlineColour());
        extractor.fill(this.getX(), this.getY() + 1, this.getX() + this.getWidth(), this.getY() + this.getHeight() - 1, this.getOutlineColour());
        extractor.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() - 1, this.getBackgroundColour());

        // Draw program icon
        extractor.blit(RenderPipelines.GUI_TEXTURED, this.icon.texture(), this.getX() + (this.width - 16) / 2, this.getY() + 4, this.icon.u(), this.icon.v(), 16, 16, 128, 128);

        // Draw program name in unicode font
        extractor.pose().pushMatrix();
        Font font = Minecraft.getInstance().font;
        float scale = 0.666F;
        int labelWidth = font.width(this.getMessage());
        int labelX = (int) (this.getX() + (this.width - labelWidth * scale) / 2) + 1;
        int labelY = this.getY() + this.height - font.lineHeight;
        extractor.pose().translate(labelX, labelY);
        extractor.pose().scale(scale, scale);
        extractor.text(Minecraft.getInstance().font, this.getMessage(), 0, 0, this.getTextColour(), false);
        extractor.pose().popMatrix();
    }

    @Override
    protected boolean isValidClickButton(MouseButtonInfo info)
    {
        // Prevent clicks if a window is open
        return this.screen.getOrCreateWindow() == null;
    }

    public int getIndex()
    {
        return this.index;
    }
}
