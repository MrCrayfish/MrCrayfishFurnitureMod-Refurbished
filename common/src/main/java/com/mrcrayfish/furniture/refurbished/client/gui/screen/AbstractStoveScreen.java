package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.inventory.IBakingMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IElectricityMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IPowerSwitchMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Author: MrCrayfish
 */
public class AbstractStoveScreen<T extends AbstractContainerMenu & IElectricityMenu & IPowerSwitchMenu & IBakingMenu> extends ElectricityContainerScreen<T>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/stove.png");

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
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.afterRender(poseStack, mouseX, mouseY, partialTick);
    }

    protected void afterRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);

        // Draw base gui background
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Draw animated fan
        int offset = this.menu.isPowered() && this.menu.isEnabled() ? (int) (Util.getMillis() / 100) % 3 : 0;
        this.blit(poseStack, this.leftPos + 32, this.topPos + 23, 176, 16 + offset * 40, 40, 40);

        for(int i = 0; i < 3; i++)
        {
            int progress = this.menu.getBakingProgress(i);
            int totalProgress = this.menu.getTotalBakingProgress(i);
            if(totalProgress == 0)
                continue;
            int height = (int) Math.ceil(16 * (progress / (float) totalProgress));
            this.blit(poseStack, this.leftPos + 84 + i * 18, this.topPos + 36, 190, 0, 17, height);
        }
    }
}
