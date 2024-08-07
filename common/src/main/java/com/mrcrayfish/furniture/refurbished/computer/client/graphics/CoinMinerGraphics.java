package com.mrcrayfish.furniture.refurbished.computer.client.graphics;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.app.CoinMiner;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Icon;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

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
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
        {
            Icon icon = Display.get().getIcon(this.program.getProgram().getId());
            if(icon != null)
            {
                RenderSystem.setShaderTexture(0, icon.texture());
                GuiComponent.blit(poseStack, (this.program.getWidth() - 16) / 2, 10, icon.u(), icon.v(), 16, 16, 128, 128);
            }
            GuiComponent.drawCenteredString(poseStack, Minecraft.getInstance().font, "Coming Soon!", MAX_CONTENT_WIDTH / 4, 35, 0xFFFFFFFF);
        }
    }
}
