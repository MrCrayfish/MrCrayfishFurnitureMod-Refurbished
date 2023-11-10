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

    public DisplayableProgram(T program, int width, int height)
    {
        Preconditions.checkArgument(width >= 16 && width <= 224, "Width must be between 16 and 224 (inclusive)");
        Preconditions.checkArgument(height >= 16 && height <= 110, "Height must be between 16 and 110 (inclusive)");
        this.program = program;
        this.width = width;
        this.height = height;
    }

    public final T getProgram()
    {
        return this.program;
    }

    public abstract void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);
}
