package com.mrcrayfish.furniture.refurbished.computer.client.graphics;

import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.app.CoinMiner;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Icon;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Author: MrCrayfish
 */
public class CoinMinerGraphics extends DisplayableProgram<CoinMiner>
{
    public CoinMinerGraphics(CoinMiner program)
    {
        super(program, MAX_CONTENT_WIDTH / 2, MAX_CONTENT_HEIGHT / 2);
        this.setScene(new ComingSoon(this));
    }

    public static class ComingSoon extends Scene
    {
        private final CoinMinerGraphics program;

        public ComingSoon(CoinMinerGraphics program)
        {
            this.program = program;
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop) {}

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {
            Icon icon = Display.get().getIcon(this.program.getProgram().getId());
            if(icon != null)
            {
                graphics.blit(icon.texture(), (this.program.getWidth() - 16) / 2, 10, icon.u(), icon.v(), 16, 16, 128, 128);
            }
            graphics.drawCenteredString(Minecraft.getInstance().font, "Coming Soon!", MAX_CONTENT_WIDTH / 4, 35, 0xFFFFFFFF);
        }
    }
}
