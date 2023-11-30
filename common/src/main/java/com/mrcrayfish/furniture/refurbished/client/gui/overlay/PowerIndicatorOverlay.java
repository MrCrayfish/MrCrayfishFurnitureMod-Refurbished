package com.mrcrayfish.furniture.refurbished.client.gui.overlay;

import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.IModuleNode;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Author: MrCrayfish
 */
public class PowerIndicatorOverlay implements IHudOverlay
{
    private static final Component MESSAGE = Utils.translation("gui", "no_power");

    @Override
    public void draw(GuiGraphics graphics, float partialTick)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft.level == null || minecraft.hitResult == null || minecraft.hitResult.getType() != HitResult.Type.BLOCK)
            return;

        if(minecraft.player == null || !minecraft.player.isCrouching())
            return;

        BlockHitResult result = (BlockHitResult) minecraft.hitResult;
        BlockEntity entity = minecraft.level.getBlockEntity(result.getBlockPos());
        if(!(entity instanceof IElectricityNode node) || node.isPowered())
            return;

        int padding = 2;
        int iconSize = 10;
        int messageWidth = minecraft.font.width(MESSAGE);
        int contentWidth = padding + iconSize + padding + messageWidth + padding;
        int contentHeight = padding + minecraft.font.lineHeight + padding;
        int contentStart = (graphics.guiWidth() - contentWidth) / 2;
        int contentTop = (graphics.guiHeight() - contentHeight) / 2 + 50;

        // Draw background with "rounded" corners
        graphics.fill(contentStart, contentTop + 1, contentStart + 1, contentTop + contentHeight - 1, 0x77000000);
        graphics.fill(contentStart + 1, contentTop, contentStart + contentWidth - 1, contentTop + contentHeight, 0x77000000);
        graphics.fill(contentStart + contentWidth - 1, contentTop + 1, contentStart + contentWidth, contentTop + contentHeight - 1, 0x77000000);

        // Draw icon
        graphics.blit(IconButton.ICON_TEXTURES, contentStart + padding, contentTop + padding, 20, 20, iconSize, iconSize, 64, 64);

        // Draw message
        graphics.drawString(minecraft.font, MESSAGE, contentStart + padding + iconSize + padding, contentTop + padding + 1, 0xFFFFFFFF);
    }
}
