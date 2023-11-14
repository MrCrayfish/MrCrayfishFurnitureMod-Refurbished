package com.mrcrayfish.furniture.refurbished.computer.client.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class ComputerButton extends Button
{
    protected int outlineColour = 0xFF47403E;
    protected int outlineHighlightColour = 0xFF47403E;
    protected int backgroundColour = 0xFF222225;
    protected int backgroundHighlightColour = 0xFF47403E;
    protected int textColour = 0xFF5B5450;
    protected int textHighlightColour = 0xFF5B5450;
    protected int textDisabledColour = 0xFF5B5450;

    public ComputerButton(int width, int height, Component label, OnPress onPress)
    {
        super(0, 0, width, height, label, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        Minecraft mc = Minecraft.getInstance();
        graphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), this.getOutlineColour());
        graphics.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() - 1, this.getBackgroundColour());
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int messageWidth = mc.font.width(this.getMessage());
        graphics.drawString(mc.font, this.getMessage(), this.getX() + (this.getWidth() - messageWidth) / 2 + 1, this.getY() + (this.getHeight() - mc.font.lineHeight) / 2, this.getLabelColour(), false);
    }

    private int getLabelColour()
    {
        if(this.isActive())
        {
            if(this.isHoveredOrFocused())
            {
                return this.textHighlightColour;
            }
            return this.textColour;
        }
        return this.textDisabledColour;
    }

    private int getOutlineColour()
    {
        if(this.isActive() && this.isHoveredOrFocused())
        {
            return this.outlineHighlightColour;
        }
        return this.outlineColour;
    }

    private int getBackgroundColour()
    {
        if(this.isActive() && this.isHoveredOrFocused())
        {
            return this.backgroundHighlightColour;
        }
        return this.backgroundColour;
    }

    public void setOutlineColour(int outlineColour)
    {
        this.outlineColour = outlineColour;
    }

    public void setOutlineHighlightColour(int outlineHighlightColour)
    {
        this.outlineHighlightColour = outlineHighlightColour;
    }

    public void setBackgroundColour(int backgroundColour)
    {
        this.backgroundColour = backgroundColour;
    }

    public void setBackgroundHighlightColour(int backgroundHighlightColour)
    {
        this.backgroundHighlightColour = backgroundHighlightColour;
    }

    public void setTextColour(int textColour)
    {
        this.textColour = textColour;
    }

    public void setTextHighlightColour(int textHighlightColour)
    {
        this.textHighlightColour = textHighlightColour;
    }

    public void setTextDisabledColour(int textDisabledColour)
    {
        this.textDisabledColour = textDisabledColour;
    }
}
