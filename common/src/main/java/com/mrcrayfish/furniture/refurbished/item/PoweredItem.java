package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.framework.api.Environment;
import com.mrcrayfish.framework.api.util.EnvironmentHelper;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.FontIcons;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class PoweredItem extends BlockItem
{
    public static final Component POWER_TOOLTIP = Component.empty()
        .append(Components.getIcon(FontIcons.INFO))
        .append(Component.literal(" "))
        .append(Utils.translation("gui", "requires_power",
            Components.GUI_ELECTRICITY_GENERATOR.plainCopy().withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY));

    public PoweredItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag)
    {
        EnvironmentHelper.runOn(Environment.CLIENT, () -> () -> {
            Minecraft.getInstance().font.getSplitter().splitLines(POWER_TOOLTIP, 150, Style.EMPTY).forEach(text -> {
                // Dumb but works
                MutableComponent line = Component.empty();
                text.visit((style, s) -> {
                    line.append(Component.literal(s).withStyle(style));
                    return Optional.empty();
                }, Style.EMPTY);
                lines.add(line);
            });
        });
        super.appendHoverText(stack, level, lines, flag);
    }
}
