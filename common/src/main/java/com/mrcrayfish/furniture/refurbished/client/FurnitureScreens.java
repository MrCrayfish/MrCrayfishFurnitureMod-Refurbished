package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.TextInputScreen;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSetName;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class FurnitureScreens
{
    public static void openNameableScreen(BlockPos pos, Component title, int maxLength)
    {
        Minecraft mc = Minecraft.getInstance();
        TextInputScreen screen = new TextInputScreen(title, Component.empty(), name -> {
            Network.getPlay().sendToServer(new MessageSetName(pos, name));
            return true;
        });
        screen.setValidator(s -> !s.isBlank() && s.length() <= maxLength);
        mc.setScreen(screen);
    }
}
