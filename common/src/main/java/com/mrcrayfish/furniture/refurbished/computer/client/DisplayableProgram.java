package com.mrcrayfish.furniture.refurbished.computer.client;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Author: MrCrayfish
 */
public abstract class DisplayableProgram<T extends Program>
{
    protected final T program;
    protected final int width;
    protected final int height;
    protected int windowOutlineColour = 0xFF47403E;
    protected int windowTitleBarColour = 0xFF5B5450;
    protected int windowTitleLabelColour = 0xFF222225;
    protected int windowBackgroundColour = 0xFF222225;

    public DisplayableProgram(T program, int width, int height)
    {
        Preconditions.checkArgument(width >= 16 && width <= 224, "Width must be between 16 and 224 (inclusive)");
        Preconditions.checkArgument(height >= 16 && height <= 110, "Height must be between 16 and 110 (inclusive)");
        this.program = program;
        this.width = width;
        this.height = height;
    }

    public abstract void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);

    public final T getProgram()
    {
        return this.program;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setWindowOutlineColour(int colour)
    {
        this.windowOutlineColour = colour;
    }

    public int getWindowOutlineColour()
    {
        return this.windowOutlineColour;
    }

    public void setWindowTitleBarColour(int colour)
    {
        this.windowTitleBarColour = colour;
    }

    public int getWindowTitleBarColour()
    {
        return this.windowTitleBarColour;
    }

    public void setWindowTitleLabelColour(int colour)
    {
        this.windowTitleLabelColour = colour;
    }

    public int getWindowTitleLabelColour()
    {
        return this.windowTitleLabelColour;
    }

    public void setWindowBackgroundColour(int colour)
    {
        this.windowBackgroundColour = colour;
    }

    public int getWindowBackgroundColour()
    {
        return windowBackgroundColour;
    }
}
