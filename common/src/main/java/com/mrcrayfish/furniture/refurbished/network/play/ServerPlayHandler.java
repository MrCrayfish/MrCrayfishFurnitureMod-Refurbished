package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.Config;
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
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
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
    public static void handleMessageSetName(MessageSetName message, @Nullable Player player)
    {
        if(player == null)
            return;

        Level level = player.level();
        if(level.isLoaded(message.pos()) && level.getBlockEntity(message.pos()) instanceof INameable nameable && player instanceof ServerPlayer serverPlayer)
        {
            nameable.setName(serverPlayer, message.name().trim());
        }
    }

    public static void handleMessageSendPackage(MessageSendPackage message, @Nullable Player player, MessageContext context)
    {
        if(player instanceof ServerPlayer serverPlayer && serverPlayer.containerMenu instanceof PostBoxMenu menu)
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

            DeliveryService.get(serverPlayer.server).ifPresent(service -> {
                ItemStack stack = PackageItem.create(container, message.message(), player.getGameProfile().getName());
                if(service.sendMail(message.mailboxId(), stack)) {
                    container.clearContent();
                    Network.getPlay().sendToPlayer(() -> serverPlayer, new MessageClearMessage());
                }
            });
        }
    }

    public static void handleMessageDeleteLink(MessageDeleteLink message, @Nullable Player player)
    {
        if(player != null && player.getMainHandItem().is(ModItems.WRENCH.get()))
        {
            Level level = player.level();
            Connection c = Connection.of(message.a(), message.b());
            if(level.isLoaded(c.getPosA()) && level.isLoaded(c.getPosB()))
            {
                // Check if the player is near the nodes that are being disconnected
                Vec3 a = c.getPosA().getCenter();
                Vec3 b = c.getPosB().getCenter();
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

    public static void handleMessageToggleSwitch(MessageTogglePower message, @Nullable Player player)
    {
        if(player != null && player.containerMenu instanceof IPowerSwitchMenu menu)
        {
            menu.toggle();
        }
    }

    public static void handleMessageTennisGame(MessagePaddleBall.Action message, @Nullable Player player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            if(menu.getComputer().getProgram() instanceof PaddleBall game)
            {
                game.update(message.action(), message.data());
            }
        }
    }

    public static void handleMessageComputerOpenProgram(MessageComputerOpenProgram message, @Nullable Player player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            menu.getComputer().launchProgram(message.id());
        }
    }

    public static void handleMessageUpdatePainting(MessageUpdatePainting message, @Nullable Player player)
    {
        // TODO could be interface for paintable menu when needed
        if(player != null && player.containerMenu instanceof DoorMatMenu menu)
        {
            IPaintable paintable = menu.getPaintable();
            if(paintable.isEditable())
            {
                paintable.setImage(message.image());
                paintable.setEditable(false);
                if(player instanceof ServerPlayer serverPlayer)
                {
                    serverPlayer.closeContainer();
                }

            }
        }
    }

    public static void handleMessageHomeControlToggle(MessageHomeControl.Toggle message, @Nullable Player player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            if(menu.getComputer().getProgram() instanceof HomeControl program)
            {
                program.toggleDevice(message.pos());
            }
        }
    }

    public static void handleMessageHomeControlUpdateAll(MessageHomeControl.UpdateAll message, @Nullable Player player)
    {
        if(player != null && player.containerMenu instanceof ComputerMenu menu)
        {
            if(menu.getComputer().getProgram() instanceof HomeControl program)
            {
                program.updateDevices(message.state());
            }
        }
    }

    public static void handleMessageWorkbenchSelectRecipe(MessageWorkbench.SelectRecipe message, @Nullable Player player)
    {
        if(player instanceof ServerPlayer serverPlayer)
        {
            serverPlayer.resetLastActionTime();
            if(player.containerMenu instanceof WorkbenchMenu menu && menu.stillValid(player) && !player.isSpectator())
            {
                if(menu.clickMenuButton(player, message.index()))
                {
                    menu.broadcastChanges();
                }
            }
        }
    }

    public static void handleMessageWorkbenchSearchNeighbours(MessageWorkbench.SearchNeighbours message, @Nullable Player player)
    {
        if(player instanceof ServerPlayer serverPlayer)
        {
            serverPlayer.resetLastActionTime();
            if(serverPlayer.containerMenu instanceof WorkbenchMenu menu && menu.stillValid(serverPlayer) && !serverPlayer.isSpectator())
            {
                menu.toggleSearchNeighbours();
            }
        }
    }

    public static void handleMessageWithdrawExperience(MessageWithdrawExperience message, Player player)
    {
        if(player instanceof ServerPlayer serverPlayer)
        {
            serverPlayer.resetLastActionTime();
            if(player.containerMenu instanceof RecycleBinMenu menu && menu.stillValid(player) && !player.isSpectator())
            {
                if(menu.getContainer() instanceof RecycleBinBlockEntity entity)
                {
                    entity.withdrawExperience(serverPlayer);
                }
            }
        }
    }
}
