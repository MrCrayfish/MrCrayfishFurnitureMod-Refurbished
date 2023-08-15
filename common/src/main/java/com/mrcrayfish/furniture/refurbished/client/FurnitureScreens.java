package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.TextInputScreen;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSetMailboxName;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class FurnitureScreens
{
    public static void openMailboxNameScreen(BlockPos pos)
    {
        Minecraft mc = Minecraft.getInstance();
        TextInputScreen screen = new TextInputScreen(Utils.translation("gui", "set_mailbox_name"), Component.empty(), name -> {
            Network.getPlay().sendToServer(new MessageSetMailboxName(pos, name));
            return true;
        });
        screen.setValidator(s -> !s.isBlank() && s.length() <= 32);
        mc.setScreen(screen);
    }
}
