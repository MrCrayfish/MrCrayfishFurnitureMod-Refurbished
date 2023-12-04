package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.INameable;
import com.mrcrayfish.furniture.refurbished.blockentity.IPaintable;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.DoorMatMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IPowerSwitchMenu;
import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageClearMessage;
import com.mrcrayfish.furniture.refurbished.network.message.MessageComputerOpenProgram;
import com.mrcrayfish.furniture.refurbished.network.message.MessageDeleteLink;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSendPackage;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSetName;
import com.mrcrayfish.furniture.refurbished.network.message.MessagePaddleBall;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.network.message.MessageUpdatePainting;
import net.minecraft.server.level.ServerPlayer;
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

                IElectricityNode nodeA = c.getNodeA(level);
                IElectricityNode nodeB = c.getNodeB(level);
                if(nodeA != null && nodeB != null && nodeA.isConnectedTo(nodeB))
                {
                    nodeA.removeConnection(c);
                    nodeB.removeConnection(c);
                }
            }
        }
    }

    public static void handleMessageToggleSwitch(MessageTogglePower message, ServerPlayer player)
    {
        if(player != null && player.containerMenu instanceof IPowerSwitchMenu menu)
        {
            menu.toggle();
        }
    }

    public static void handleMessageTennisGame(MessagePaddleBall.Action message, ServerPlayer player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            if(menu.getComputer().getProgram() instanceof PaddleBall game)
            {
                game.update(message.getMode(), message.getData());
            }
        }
    }

    public static void handleMessageComputerOpenProgram(MessageComputerOpenProgram message, ServerPlayer player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            menu.getComputer().launchProgram(message.getId());
        }
    }

    public static void handleMessageUpdatePainting(MessageUpdatePainting message, ServerPlayer player)
    {
        // TODO could be interface for paintable menu when needed
        if(player != null && player.containerMenu instanceof DoorMatMenu menu)
        {
            IPaintable paintable = menu.getPaintable();
            if(paintable.isEditable())
            {
                paintable.setImage(message.getImage());
                paintable.setEditable(false);
                player.closeContainer();
            }
        }
    }
}
