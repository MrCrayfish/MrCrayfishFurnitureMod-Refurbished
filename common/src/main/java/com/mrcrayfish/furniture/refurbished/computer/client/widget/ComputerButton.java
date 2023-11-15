package com.mrcrayfish.furniture.refurbished.computer.client.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FastColor;

/**
 * Author: MrCrayfish
 */
public class ComputerButton extends Button
{
    protected int outlineColour = 0xFF47403E;
    protected int outlineHighlightColour = 0xFF47403E;
    protected int outlineDisabledColour = 0xFF332E2D;
    protected int backgroundColour = 0xFF222225;
    protected int backgroundHighlightColour = 0xFF47403E;
    protected int textColour = 0xFF5B5450;
    protected int textHighlightColour = 0xFFD3D3D3;
    protected int textDisabledColour = 0xFF332E2D;
    protected int textOffset;
    protected SoundEvent clickSound;

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
        graphics.drawString(mc.font, this.getMessage(), this.getX() + (this.getWidth() - messageWidth) / 2 + 1, this.getY() + (this.getHeight() - mc.font.lineHeight) / 2 + 1 + this.textOffset, this.getTextColour(), false);
    }

    @Override
    public void playDownSound(SoundManager manager)
    {
        if(this.clickSound != null)
        {
            manager.play(SimpleSoundInstance.forUI(this.clickSound, 1.0F, 0.5F));
            return;
        }
        super.playDownSound(manager);
    }

    /**
     * @return The text colour for the button based on its current state
     */
    private int getTextColour()
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

    /**
     * @return The outline colour for the button based on its current state
     */
    private int getOutlineColour()
    {
        if(this.isActive())
        {
            if(this.isHoveredOrFocused())
            {
                return this.outlineHighlightColour;
            }
            return this.outlineColour;
        }
        return this.outlineDisabledColour;
    }

    /**
     * @return The background colour for the button based on its current state
     */
    private int getBackgroundColour()
    {
        if(this.isActive() && this.isHoveredOrFocused())
        {
            return this.backgroundHighlightColour;
        }
        return this.backgroundColour;
    }

    /**
     * Sets the outline colour of the button when enabled
     *
     * @param outlineColour an ARGB integer of the colour. See {@link FastColor.ARGB32} for utilities
     */
    public void setOutlineColour(int outlineColour)
    {
        this.outlineColour = outlineColour;
    }

    /**
     * Sets the outline colour of the button when hovered/focused
     *
     * @param outlineHighlightColour an ARGB integer of the colour. See {@link FastColor.ARGB32} for utilities
     */
    public void setOutlineHighlightColour(int outlineHighlightColour)
    {
        this.outlineHighlightColour = outlineHighlightColour;
    }

    /**
     * Sets the background colour of the button when enabled
     *
     * @param backgroundColour an ARGB integer of the colour. See {@link FastColor.ARGB32} for utilities
     */
    public void setBackgroundColour(int backgroundColour)
    {
        this.backgroundColour = backgroundColour;
    }

    /**
     * Sets the background colour of the button when hovered/focused
     *
     * @param backgroundHighlightColour an ARGB integer of the colour. See {@link FastColor.ARGB32} for utilities
     */
    public void setBackgroundHighlightColour(int backgroundHighlightColour)
    {
        this.backgroundHighlightColour = backgroundHighlightColour;
    }

    /**
     * Sets the text colour of the button when enabled
     *
     * @param textColour an ARGB integer of the colour. See {@link FastColor.ARGB32} for utilities
     */
    public void setTextColour(int textColour)
    {
        this.textColour = textColour;
    }

    /**
     * Sets the text colour of the button when hovered/focused
     *
     * @param textHighlightColour an ARGB integer of the colour. See {@link FastColor.ARGB32} for utilities
     */
    public void setTextHighlightColour(int textHighlightColour)
    {
        this.textHighlightColour = textHighlightColour;
    }

    /**
     * Sets the text colour of the button when disabled
     *
     * @param textDisabledColour an ARGB integer of the colour. See {@link FastColor.ARGB32} for utilities
     */
    public void setTextDisabledColour(int textDisabledColour)
    {
        this.textDisabledColour = textDisabledColour;
    }

    public void setTextOffset(int textOffset)
    {
        this.textOffset = textOffset;
    }

    public void setClickSound(SoundEvent clickSound)
    {
        this.clickSound = clickSound;
    }
}
