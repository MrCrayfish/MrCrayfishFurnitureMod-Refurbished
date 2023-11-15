package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.client.ClientComputer;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class Window
{
    public static final int TITLE_BAR_HEIGHT = 9;

    private final DisplayableProgram<?> displayable;
    private final ClientComputer computer;
    private final ComputerButton closeButton;
    private final int windowWidth;
    private final int windowHeight;
    private int windowStart;
    private int windowTop;
    private int contentStart;
    private int contentTop;

    public Window(DisplayableProgram<?> displayable, ClientComputer computer)
    {
        this.displayable = displayable;
        this.computer = computer;
        this.windowWidth = 1 + displayable.getWidth() + 1;
        this.windowHeight = 1 + TITLE_BAR_HEIGHT + 1 + displayable.getHeight() + 1;
        this.closeButton = new ComputerButton(9, 9, Component.literal("x"), btn -> {
            computer.setProgram(null);
        });
        this.closeButton.setTextOffset(-1);
        this.closeButton.setTextColour(displayable.getWindowTitleBarColour());
        this.closeButton.setTextHighlightColour(0xFFFFFFFF);
        this.closeButton.setOutlineColour(displayable.getWindowTitleLabelColour());
        this.closeButton.setOutlineHighlightColour(displayable.getWindowTitleLabelColour());
        this.closeButton.setBackgroundColour(displayable.getWindowTitleLabelColour());
    }

    public void update(int displayStart, int displayTop, int displayWidth, int displayHeight)
    {
        this.windowStart = displayStart + (displayWidth - this.windowWidth) / 2;
        this.windowTop = displayTop + (displayHeight - this.windowHeight) / 2;
        this.contentStart = this.windowStart + 1;
        this.contentTop =  this.windowTop + 1 + TITLE_BAR_HEIGHT + 1;
        this.displayable.update(this.contentStart, this.contentTop);
        this.closeButton.setPosition(this.windowStart + this.windowWidth - this.closeButton.getWidth() - 1, this.windowTop + 1);
    }

    public void tick()
    {
        this.displayable.tick();
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

    public void onClose()
    {
        this.displayable.onClose();
    }

    public DisplayableProgram<?> getDisplayable()
    {
        return this.displayable;
    }

    public Button getCloseButton()
    {
        return this.closeButton;
    }
}
