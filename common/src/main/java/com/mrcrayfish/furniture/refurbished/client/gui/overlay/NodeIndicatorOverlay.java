package com.mrcrayfish.furniture.refurbished.client.gui.overlay;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.LinkHandler;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
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
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft.level == null || minecraft.hitResult == null)
            return;

        if(minecraft.player == null)
            return;

        Component label = null;
        int iconU = 0;
        int iconV = 0;

        LinkHandler handler = LinkHandler.get();
        IElectricityNode node = handler.getTargetNode();
        if(handler.isLinking() && handler.getLinkDistance() > Config.SERVER.electricity.maximumLinkDistance.get())
        {
            label = Utils.translation("gui", "link_too_long");
            iconU = 40;
        }
        else if(node != null)
        {
            label = Utils.translation("gui", "progress", node.getConnections().size(), Components.GUI_SLASH, Config.SERVER.electricity.maximumLinksPerElectricityNode.get());
            iconV = 10;
        }
        else if(minecraft.hitResult instanceof BlockHitResult result && minecraft.player.isCrouching())
        {
            BlockEntity entity = minecraft.level.getBlockEntity(result.getBlockPos());
            if(entity instanceof IElectricityNode node1 && !node1.isPowered())
            {
                label = Components.GUI_NO_POWER;
                iconU = 20;
                iconV = 20;
            }
        }

        if(label == null)
            return;

        int padding = 3;
        int iconSize = 10;
        int messageWidth = minecraft.font.width(label);
        int contentWidth = padding + iconSize + padding + messageWidth + padding;
        int contentHeight = padding + minecraft.font.lineHeight + padding;
        int contentStart = (graphics.guiWidth() - contentWidth) / 2;
        int contentTop = (graphics.guiHeight() - contentHeight) / 2 + 50;

        // Draw background with "rounded" corners
        graphics.fill(contentStart, contentTop + 1, contentStart + 1, contentTop + contentHeight - 1, 0x77000000);
        graphics.fill(contentStart + 1, contentTop, contentStart + contentWidth - 1, contentTop + contentHeight, 0x77000000);
        graphics.fill(contentStart + contentWidth - 1, contentTop + 1, contentStart + contentWidth, contentTop + contentHeight - 1, 0x77000000);

        // Draw icon
        graphics.blit(IconButton.ICON_TEXTURES, contentStart + padding, contentTop + padding, iconU, iconV, iconSize, iconSize, 64, 64);

        // Draw message
        graphics.drawString(minecraft.font, label, contentStart + padding + iconSize + padding, contentTop + padding + 1, 0xFFFFFFFF);
    }
}
