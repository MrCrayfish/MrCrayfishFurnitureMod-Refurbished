package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.inventory.IElectricityMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricityContainerScreen<T extends AbstractContainerMenu & IElectricityMenu> extends AbstractContainerScreen<T>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/widgets.png");

    protected ElectricityContainerScreen(T menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        if(!this.menu.isPowered())
        {
            PoseStack pose = graphics.pose();
            pose.pushPose();
            pose.translate(0, 0, 250);

            int iconSize = 10;
            int padding = 5;
            int messageWidth = this.minecraft.font.width(Components.GUI_NO_POWER);
            int contentWidth = iconSize + 3 + messageWidth;
            int bannerWidth = padding + contentWidth + padding;
            int bannerStart = this.leftPos + (this.imageWidth - bannerWidth) / 2;
            int bannerTop = this.getBannerTop();

            // Draw background
            graphics.blit(TEXTURE, bannerStart, bannerTop, 0, 46, 4, 18, 64, 64);
            graphics.blit(TEXTURE, bannerStart + 4, bannerTop, bannerWidth - 7, 18, 4, 46, 1, 18, 64, 64);
            graphics.blit(TEXTURE, bannerStart + 4 + bannerWidth - 7, bannerTop, 5, 46, 3, 18, 64, 64);

            // Draw icon
            graphics.blit(IconButton.ICON_TEXTURES, bannerStart + padding, bannerTop + 4, 20, 20, 10, 10, 64, 64);

            // Draw message
            graphics.drawString(this.minecraft.font, Components.GUI_NO_POWER, bannerStart + padding + iconSize + 3, bannerTop + 5, 0xFFFFFFFF);

            // Set tooltip if the mouse is hovering the banner
            if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, bannerStart, bannerTop, bannerWidth, 18))
            {
                Tooltip tooltip = ScreenHelper.createMultilineTooltip(List.of(
                    Components.GUI_NO_POWER.plainCopy().withStyle(ChatFormatting.RED),
                    Components.GUI_CONNECT_TO_POWER
                ));
                this.setTooltipForNextRenderPass(tooltip, DefaultTooltipPositioner.INSTANCE, false);this.setTooltipForNextRenderPass(tooltip, DefaultTooltipPositioner.INSTANCE, false);
            }

            pose.popPose();
        }
    }

    protected int getBannerTop()
    {
        return this.topPos - 22;
    }
}
