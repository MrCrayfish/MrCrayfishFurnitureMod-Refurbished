package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class StoveScreen extends AbstractContainerScreen<StoveMenu>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/stove.png");

    public StoveScreen(StoveMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
