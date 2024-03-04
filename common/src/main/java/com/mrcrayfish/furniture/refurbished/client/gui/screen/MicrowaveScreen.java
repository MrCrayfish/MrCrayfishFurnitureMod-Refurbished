package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class MicrowaveScreen extends ElectricityContainerScreen<MicrowaveMenu>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/microwave.png");

    private OnOffSlider slider;

    public MicrowaveScreen(MicrowaveMenu menu, Inventory inventory, Component title)
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
        if(this.menu.getMaxProcessTime() > 0 && this.menu.getProcessTime() >= 0)
        {
            int width = (int) Math.ceil(25 * (this.menu.getProcessTime() / (float) this.menu.getMaxProcessTime()));
            graphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 34, 176, 0, width, 17);
        }
        if(this.menu.getProcessTime() > 0 && this.menu.getMaxProcessTime() > 0 && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 71, this.topPos + 34, 25, 17))
        {
            this.setTooltipForNextRenderPass(Utils.translation("gui", "progress", this.menu.getProcessTime(), Components.GUI_SLASH, this.menu.getMaxProcessTime()));
        }
    }
}
