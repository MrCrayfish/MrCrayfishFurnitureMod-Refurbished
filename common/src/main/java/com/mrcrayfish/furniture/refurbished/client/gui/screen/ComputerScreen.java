package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.client.ClientComputer;
import com.mrcrayfish.furniture.refurbished.computer.client.Desktop;
import com.mrcrayfish.furniture.refurbished.computer.client.Window;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.mixin.client.ScreenAccessor;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class ComputerScreen extends AbstractContainerScreen<ComputerMenu>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/computer.png");
    private static final int DISPLAY_LEFT = 15;
    private static final int DISPLAY_TOP = 15;
    public static final int DISPLAY_WIDTH = 226;
    public static final int DISPLAY_HEIGHT = 120;

    private final Desktop desktop;
    private @Nullable Window window;

    public ComputerScreen(ComputerMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.desktop = new Desktop(this);
        this.desktop.getShortcuts().forEach(this::addWidget);
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
            this.window.onClose();
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
    protected void init()
    {
        super.init();
        this.desktop.update(this.leftPos + DISPLAY_LEFT, this.topPos + DISPLAY_TOP, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }

    @Override
    protected void repositionElements()
    {
        super.init();
        this.updateWindow();
        this.desktop.update(this.leftPos + DISPLAY_LEFT, this.topPos + DISPLAY_TOP, DISPLAY_WIDTH, DISPLAY_HEIGHT);
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

        // Draw desktop and window
        int displayLeft = this.leftPos + DISPLAY_LEFT;
        int displayTop = this.topPos + DISPLAY_TOP;
        int displayEnd = displayLeft + DISPLAY_WIDTH;
        int displayBottom = displayTop + DISPLAY_HEIGHT;
        graphics.enableScissor(displayLeft, displayTop, displayEnd, displayBottom);
        this.desktop.render(graphics, mouseX, mouseY, partialTick);
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

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers)
    {
        if(this.window != null && this.window.getDisplayable().blocksNavigation())
        {
            if(key == GLFW.GLFW_KEY_UP || key == GLFW.GLFW_KEY_DOWN || key == GLFW.GLFW_KEY_LEFT || key == GLFW.GLFW_KEY_RIGHT)
            {
                return true;
            }
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    public void addWidgets(IWidgetGroup group)
    {
        ScreenAccessor accessor = (ScreenAccessor) this;
        group.getWidgets().forEach(listener -> {
            accessor.getChildren().add(listener);
            if(listener instanceof NarratableEntry entry) {
                accessor.getNarratables().add(entry);
            }
        });
    }

    public void removeWidgets(IWidgetGroup group)
    {
        group.getWidgets().forEach(this::removeWidget);
    }
}
