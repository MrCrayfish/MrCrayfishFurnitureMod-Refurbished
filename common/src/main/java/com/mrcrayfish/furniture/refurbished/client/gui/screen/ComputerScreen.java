package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.blockentity.ComputerBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.ClientComputer;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class ComputerScreen extends AbstractContainerScreen<ComputerMenu> implements ContainerListener
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/computer.png");
    private static final int DISPLAY_LEFT = 15;
    private static final int DISPLAY_TOP = 15;
    private static final int DISPLAY_WIDTH = 226;
    private static final int DISPLAY_HEIGHT = 120;

    public ComputerScreen(ComputerMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 150;
        menu.setChangeListener(this);
    }

    // Stop default labels from rendering
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Draw the current program
        DisplayableProgram<?> displayable = ((ClientComputer) this.menu.getComputer()).getDisplayable();
        if(displayable != null)
        {
            int displayStart = this.leftPos + DISPLAY_LEFT;
            int displayTop = this.topPos + DISPLAY_TOP;
            int contentWidth = displayable.getWidth();
            int contentHeight = displayable.getHeight();
            int windowWidth = 1 + contentWidth + 1;
            int windowHeight = 11 + contentHeight + 1;
            int windowStart = displayStart + (DISPLAY_WIDTH - windowWidth) / 2;
            int windowTop = displayTop + (DISPLAY_HEIGHT - windowHeight) / 2;
            int windowEnd = windowStart + windowWidth;
            int windowBottom = windowTop + windowHeight;
            int titleBarHeight = 9;
            int titleBarStart = windowStart + 1;
            int titleBarTop = windowTop + 1;
            int titleBarEnd = windowStart + windowWidth - 1;
            int titleBarBottom = windowTop + 1 + titleBarHeight;
            int contentStart = windowStart + 1;
            int contentTop =  windowTop + 1 + titleBarHeight + 1;
            int contentEnd = windowStart + windowWidth - 1;
            int contentBottom = windowTop + windowHeight - 1;

            // Draw window
            graphics.fill(windowStart + 1, windowTop, windowEnd - 1, windowBottom, displayable.getWindowOutlineColour());
            graphics.fill(windowStart, windowTop + 1, windowEnd, windowBottom - 1, displayable.getWindowOutlineColour());
            graphics.fill(titleBarStart, titleBarTop, titleBarEnd, titleBarBottom, displayable.getWindowTitleBarColour());
            graphics.fill(contentStart, contentTop, contentEnd, contentBottom, displayable.getWindowBackgroundColour());
            graphics.drawString(this.font, displayable.getProgram().getTitle(), titleBarStart + 5, titleBarTop, 0xFFFFFFFF);

            // Draw content
            graphics.enableScissor(contentStart, contentTop, contentEnd, contentBottom);
            graphics.pose().translate(contentStart, contentTop, 0);
            displayable.render(graphics, mouseX, mouseY, partialTick);
            graphics.disableScissor();
        }
    }

    @Override
    public void dataChanged(AbstractContainerMenu menu, int dataIndex, int dataValue)
    {

    }

    @Override
    public void slotChanged(AbstractContainerMenu menu, int slotIndex, ItemStack stack) {}
}
