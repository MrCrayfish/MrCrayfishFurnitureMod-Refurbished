package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageClearMessage;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSendPackage;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSetMailboxName;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

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

    public static void handleMessageSendPackage(MessageSendPackage message, @Nullable ServerPlayer player, MessageContext context)
    {
        if(player != null && player.containerMenu instanceof PostBoxMenu postBox)
        {
            Container container = postBox.getContainer();
            if(container.isEmpty())
                return;

            DeliveryService.get(player.server).ifPresent(service -> {
                ItemStack stack = PackageItem.create(container, message.getMessage(), player.getGameProfile().getName());
                if(service.sendMail(message.getMailboxId(), stack)) {
                    container.clearContent();
                    Network.getPlay().sendToPlayer(() -> player, new MessageClearMessage());
                }
            });
        }
    }
}
