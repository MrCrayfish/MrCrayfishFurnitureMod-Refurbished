package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class Components
{
    public static final Component GUI_ELECTRICITY_GENERATOR = Utils.translation("gui", "electricity_generator");
    public static final Component GUI_TOGGLE_POWER = Utils.translation("gui", "toggle_power");
    public static final Component GUI_NO_POWER = Utils.translation("gui", "no_power");
    public static final Component GUI_CONNECT_TO_POWER = Utils.translation("gui", "connect_to_power",
            GUI_ELECTRICITY_GENERATOR.plainCopy().withStyle(ChatFormatting.YELLOW),
            Utils.translation("item", "wrench").plainCopy().withStyle(ChatFormatting.YELLOW)
    );
}
