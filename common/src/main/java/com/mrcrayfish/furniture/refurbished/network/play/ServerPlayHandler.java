package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.INameable;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageClearMessage;
import com.mrcrayfish.furniture.refurbished.network.message.MessageDeleteLink;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSendPackage;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSetName;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Class containing all the handling of network messages on the logical server side
 * <p>
 * Author: MrCrayfish
 */
public class ServerPlayHandler
{
    public static void handleMessageSetName(MessageSetName message, @Nullable ServerPlayer player)
    {
        if(player == null)
            return;

        Level level = player.level();
        if(level.isLoaded(message.getPos()) && level.getBlockEntity(message.getPos()) instanceof INameable nameable)
        {
            nameable.setName(player, message.getName().trim());
        }
    }

    public static void handleMessageSendPackage(MessageSendPackage message, @Nullable ServerPlayer player, MessageContext context)
    {
        if(player != null && player.containerMenu instanceof PostBoxMenu postBox)
        {
            Container container = postBox.getContainer();
            if(container.isEmpty())
                return;

            // Check if all items in the container can be sent
            for(int i = 0; i < container.getContainerSize(); i++)
            {
                ItemStack stack = container.getItem(i);
                if(!stack.isEmpty() && DeliveryService.isBannedItem(stack))
                {
                    return;
                }
            }

            DeliveryService.get(player.server).ifPresent(service -> {
                ItemStack stack = PackageItem.create(container, message.getMessage(), player.getGameProfile().getName());
                if(service.sendMail(message.getMailboxId(), stack)) {
                    container.clearContent();
                    Network.getPlay().sendToPlayer(() -> player, new MessageClearMessage());
                }
            });
        }
    }

    public static void handleMessageDeleteLink(MessageDeleteLink message, @Nullable ServerPlayer player)
    {
        if(player != null && player.getMainHandItem().is(ModItems.WRENCH.get()))
        {
            Level level = player.level();
            Connection c = Connection.of(message.getPosA(), message.getPosB());
            if(level.isLoaded(c.getPosA()) && level.isLoaded(c.getPosB()))
            {
                // Check if the player is near the nodes that are being disconnected
                double maxDistance = Mth.square(Config.SERVER.electricity.maximumLinkDistance.get());
                Vec3 a = c.getPosA().getCenter();
                Vec3 b = c.getPosB().getCenter();
                if(player.distanceToSqr(a) > maxDistance && player.distanceToSqr(b) > maxDistance)
                    return;

                IElectricNode nodeA = c.getNodeA(level);
                IElectricNode nodeB = c.getNodeB(level);
                if(nodeA != null && nodeB != null && nodeA.isConnectedTo(nodeB))
                {
                    nodeA.removeConnection(c);
                    nodeB.removeConnection(c);
                }
            }
        }
    }
}
