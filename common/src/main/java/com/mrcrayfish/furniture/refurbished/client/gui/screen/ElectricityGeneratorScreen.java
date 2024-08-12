package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.inventory.ElectricityGeneratorMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class ElectricityGeneratorScreen extends AbstractContainerScreen<ElectricityGeneratorMenu>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/electricity_generator.png");

    protected OnOffSlider slider;

    public ElectricityGeneratorScreen(ElectricityGeneratorMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Utils.translation("gui", "generator_toggle"), btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getEnergy() > 0 && this.menu.getTotalEnergy() > 0)
        {
            float normalEnergy = this.menu.getEnergy() / (float) this.menu.getTotalEnergy();
            int v = (int) Math.ceil(14 * normalEnergy);
            this.blit(poseStack, this.leftPos + 26, this.topPos + 25 + 14 - v, 176, 14 - v, 14, v);
        }
        Status status = this.getStatus();
        RenderSystem.setShaderTexture(0, IconButton.ICON_TEXTURES);
        GuiComponent.blit(poseStack, this.leftPos + 66, this.topPos + 29, status.iconU, status.iconV, 10, 10, 64, 64);
        GuiComponent.blit(poseStack, this.leftPos + 66, this.topPos + 46, 0, 10, 10, 10, 64, 64);
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY)
    {
        if(this.menu.getEnergy() > 0 && this.menu.getTotalEnergy() > 0 && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 26, this.topPos + 25, 14, 14))
        {
            this.renderTooltip(poseStack, Utils.translation("gui", "progress", this.menu.getEnergy(), Components.GUI_SLASH, this.menu.getTotalEnergy()), mouseX, mouseY);
            return;
        }
        super.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        super.renderLabels(poseStack, mouseX, mouseY);
        Status status = this.getStatus();
        ScreenHelper.drawString(poseStack, status.label, 80, 30, status.textColour, true);
        Pair<Component, Integer> pair = this.getNodeCount();
        ScreenHelper.drawString(poseStack, pair.left(), 80, 47, pair.right(), true);
    }

    private Pair<Component, Integer> getNodeCount()
    {
        int nodeCount = this.menu.getNodeCount();
        int maxNodeCount = Config.SERVER.electricity.maximumNodesInNetwork.get();
        Component label = Utils.translation("gui", "node_count", nodeCount, maxNodeCount);
        int textColour = nodeCount > maxNodeCount ? 0xFFC33636 : 0xFFFFFFFF;
        return Pair.of(label, textColour);
    }

    private Status getStatus()
    {
        if(this.menu.isOverloaded())
        {
            return Status.OVERLOADED;
        }
        else if(this.menu.isEnabled())
        {
            if(this.menu.isPowered())
            {
                return Status.ONLINE;
            }
            else if(this.menu.getEnergy() == 0)
            {
                return Status.NO_FUEL;
            }
        }
        return Status.OFFLINE;
    }

    private enum Status
    {
        ONLINE(0xFF79BB23, 50, 0, Utils.translation("gui", "status.online")),
        OFFLINE(0xFFC33636, 40, 0, Utils.translation("gui", "status.offline")),
        OVERLOADED(0xFFD69F2C, 30, 0, Utils.translation("gui", "status.overloaded")),
        NO_FUEL(0xFFD69F2C, 10, 10, Utils.translation("gui", "status.no_fuel"));

        private final int textColour;
        private final int iconU;
        private final int iconV;
        private final Component label;

        Status(int textColour, int iconU, int iconV, Component label)
        {
            this.textColour = textColour;
            this.iconU = iconU;
            this.iconV = iconV;
            this.label = label;
        }
    }
}
