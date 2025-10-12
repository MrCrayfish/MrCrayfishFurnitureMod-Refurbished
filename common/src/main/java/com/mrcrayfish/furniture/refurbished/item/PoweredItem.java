package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.FontIcons;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class PoweredItem extends BlockItem
{
    public static final Component POWER_TOOLTIP = Component.empty()
        .append(Components.getIcon(FontIcons.INFO))
        .append(CommonComponents.SPACE)
        .append(Utils.translation("gui", "requires_power",
            Components.GUI_ELECTRICITY_GENERATOR.plainCopy().withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY));

    public PoweredItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> lines, TooltipFlag flag)
    {
        lines.accept(POWER_TOOLTIP);
    }
}
