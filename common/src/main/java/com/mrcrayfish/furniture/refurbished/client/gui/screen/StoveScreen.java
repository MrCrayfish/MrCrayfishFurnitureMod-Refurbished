package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class StoveScreen extends ElectricityContainerScreen<StoveMenu>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/stove.png");

    private OnOffSlider slider;

    public StoveScreen(StoveMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Components.GUI_TOGGLE_POWER, btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int offset = this.menu.isPowered() && this.menu.isEnabled() ? (int) (Util.getMillis() / 100) % 3 : 0;
        graphics.blit(TEXTURE, this.leftPos + 32, this.topPos + 23, 176, 16 + offset * 40, 40, 40);

        for(int i = 0; i < 3; i++)
        {
            int progress = this.menu.getBakingProgress(i);
            int totalProgress = this.menu.getTotalBakingProgress(i);
            if(totalProgress == 0)
                continue;
            int height = (int) Math.ceil(16 * (progress / (float) totalProgress));
            graphics.blit(TEXTURE, this.leftPos + 84 + i * 18, this.topPos + 36, 190, 0, 17, height);
        }
    }
}
