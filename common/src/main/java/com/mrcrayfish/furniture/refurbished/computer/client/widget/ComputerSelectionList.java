package com.mrcrayfish.furniture.refurbished.computer.client.widget;

import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MrCrayfish
 */
public class ComputerSelectionList<E extends ObjectSelectionList.Entry<E>> extends ObjectSelectionList<E>
{
    private static final int OUTLINE_SIZE = 1;

    protected int contentPadding = 2;
    protected int scrollBarWidth = 6;
    protected int outlineColour = 0xFF47403E;
    protected int backgroundColour = 0xFF262626;
    protected int scrollBarColour = 0xFF47403E;
    protected int scrollBarHighlightColour = 0xFF332E2D;
    protected int itemSpacing = 2;
    protected boolean scrolling;

    public ComputerSelectionList(int width, int height, int $$3, int $$4, int itemHeight)
    {
        super(Minecraft.getInstance(), width, height, $$3, $$4, itemHeight);
    }

    public void setPosition(int x, int y)
    {
        this.x0 = x;
        this.x1 = x + this.width;
        this.y0 = y;
        this.y1 = y + this.height;
    }

    public void setContentPadding(int contentPadding)
    {
        this.contentPadding = contentPadding;
    }

    public void setScrollBarWidth(int scrollBarWidth)
    {
        this.scrollBarWidth = scrollBarWidth;
    }

    public void setOutlineColour(int outlineColour)
    {
        this.outlineColour = outlineColour;
    }

    public void setBackgroundColour(int backgroundColour)
    {
        this.backgroundColour = backgroundColour;
    }

    public void setScrollBarColour(int scrollBarColour)
    {
        this.scrollBarColour = scrollBarColour;
    }

    public void setScrollBarHighlightColour(int scrollBarHighlightColour)
    {
        this.scrollBarHighlightColour = scrollBarHighlightColour;
    }

    public void setItemSpacing(int itemSpacing)
    {
        this.itemSpacing = itemSpacing;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY)
    {
        return mouseY >= this.y0 && mouseY <= this.y1 && mouseX >= this.x0 && mouseX <= this.x1;
    }

    @Override
    public int getRowWidth()
    {
        return this.getRowRight() - this.getRowLeft();
    }

    @Override
    public int getRowLeft()
    {
        return this.x0 + OUTLINE_SIZE + this.contentPadding;
    }

    @Override
    public int getRowRight()
    {
        if(this.getMaxScroll() > 0)
        {
            return this.x1 - this.contentPadding - OUTLINE_SIZE - this.contentPadding - this.scrollBarWidth - this.contentPadding - OUTLINE_SIZE;
        }
        return this.x1 - this.contentPadding - OUTLINE_SIZE;
    }

    @Override
    protected int getRowTop(int index)
    {
        return this.y0 + OUTLINE_SIZE + this.contentPadding - (int) this.getScrollAmount() + index * this.itemHeight + index * this.itemSpacing;
    }

    @Override
    protected int getScrollbarPosition()
    {
        return this.x1 - this.scrollBarWidth - this.contentPadding - OUTLINE_SIZE;
    }

    private int getScrollbarHeight()
    {
        int scrollAreaHeight = this.getScrollAreaHeight();
        int scrollBarHeight = (int) (Mth.square(scrollAreaHeight) / (float) this.getMaxPosition());
        return Mth.clamp(scrollBarHeight, 32, scrollAreaHeight);
    }

    @Override
    public int getScrollBottom()
    {
        return (int) this.getScrollAmount() - this.height;
    }

    public int getScrollAreaHeight()
    {
        return this.height - OUTLINE_SIZE * 2 - this.contentPadding * 2;
    }

    public int getScrollAreaTop()
    {
        return this.y0 + OUTLINE_SIZE + this.contentPadding;
    }

    @Override
    public int getMaxScroll()
    {
        return Math.max(0, this.getMaxPosition() - this.height + this.contentPadding * 2 + OUTLINE_SIZE * 2);
    }

    @Override
    protected int getMaxPosition()
    {
        return this.getItemCount() * (this.itemHeight + this.itemSpacing) - this.itemSpacing;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        // Draw outlines and background
        graphics.fill(this.x0, this.y0, this.x1, this.y1, this.outlineColour);
        graphics.fill(this.x0 + 1, this.y0 + 1, this.x1 - 1, this.y1 - 1, this.backgroundColour);

        // Draw items
        graphics.enableScissor(this.getRowLeft() - 1, this.y0 + 1, this.getRowRight() + 1, this.y1 - 1);
        this.renderList(graphics, mouseX, mouseY, partialTick);
        graphics.disableScissor();

        // Only draw scroll bar if enough items
        int maxScroll = this.getMaxScroll();
        if(maxScroll > 0)
        {
            // Draw divider between items and scroll bar
            graphics.fill(this.getScrollbarPosition() - this.contentPadding - 1, this.y0 + 1, this.getScrollbarPosition() - this.contentPadding, this.y1 - 1, this.outlineColour);

            // Draw scroll bar
            int scrollBarStart = this.getScrollbarPosition();
            int scrollBarEnd = scrollBarStart + this.scrollBarWidth;
            int scrollBarHeight = this.getScrollbarHeight();
            int scrollBarTop = (int) (this.getScrollAreaTop() + (this.getScrollAreaHeight() - this.getScrollbarHeight()) * (this.getScrollAmount() / maxScroll));
            int scrollBarColour = ScreenHelper.isMouseWithinBounds(mouseX, mouseY, scrollBarStart, scrollBarTop, this.scrollBarWidth, scrollBarHeight) ? this.scrollBarHighlightColour : this.scrollBarColour;
            graphics.fill(scrollBarStart, scrollBarTop, scrollBarEnd, scrollBarTop + scrollBarHeight, scrollBarColour);
        }
    }

    @Override
    protected void renderList(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        int rowLeft = this.getRowLeft();
        int rowWidth = this.getRowWidth();
        int rowHeight = this.itemHeight;
        int rowCount = this.getItemCount();

        // For efficiency, find the index to start drawing based on scroll amount
        int startIndex = Math.max(0, (int) ((this.getScrollAmount() - this.contentPadding) / (rowHeight + this.itemSpacing)));
        for(int i = startIndex; i < rowCount; i++)
        {
            int rowTop = this.getRowTop(i);
            if(rowTop <= this.y1)
            {
                this.renderItem(graphics, mouseX, mouseY, partialTick, i, rowLeft, rowTop, rowWidth, rowHeight);
                continue;
            }
            // Break if the item is below the content area. Also stops drawing subsequent items.
            break;
        }

    }

    @Override
    protected void renderSelection(GuiGraphics graphics, int top, int rowWidth, int itemHeight, int outlineColour, int innerColour)
    {
        int start = this.getRowLeft();
        int end = this.getRowRight();
        graphics.fill(start - 1, top - 1, end + 1, top + itemHeight + 1, outlineColour);
        //graphics.fill(start + 1, top - 1, end - 1, top + itemHeight + 1, innerColour);
    }

    @Override
    protected void updateScrollingState(double mouseX, double mouseY, int button)
    {
        this.scrolling = button == GLFW.GLFW_MOUSE_BUTTON_LEFT
                && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.getScrollbarPosition(), this.getScrollAreaTop(), 6, this.getScrollAreaHeight());
    }

    @Override
    public boolean mouseDragged(double $$0, double $$1, int button, double deltaX, double deltaY)
    {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            if(this.getFocused() != null && this.isDragging() && this.getFocused().mouseDragged($$0, $$1, button, deltaX, deltaY))
            {
                return true;
            }
            if(this.scrolling)
            {
                double unitsPerScroll = (double) this.getMaxScroll() / (this.getScrollAreaHeight() - this.getScrollbarHeight());
                this.setScrollAmount(this.getScrollAmount() + deltaY * unitsPerScroll);
                return true;
            }
        }
        return false;
    }
}
