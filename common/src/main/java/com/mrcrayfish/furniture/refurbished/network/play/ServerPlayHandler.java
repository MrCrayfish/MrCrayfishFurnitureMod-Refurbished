package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSendMail;
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

    public static void handleMessageSendMail(MessageSendMail message, ServerPlayer player)
    {
        if(player.containerMenu instanceof PostBoxMenu postBox)
        {
            Container container = postBox.getContainer();
            if(!container.isEmpty())
            {
                DeliveryService.get(player.server).ifPresent(service ->
                {
                    // TODO create envelope item to contain all the items
                    for(int i = 0; i < container.getContainerSize(); i++)
                    {
                        ItemStack stack = container.getItem(i);
                        if(!stack.isEmpty())
                        {
                            container.setItem(i, ItemStack.EMPTY);
                            service.sendMail(message.getMailboxId(), message.getMessage(), player.getGameProfile().getName(), stack);
                        }
                    }
                });
            }
        }
    }
}
