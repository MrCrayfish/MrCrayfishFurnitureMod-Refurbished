package com.mrcrayfish.furniture.refurbished.client.gui.overlay;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.LinkHandler;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.LinkManager;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Author: MrCrayfish
 */
public class NodeIndicatorOverlay implements IHudOverlay
{
    @Override
    public void draw(GuiGraphics graphics, float partialTick)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.level == null || mc.hitResult == null)
            return;

        if(mc.player == null)
            return;

        LinkHandler handler = LinkHandler.get();
        IElectricityNode target = handler.getTargetNode();
        if(handler.isLinking())
        {
            double linkLength = handler.getLinkLength();
            if(linkLength > LinkManager.MAX_LINK_LENGTH)
            {
                this.drawLabel(mc, graphics, Components.GUI_LINK_TOO_LONG, 40, 0);
                return;
            }

            IElectricityNode linking = handler.getLinkingNode(mc.level);
            if(target != null && linking != null && target != linking)
            {
                if(target.isSourceNode() && linking.isSourceNode())
                {
                    this.drawLabel(mc, graphics, Components.GUI_LINK_INVALID_NODE, 40, 0);
                    return;
                }

                int nodeLinkLength = (int) (linking.getNodePosition().getCenter().distanceTo(target.getNodePosition().getCenter()) + 0.5);
                if(nodeLinkLength > LinkManager.MAX_LINK_LENGTH)
                {
                    this.drawLabel(mc, graphics, Components.GUI_LINK_TOO_LONG, 40, 0);
                    return;
                }

                if(target.isNodeConnectionLimitReached())
                {
                    this.drawLabel(mc, graphics, Components.GUI_LINK_TOO_MANY, 40, 0);
                    return;
                }

                if(handler.canLinkToNode(mc.level, target))
                {
                    if(!handler.isLinkInsidePowerableArea())
                    {
                        this.drawLabel(mc, graphics, Components.GUI_LINK_UNPOWERABLE, 30, 0);
                        return;
                    }

                    Component label = Utils.translation("gui", "progress", target.getNodeConnections().size(), Components.GUI_SLASH, target.getNodeMaximumConnections());
                    this.drawLabel(mc, graphics, label, 0, 10);
                    return;
                }
            }

            if(!handler.isLinkInsidePowerableArea())
            {
                this.drawLabel(mc, graphics, Components.GUI_LINK_OUTSIDE_AREA, 40, 0);
                return;
            }
        }
        else if(target != null)
        {
            Component label = Utils.translation("gui", "progress", target.getNodeConnections().size(), Components.GUI_SLASH, target.getNodeMaximumConnections());
            this.drawLabel(mc, graphics, label, 0, 10);
            return;
        }

        Connection connection = handler.getTargetConnection();
        if(connection != null)
        {
            if(connection.isCrossingPowerableZone(mc.level))
            {
                this.drawLabel(mc, graphics, Components.GUI_LINK_OUTSIDE_AREA, 40, 0);
                return;
            }
        }

        if(!LinkHandler.isHoldingWrench() && mc.hitResult instanceof BlockHitResult result)
        {
            BlockEntity entity = mc.level.getBlockEntity(result.getBlockPos());
            if(entity instanceof IElectricityNode node1 && !node1.isNodeInPowerableNetwork())
            {
                this.drawLabel(mc, graphics, Components.GUI_NO_POWER, 20, 20);
            }
        }
    }

    private void drawLabel(Minecraft mc, GuiGraphics graphics, Component label, int iconU, int iconV)
    {
        int padding = 3;
        int iconSize = 10;
        int messageWidth = mc.font.width(label);
        int contentWidth = padding + iconSize + padding + messageWidth + padding;
        int contentHeight = padding + mc.font.lineHeight + padding;
        int contentStart = (graphics.guiWidth() - contentWidth) / 2;
        int contentTop = (graphics.guiHeight() - contentHeight) / 2 + 50;

        // Draw background with "rounded" corners
        graphics.fill(contentStart, contentTop + 1, contentStart + 1, contentTop + contentHeight - 1, 0x77000000);
        graphics.fill(contentStart + 1, contentTop, contentStart + contentWidth - 1, contentTop + contentHeight, 0x77000000);
        graphics.fill(contentStart + contentWidth - 1, contentTop + 1, contentStart + contentWidth, contentTop + contentHeight - 1, 0x77000000);

        // Draw icon
        graphics.blit(IconButton.ICON_TEXTURES, contentStart + padding, contentTop + padding, iconU, iconV, iconSize, iconSize, 64, 64);

        // Draw message
        graphics.drawString(mc.font, label, contentStart + padding + iconSize + padding, contentTop + padding + 1, 0xFFFFFFFF);
    }
}
