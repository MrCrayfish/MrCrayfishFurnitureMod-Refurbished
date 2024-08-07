package com.mrcrayfish.furniture.refurbished.computer.client.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.computer.client.Icon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class ProgramShortcutButton extends ComputerButton
{
    private final ComputerScreen screen;
    private final int index;
    private final Icon icon;

    public ProgramShortcutButton(ComputerScreen screen, int index, int width, int height, Component label, Icon icon, OnPress onPress)
    {
        super(width, height, label.copy(), onPress);
        this.screen = screen;
        this.index = index;
        this.icon = icon;
        this.setOutlineColour(0x00000000);
        this.setBackgroundColour(0x00000000);
        this.setBackgroundHighlightColour(0xFF59504E);
        this.setBackgroundDisabledColour(0x00000000);
        this.setOutlineDisabledColour(0x00000000);
        this.setTextDisabledColour(0x5559504E);
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.active = this.screen.getOrCreateWindow() == null;

        // Draw button
        GuiComponent.fill(poseStack, this.getX() + 1, this.getY(), this.getX() + this.getWidth() - 1, this.getY() + this.getHeight(), this.getOutlineColour());
        GuiComponent.fill(poseStack, this.getX(), this.getY() + 1, this.getX() + this.getWidth(), this.getY() + this.getHeight() - 1, this.getOutlineColour());
        GuiComponent.fill(poseStack, this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() - 1, this.getBackgroundColour());

        // Draw program icon
        RenderSystem.setShaderTexture(0, this.icon.texture());
        GuiComponent.blit(poseStack, this.getX() + (this.width - 16) / 2, this.getY() + 4, this.icon.u(), this.icon.v(), 16, 16, 128, 128);

        // Draw program name in unicode font
        poseStack.pushPose();
        Font font = Minecraft.getInstance().font;
        float scale = 0.666F;
        int labelWidth = font.width(this.getMessage());
        int labelX = (int) (this.getX() + (this.width - labelWidth * scale) / 2) + 1;
        int labelY = this.getY() + this.height - font.lineHeight;
        poseStack.translate(labelX, labelY, 0);
        poseStack.scale(scale, scale, scale);
        ScreenHelper.drawString(poseStack, this.getMessage(), 0, 0, this.getTextColour(), false);
        poseStack.popPose();
    }

    @Override
    protected boolean isValidClickButton(int button)
    {
        // Prevent clicks if a window is open
        return this.screen.getOrCreateWindow() == null;
    }

    public int getIndex()
    {
        return this.index;
    }
}
