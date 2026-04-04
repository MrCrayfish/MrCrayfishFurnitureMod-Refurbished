package com.mrcrayfish.furniture.refurbished.computer.client.widget;

import com.mrcrayfish.furniture.refurbished.client.gui.ICustomSelectionList;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MrCrayfish
 */
public class ComputerSelectionList<E extends ObjectSelectionList.Entry<E>> extends ObjectSelectionList<E> implements ICustomSelectionList<E>
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

    // TODO debug entire list

    public ComputerSelectionList(int width, int height, int x, int y, int itemHeight)
    {
        super(Minecraft.getInstance(), width, height, y, itemHeight);
        this.setPosition(x, y);
    }

    public void setPosition(int x, int y)
    {
        super.setPosition(x, y);
        this.setSize(this.width, this.height);
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
    public int addEntry(E e)
    {
        return super.addEntry(e);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY)
    {
        return mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight() && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth();
    }

    @Override
    public int getRowWidth()
    {
        return this.getRowRight() - this.getRowLeft();
    }

    @Override
    public int getRowLeft()
    {
        return this.getX() + OUTLINE_SIZE + this.contentPadding;
    }

    @Override
    public int getRowRight()
    {
        if(this.maxScrollAmount() > 0)
        {
            return this.getX() + this.getWidth() - this.contentPadding - OUTLINE_SIZE - this.contentPadding - this.scrollBarWidth - this.contentPadding - OUTLINE_SIZE;
        }
        return this.getX() + this.getWidth() - this.contentPadding - OUTLINE_SIZE;
    }

    @Override
    protected int scrollBarX()
    {
        return this.getX() + this.getWidth() - this.scrollBarWidth - this.contentPadding - OUTLINE_SIZE;
    }

    private int getScrollbarHeight()
    {
        int scrollAreaHeight = this.getScrollAreaHeight();
        int scrollBarHeight = (int) (Mth.square(scrollAreaHeight) / (float) this.contentHeight());
        return Mth.clamp(scrollBarHeight, 32, scrollAreaHeight);
    }

    public int getScrollAreaHeight()
    {
        return this.height - OUTLINE_SIZE * 2 - this.contentPadding * 2;
    }

    public int getScrollAreaTop()
    {
        return this.getY() + OUTLINE_SIZE + this.contentPadding;
    }

    @Override
    public int maxScrollAmount()
    {
        return Math.max(0, this.scrollerHeight() - this.height + this.contentPadding * 2 + OUTLINE_SIZE * 2);
    }

    @Override
    protected int scrollerHeight()
    {
        // TODO 1.21.10 will need updating since rows can be different heights,
        return this.getItemCount() * (this.defaultEntryHeight + this.itemSpacing) - this.itemSpacing;
    }

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        // Draw outlines and background
        extractor.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), this.outlineColour);
        extractor.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() - 1, this.backgroundColour);

        // Draw items
        extractor.enableScissor(this.getRowLeft() - 1, this.getY() + 1, this.getRowRight() + 1, this.getY() + this.getHeight() - 1);
        this.extractListItems(extractor, mouseX, mouseY, partialTick);
        extractor.disableScissor();

        // Only draw scroll bar if enough items
        int maxScroll = this.maxScrollAmount();
        if(maxScroll > 0)
        {
            // Draw divider between items and scroll bar
            extractor.fill(this.scrollBarX() - this.contentPadding - 1, this.getY() + 1, this.scrollBarX() - this.contentPadding, this.getY() + this.getHeight() - 1, this.outlineColour);

            // Draw scroll bar
            int scrollBarStart = this.scrollBarX();
            int scrollBarEnd = scrollBarStart + this.scrollBarWidth;
            int scrollBarHeight = this.getScrollbarHeight();
            int scrollBarTop = (int) (this.getScrollAreaTop() + (this.getScrollAreaHeight() - this.getScrollbarHeight()) * (this.scrollAmount() / maxScroll));
            int scrollBarColour = ScreenHelper.isMouseWithinBounds(mouseX, mouseY, scrollBarStart, scrollBarTop, this.scrollBarWidth, scrollBarHeight) ? this.scrollBarHighlightColour : this.scrollBarColour;
            extractor.fill(scrollBarStart, scrollBarTop, scrollBarEnd, scrollBarTop + scrollBarHeight, scrollBarColour);
        }
    }

    @Override
    protected void extractListItems(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        int rowHeight = this.defaultEntryHeight;
        int rowCount = this.getItemCount();

        /* TODO 1.21.10 needs to be updated. see super. items heights are dynamic, rewrite and optimise.
           Mojangs impl seems to iterate all items, while it can break earlier */

        // For efficiency, find the index to start drawing based on scroll amount
        int startIndex = Math.max(0, (int) ((this.scrollAmount() - this.contentPadding) / (rowHeight + this.itemSpacing)));
        for(int i = startIndex; i < rowCount; i++)
        {
            E entry = this.children().get(i);
            if(entry.getY() <= this.getY() + this.getHeight())
            {
                this.extractItem(extractor, mouseX, mouseY, partialTick, this.children().get(i));
                continue;
            }
            // Break if the item is below the content area. Also stops drawing subsequent items.
            break;
        }
    }

    @Override
    protected void extractSelection(GuiGraphicsExtractor extractor, E entry, int outlineColour)
    {
        extractor.fill(entry.getX() - 1, entry.getY() - 1, entry.getX() + entry.getWidth() + 1, entry.getY() + entry.getHeight() + 1, outlineColour);
    }

    @Override
    public boolean updateScrolling(MouseButtonEvent event)
    {
        this.scrolling = event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT
                && ScreenHelper.isMouseWithinBounds(event.x(), event.y(), this.scrollBarX(), this.getScrollAreaTop(), 6, this.getScrollAreaHeight());
        return super.updateScrolling(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY)
    {
        if(event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            if(this.getFocused() != null && this.isDragging() && this.getFocused().mouseDragged(event, dragX, dragY))
            {
                return true;
            }
            if(this.scrolling)
            {
                double unitsPerScroll = (double) this.maxScrollAmount() / (this.getScrollAreaHeight() - this.getScrollbarHeight());
                this.setScrollAmount(this.scrollAmount() + dragY * unitsPerScroll);
                return true;
            }
        }
        return false;
    }

    @Override
    public E getEntry(double mouseX, double mouseY)
    {
        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight()))
        {
            int rowHeight = this.defaultEntryHeight; // TODO might need to be changed due to dynamic item heights
            int rowCount = this.getItemCount();
            int startIndex = Math.max(0, (int) ((this.scrollAmount() - this.contentPadding) / (rowHeight + this.itemSpacing)));
            for(int i = startIndex; i < rowCount; i++)
            {
                E entry = this.children().get(i);
                if(entry.getY() <= this.getY() + this.getHeight())
                {
                    if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, entry.getX(), entry.getY(), entry.getWidth(), entry.getHeight()))
                    {
                        return this.children().get(i);
                    }
                    continue;
                }
                break;
            }
        }
        return null;
    }

    @Override
    public int getStartEntryY()
    {
        return this.getY() + OUTLINE_SIZE + this.contentPadding;
    }

    @Override
    public void arrangeEntries()
    {
        int nextY = this.getStartEntryY() - (int) this.scrollAmount();
        for(E e : this.children())
        {
            e.setY(nextY);
            e.setX(this.getRowLeft());
            e.setWidth(this.getRowWidth());
            nextY += e.getHeight() + this.itemSpacing;
        }
    }
}
