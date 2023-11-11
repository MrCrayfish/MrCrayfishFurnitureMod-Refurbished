package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Author: MrCrayfish
 */
public class Window
{
    private static final int TITLE_BAR_HEIGHT = 9;

    private final DisplayableProgram<?> displayable;
    private final int windowWidth;
    private final int windowHeight;
    private int windowStart;
    private int windowTop;
    private int contentStart;
    private int contentTop;

    public Window(DisplayableProgram<?> displayable)
    {
        this.displayable = displayable;
        this.windowWidth = 1 + displayable.getWidth() + 1;
        this.windowHeight = 1 + TITLE_BAR_HEIGHT + 1 + displayable.getHeight() + 1;
    }

    public void update(int displayStart, int displayTop, int displayWidth, int displayHeight)
    {
        this.windowStart = displayStart + (displayWidth - this.windowWidth) / 2;
        this.windowTop = displayTop + (displayHeight - this.windowHeight) / 2;
        this.contentStart = this.windowStart + 1;
        this.contentTop =  this.windowTop + 1 + TITLE_BAR_HEIGHT + 1;
        this.displayable.update(this.contentStart, this.contentTop);
    }

    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTick)
    {
        int windowEnd = this.windowStart + this.windowWidth;
        int windowBottom = this.windowTop + this.windowHeight;
        int titleBarStart = this.windowStart + 1;
        int titleBarTop = this.windowTop + 1;
        int titleBarEnd = this.windowStart + this.windowWidth - 1;
        int titleBarBottom = this.windowTop + 1 + TITLE_BAR_HEIGHT;
        int contentEnd = this.windowStart + this.windowWidth - 1;
        int contentBottom = this.windowTop + this.windowHeight - 1;

        // Draw window frame
        graphics.fill(this.windowStart + 1, this.windowTop, windowEnd - 1, windowBottom, this.displayable.getWindowOutlineColour());
        graphics.fill(this.windowStart, this.windowTop + 1, windowEnd, windowBottom - 1, this.displayable.getWindowOutlineColour());
        graphics.fill(titleBarStart, titleBarTop, titleBarEnd, titleBarBottom, this.displayable.getWindowTitleBarColour());
        graphics.fill(this.contentStart, this.contentTop, contentEnd, contentBottom, this.displayable.getWindowBackgroundColour());
        graphics.drawString(font, this.displayable.getProgram().getTitle(), titleBarStart + 5, titleBarTop + 1, this.displayable.getWindowTitleLabelColour(), false);

        // Draw displayable content
        graphics.enableScissor(this.contentStart, this.contentTop, contentEnd, contentBottom);
        this.displayable.render(graphics, mouseX, mouseY, partialTick);
        graphics.disableScissor();
    }
}
