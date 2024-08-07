package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.inventory.IElectricityMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IPowerSwitchMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IProcessingMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Author: MrCrayfish
 */
public abstract class AbstractMicrowaveScreen<T extends AbstractContainerMenu & IElectricityMenu & IPowerSwitchMenu & IProcessingMenu> extends ElectricityContainerScreen<T>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/microwave.png");

    protected OnOffSlider slider;

    public AbstractMicrowaveScreen(T menu, Inventory inventory, Component title)
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
        RenderSystem.setShaderTexture(0, TEXTURE);
        GuiComponent.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getMaxProcessTime() > 0 && this.menu.getProcessTime() >= 0)
        {
            int width = (int) Math.ceil(25 * (this.menu.getProcessTime() / (float) this.menu.getMaxProcessTime()));
            GuiComponent.blit(poseStack, this.leftPos + 71, this.topPos + 34, 176, 0, width, 17);
        }
        if(this.menu.getProcessTime() > 0 && this.menu.getMaxProcessTime() > 0 && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 71, this.topPos + 34, 25, 17))
        {
            this.setTooltipForNextRenderPass(Utils.translation("gui", "progress", this.menu.getProcessTime(), Components.GUI_SLASH, this.menu.getMaxProcessTime()));
        }
    }
}
