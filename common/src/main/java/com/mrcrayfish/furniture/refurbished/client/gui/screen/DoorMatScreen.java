package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.blockentity.DoorMatBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import com.mrcrayfish.furniture.refurbished.inventory.DoorMatMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageUpdatePainting;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Author: MrCrayfish
 */
public class DoorMatScreen extends AbstractContainerScreen<DoorMatMenu>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/door_mat.png");

    private final PaletteImage image = new PaletteImage(DoorMatBlockEntity.IMAGE_WIDTH, DoorMatBlockEntity.IMAGE_HEIGHT);
    private Tool currentTool = Tool.PENCIL;
    private @Nullable Tool activeTool;
    private int selectedColourIndex = 1;

    public DoorMatScreen(DoorMatMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageWidth = 134;
        this.imageHeight = 103;
    }

    @Override
    protected void init()
    {
        super.init();
        for(int i = 1; i < PaletteImage.COLOURS.length; i++)
        {
            this.addRenderableWidget(new ColourButton(this.leftPos + (i - 1) * 8 + 7, this.topPos + 65, i));
        }
        this.addRenderableWidget(new ToolButton(this.leftPos + 7, this.topPos + 77, 0, 0, Tool.PENCIL));
        this.addRenderableWidget(new ToolButton(this.leftPos + 30, this.topPos + 77, 0, 0, Tool.ERASER));
        this.addRenderableWidget(new ToolButton(this.leftPos + 53, this.topPos + 77, 0, 0, Tool.FILL));
        this.addRenderableWidget(Button.builder(Utils.translation("gui", "save"), var1 -> {
            Network.getPlay().sendToServer(new MessageUpdatePainting(this.image));
        }).pos(this.leftPos + 79, this.topPos + 77).size(49, 20).build());
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        this.renderBackground(graphics);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        for(int y = 0; y < this.image.getHeight(); y++)
        {
            for(int x = 0; x < this.image.getWidth(); x++)
            {
                int pixelStart = this.leftPos + 39 + x * 4;
                int pixelEnd = pixelStart + 4;
                int pixelTop = this.topPos + 4 + y * 4;
                int pixelBottom = pixelTop + 4;
                graphics.fill(pixelStart, pixelTop, pixelEnd, pixelBottom, PaletteImage.COLOURS[this.image.get(x, y)]);
            }
        }

        if(this.activeTool != null)
        {
            this.activateTool(mouseX, mouseY, true);
        }
    }

    private boolean activateTool(double mouseX, double mouseY, boolean continuous)
    {
        int start = this.leftPos + 39;
        int end = start + 56;
        int top = this.topPos + 4;
        int bottom = top + 40;
        if(mouseX >= start && mouseX < end && mouseY >= top && mouseY < bottom)
        {
            int x = (int) ((mouseX - start) / 4);
            int y = (int) ((mouseY - top) / 4);
            if(this.currentTool.getFunction().apply(this.image, x, y, this.selectedColourIndex) && !continuous)
            {
                this.activeTool = this.currentTool;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            if(this.activateTool(mouseX, mouseY, false))
            {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if(this.activeTool != null && button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            this.activeTool = null;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private class ToolButton extends IconButton
    {
        private final Tool tool;

        public ToolButton(int x, int y, int u, int v, Tool tool)
        {
            super(x, y, u, v, btn -> DoorMatScreen.this.currentTool = tool);
            this.tool = tool;
        }

        @Override
        public boolean isHovered()
        {
            return super.isHovered() || DoorMatScreen.this.currentTool == this.tool;
        }
    }

    private class ColourButton extends ComputerButton
    {
        private final int colourIndex;

        protected ColourButton(int x, int y, int colourIndex)
        {
            super(8, 8, CommonComponents.EMPTY, btn -> {
                DoorMatScreen.this.selectedColourIndex = colourIndex;
            });
            this.setPosition(x, y);
            this.colourIndex = colourIndex;
            this.setBackgroundColour(PaletteImage.COLOURS[colourIndex]);
            this.setBackgroundHighlightColour(PaletteImage.COLOURS[colourIndex]);
            this.setOutlineColour(PaletteImage.COLOURS[colourIndex]);
            this.setOutlineHighlightColour(0xFFFFFFFF);
        }

        @Override
        public boolean isHovered()
        {
            return super.isHovered() || DoorMatScreen.this.selectedColourIndex == this.colourIndex;
        }

        public int getColourIndex()
        {
            return this.colourIndex;
        }
    }

    private enum Tool
    {
        PENCIL((image, x, y, colourIndex) -> {
            image.set(x, y, colourIndex);
            return true;
        }),
        ERASER((image, x, y, colourIndex) -> {
            image.set(x, y, 0);
            return true;
        }),
        FILL((image, x, y, colourIndex) -> {
            int fillColourIndex = image.get(x, y);
            if(fillColourIndex == colourIndex)
                return false;
            Queue<Pair<Integer, Integer>> queue = new ArrayDeque<>();
            queue.offer(Pair.of(x, y));
            while(!queue.isEmpty()) {
                Pair<Integer, Integer> pair = queue.poll();
                int pixelX = pair.left();
                int pixelY = pair.right();
                image.set(pixelX, pixelY, colourIndex);
                if(pixelY - 1 >= 0 && image.get(pixelX, pixelY - 1) == fillColourIndex) {
                    queue.offer(Pair.of(pixelX, pixelY - 1));
                }
                if(pixelY + 1 < image.getHeight() && image.get(pixelX, pixelY + 1) == fillColourIndex) {
                    queue.offer(Pair.of(pixelX, pixelY + 1));
                }
                if(pixelX - 1 >= 0 && image.get(pixelX - 1, pixelY) == fillColourIndex) {
                    queue.offer(Pair.of(pixelX - 1, pixelY));
                }
                if(pixelX + 1 < image.getWidth() && image.get(pixelX + 1, pixelY) == fillColourIndex) {
                    queue.offer(Pair.of(pixelX + 1, pixelY));
                }
            }
            return false;
        });

        private final ToolFunction function;

        Tool(ToolFunction function)
        {
            this.function = function;
        }

        public ToolFunction getFunction()
        {
            return this.function;
        }
    }

    @FunctionalInterface
    private interface ToolFunction
    {
        boolean apply(PaletteImage image, int x, int y, int colourIndex);
    }
}
