package com.mrcrayfish.furniture.refurbished.client.util;

import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
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
    public static Tooltip createMultilineTooltip(List<Component> lines)
    {
        if(!lines.isEmpty())
        {
            Tooltip tooltip = Tooltip.create(lines.get(0));
            List<FormattedCharSequence> sequences = lines.stream().map(c -> Minecraft.getInstance().font.split(c, 170)).flatMap(Collection::stream).toList();
            ClientServices.PLATFORM.setTooltipCache(tooltip, sequences);
            return tooltip;
        }
        return Tooltip.create(CommonComponents.EMPTY);
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
}
