package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSetMailboxName;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

/**
 * Class containing all the handling of network messages on the logical server side
 * <p>
 * Author: MrCrayfish
 */
public class ServerPlayHandler
{
    public static void handleMessageSetMailboxName(MessageSetMailboxName message, @Nullable ServerPlayer player)
    {
        if(player == null)
            return;

        DeliveryService.get(player.server).ifPresent(service -> {
            if(!service.renameMailbox(player, player.level(), message.getPos(), message.getName())) {
                player.sendSystemMessage(Utils.translation("gui", "rename_mailbox_failed"));
            }
        });
    }
}
