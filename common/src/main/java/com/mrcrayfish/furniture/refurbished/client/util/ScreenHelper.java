package com.mrcrayfish.furniture.refurbished.client.util;

import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.Collection;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ScreenHelper
{
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
}
