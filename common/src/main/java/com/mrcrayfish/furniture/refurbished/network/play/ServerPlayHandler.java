package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.blockentity.INameable;
import com.mrcrayfish.furniture.refurbished.blockentity.IPaintable;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.computer.app.HomeControl;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.DoorMatMenu;
import com.mrcrayfish.furniture.refurbished.inventory.IPowerSwitchMenu;
import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.inventory.RecycleBinMenu;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryResult;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.*;
import net.minecraft.server.level.ServerPlayer;
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

        Level level = player.getLevel();
        if(level.isLoaded(message.getPos()) && level.getBlockEntity(message.getPos()) instanceof INameable nameable)
        {
            nameable.setName(player, message.getName().trim());
        }
    }

    public static void handleMessageSendPackage(MessageSendPackage message, @Nullable ServerPlayer player, MessageContext context)
    {
        if(player != null && player.containerMenu instanceof PostBoxMenu menu)
        {
            Container container = menu.getContainer();
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
                DeliveryResult result = service.sendMail(message.getMailboxId(), stack);
                if(result.success()) {
                    container.clearContent();
                    Network.getPlay().sendToPlayer(() -> player, new MessageClearMessage());
                    Network.getPlay().sendToPlayer(() -> player, new MessageShowDeliveryResult(result));
                } else {
                    result.message().ifPresent(s -> {
                        Network.getPlay().sendToPlayer(() -> player, new MessageShowDeliveryResult(result));
                    });
                }
            });
        }
    }

    public static void handleMessageDeleteLink(MessageDeleteLink message, @Nullable ServerPlayer player)
    {
        if(player != null && player.getMainHandItem().is(ModItems.WRENCH.get()))
        {
            Level level = player.getLevel();
            Connection c = Connection.of(message.getPosA(), message.getPosB());
            if(level.isLoaded(c.getPosA()) && level.isLoaded(c.getPosB()))
            {
                // Check if the player is near the nodes that are being disconnected
                Vec3 a = Vec3.atCenterOf(c.getPosA());
                Vec3 b = Vec3.atCenterOf(c.getPosB());
                double maxDistance = Math.max(25, a.distanceToSqr(b));
                if(player.distanceToSqr(a) > maxDistance && player.distanceToSqr(b) > maxDistance)
                    return;

                IElectricityNode nodeA = c.getNodeA(level);
                IElectricityNode nodeB = c.getNodeB(level);
                if(nodeA != null && nodeB != null && nodeA.isConnectedToNode(nodeB))
                {
                    nodeA.removeNodeConnection(c);
                    nodeB.removeNodeConnection(c);
                }
                else if(nodeA != null && nodeB == null)
                {
                    nodeA.removeNodeConnection(c);
                }
                else if(nodeA == null && nodeB != null)
                {
                    nodeB.removeNodeConnection(c);
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

    public static void handleMessageHomeControlToggle(MessageHomeControl.Toggle message, ServerPlayer player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            if(menu.getComputer().getProgram() instanceof HomeControl program)
            {
                program.toggleDevice(message.getPos());
            }
        }
    }

    public static void handleMessageHomeControlUpdateAll(MessageHomeControl.UpdateAll message, ServerPlayer player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            if(menu.getComputer().getProgram() instanceof HomeControl program)
            {
                program.updateDevices(message.getState());
            }
        }
    }

    public static void handleMessageWorkbenchSelectRecipe(MessageWorkbench.SelectRecipe message, ServerPlayer player)
    {
        if(player != null)
        {
            player.resetLastActionTime();
            if(player.containerMenu instanceof WorkbenchMenu menu && menu.stillValid(player) && !player.isSpectator())
            {
                if(menu.clickMenuButton(player, message.getIndex()))
                {
                    menu.broadcastChanges();
                }
            }
        }
    }

    public static void handleMessageWorkbenchSearchNeighbours(MessageWorkbench.SearchNeighbours message, ServerPlayer player)
    {
        if(player != null)
        {
            player.resetLastActionTime();
            if(player.containerMenu instanceof WorkbenchMenu menu && menu.stillValid(player) && !player.isSpectator())
            {
                menu.toggleSearchNeighbours();
            }
        }
    }

    public static void handleMessageWithdrawExperience(MessageWithdrawExperience message, ServerPlayer player)
    {
        if(player != null)
        {
            player.resetLastActionTime();
            if(player.containerMenu instanceof RecycleBinMenu menu && menu.stillValid(player) && !player.isSpectator())
            {
                if(menu.getContainer() instanceof RecycleBinBlockEntity entity)
                {
                    entity.withdrawExperience(player);
                }
            }
        }
    }
}
