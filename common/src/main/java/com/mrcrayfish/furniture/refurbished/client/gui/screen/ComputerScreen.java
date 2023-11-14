package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.client.ClientComputer;
import com.mrcrayfish.furniture.refurbished.computer.Window;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class ComputerScreen extends AbstractContainerScreen<ComputerMenu>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/computer.png");
    private static final int DISPLAY_LEFT = 15;
    private static final int DISPLAY_TOP = 15;
    private static final int DISPLAY_WIDTH = 226;
    private static final int DISPLAY_HEIGHT = 120;
    private @Nullable Window window;

    public ComputerScreen(ComputerMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 150;
        this.getComputer().setScreen(this);
    }

    public ClientComputer getComputer()
    {
        return (ClientComputer) this.menu.getComputer();
    }

    @Nullable
    public Window getOrCreateWindow()
    {
        DisplayableProgram<?> displayable = this.getComputer().getDisplayable();
        if(this.window == null && displayable != null)
        {
            this.window = new Window(displayable, this.getComputer());
            this.addRenderableWidget(this.window.getCloseButton());
            this.updateWindow();
        }
        else if(this.window != null && displayable == null)
        {
            this.removeWidget(this.window.getCloseButton());
            this.window = null;
        }
        return this.window;
    }

    private void updateWindow()
    {
        if(this.window != null)
        {
            this.window.update(this.leftPos + DISPLAY_LEFT, this.topPos + DISPLAY_TOP, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        }
    }

    @Override
    protected void repositionElements()
    {
        this.updateWindow();
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY)
    {
        // Stop default labels from rendering
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        // Draw background
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Draw window
        int displayLeft = this.leftPos + DISPLAY_LEFT;
        int displayTop = this.topPos + DISPLAY_TOP;
        int displayEnd = displayLeft + DISPLAY_WIDTH;
        int displayBottom = displayTop + DISPLAY_HEIGHT;
        graphics.enableScissor(displayLeft, displayTop, displayEnd, displayBottom);
        Window window = this.getOrCreateWindow();
        if(window != null) window.render(graphics, this.font, mouseX, mouseY, this.minecraft.getFrameTime());
        graphics.disableScissor();
    }

    @Override
    protected void containerTick()
    {
        if(this.window != null)
        {
            this.window.tick();
        }
    }

    public void addWidgets(IWidgetGroup group)
    {
        group.getWidgets().forEach(this::addWidget);
    }

    public void removeWidgets(IWidgetGroup group)
    {
        group.getWidgets().forEach(this::removeWidget);
    }
}
