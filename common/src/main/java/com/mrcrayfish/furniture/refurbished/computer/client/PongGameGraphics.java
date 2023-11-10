package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mrcrayfish.furniture.refurbished.computer.app.PongGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Author: MrCrayfish
 */
public class PongGameGraphics extends DisplayableProgram<PongGame>
{
    public PongGameGraphics(PongGame program)
    {
        super(program, 50, 50);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        Minecraft mc = Minecraft.getInstance();
        graphics.drawString(mc.font, program.getId().toString(), 0, 0, 0xFFFFFFFF);
    }
}
