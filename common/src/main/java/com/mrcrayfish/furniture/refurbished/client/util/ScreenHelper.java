package com.mrcrayfish.furniture.refurbished.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class ScreenHelper
{
    /**
     * Creates a tooltip that allows control over the lines. By default, tooltips
     * only accept a single component, however it's design can accept multiple components
     * to create a custom multiline tooltip. This utility method unlocks that functionality.
     * It should be noted that long components will still be split according to the default
     * split width of tooltips. There is a side effect, only the first component in the
     * list will be used for narration.
     *
     * @param lines a list of components
     * @return a tooltip with a custom lines
     */
    public static List<FormattedCharSequence> createMultilineTooltip(List<Component> lines)
    {
        if(!lines.isEmpty())
        {
            return lines.stream().map(c -> Minecraft.getInstance().font.split(c, 170)).flatMap(Collection::stream).toList();
        }
        return List.of();
    }

    /**
     * Splits the given string into separate lines, which is determined by the maximum width. Unlike
     * vanilla utilities, this method returns a list of components. This can be used to add additional
     * lines using {@link net.minecraft.world.item.Item#appendHoverText(ItemStack, Level, List, TooltipFlag)}
     *
     * @param text     the text to split
     * @param maxWidth the maximum width of a line
     * @return a list of components
     */
    public static List<MutableComponent> splitText(String text, int maxWidth)
    {
        return Minecraft.getInstance().font.getSplitter().splitLines(text, maxWidth, Style.EMPTY).stream().map(t -> Component.literal(t.getString())).collect(Collectors.toList());
    }

    /**
     * Tests if the given mouse position is within the given bounds as determined by the position
     * of the bounds (x and y) and it's size (width and height).
     *
     * @param mouseX the current mouse x position
     * @param mouseY the current mouse y position
     * @param x      the x position of the bounds
     * @param y      the y position of the bounds
     * @param width  the width of the bounds
     * @param height the height of the bounds
     * @return True if the mouse is within the bounds
     */
    public static boolean isMouseWithinBounds(double mouseX, double mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    /**
     * Fill a rectangle on the screen with one pixel rounded corners.
     *
     * @param poseStack a gui graphics instance
     * @param x        the x position of the rectangle
     * @param y        the y position of the rectangle
     * @param width    the width of the rectangle
     * @param height   the height of the rectangle
     * @param colour   the colour to fill the rectangle
     */
    public static void fillRounded(PoseStack poseStack, int x, int y, int width, int height, int colour)
    {
        Screen.fill(poseStack, x, y + 1, x + 1, y + height - 1, colour);
        Screen.fill(poseStack, x + 1, y, x + width - 1, y + height, colour);
        Screen.fill(poseStack, x + width - 1, y + 1, x + width, y + height - 1, colour);
    }

    /**
     * Draws an item on the screen
     *
     * @param stack the item to draw
     * @param x     the x position to draw
     * @param y     the y position to draw
     */
    public static void drawItem(ItemStack stack, int x, int y)
    {
        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer().renderAndDecorateItem(stack, x, y);
    }

    /**
     *
     * @param poseStack
     * @param text
     * @param x
     * @param y
     * @param colour
     * @param shadow
     */
    public static void drawString(PoseStack poseStack, Component text, int x, int y, int colour, boolean shadow)
    {
        if(shadow)
        {
            Screen.drawString(poseStack, Minecraft.getInstance().font, text, x, y, colour);
        }
        else
        {
            Minecraft.getInstance().font.draw(poseStack, text, x, y, colour);
        }
    }

    public static void drawString(PoseStack poseStack, String text, int x, int y, int colour, boolean shadow)
    {
        if(shadow)
        {
            Screen.drawString(poseStack, Minecraft.getInstance().font, text, x, y, colour);
        }
        else
        {
            Minecraft.getInstance().font.draw(poseStack, text, x, y, colour);
        }
    }
}
