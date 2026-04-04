package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.inventory.IBakingMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IElectricityMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IPowerSwitchMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Author: MrCrayfish
 */
public class AbstractStoveScreen<T extends AbstractContainerMenu & IElectricityMenu & IPowerSwitchMenu & IBakingMenu> extends ElectricityContainerScreen<T>
{
    private static final Identifier TEXTURE = Utils.id("textures/gui/container/stove.png");

    protected OnOffSlider slider;

    public AbstractStoveScreen(T menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        this.initWidgets();
    }

    protected void initWidgets()
    {
        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Components.GUI_TOGGLE_POWER, btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        this.afterRender(graphics, mouseX, mouseY, partialTick);
    }

    protected void afterRender(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick)
    {
        this.extractTooltip(graphics, mouseX, mouseY);

        // TODO draw tooltip for progress
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        super.extractBackground(extractor, mouseX, mouseY, partialTick);
        extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int offset = this.menu.isPowered() && this.menu.isEnabled() ? (int) (Util.getMillis() / 100) % 3 : 0;
        extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos + 32, this.topPos + 23, 176, 16 + offset * 40, 40, 40, 256, 256);

        for(int i = 0; i < 3; i++)
        {
            int progress = this.menu.getBakingProgress(i);
            int totalProgress = this.menu.getTotalBakingProgress(i);
            if(totalProgress == 0)
                continue;
            int height = (int) Math.ceil(16 * (progress / (float) totalProgress));
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos + 84 + i * 18, this.topPos + 36, 190, 0, 17, height, 256, 256);
        }
    }
}
