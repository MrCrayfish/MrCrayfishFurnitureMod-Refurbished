package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.IWaterTap;
import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.client.ClientComputer;
import com.mrcrayfish.furniture.refurbished.client.FurnitureScreens;
import com.mrcrayfish.furniture.refurbished.client.LinkHandler;
import com.mrcrayfish.furniture.refurbished.client.ToolAnimationRenderer;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.PostBoxScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.toast.ItemToast;
import com.mrcrayfish.furniture.refurbished.client.particle.ItemFlushParticle;
import com.mrcrayfish.furniture.refurbished.computer.client.graphics.PaddleBallGraphics;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.mail.Mailbox;
import com.mrcrayfish.furniture.refurbished.network.message.*;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Class containing all the handling of network messages on the client side
 * <p>
 * Author: MrCrayfish
 */
public class ClientPlayHandler
{
    public static void handleMessageSyncFluid(MessageSyncFluid message)
    {
        Minecraft mc = Minecraft.getInstance();
        Level level = Objects.requireNonNull(mc.level);
        if(level.getBlockEntity(message.getPos()) instanceof IFluidContainerBlock block)
        {
            FluidContainer container = block.getFluidContainer();
            if(container != null)
            {
                container.handleSync(level, message.getFluid(), message.getAmount());
            }
        }
    }

    public static void handleMessageFlipAnimation(MessageFlipAnimation message)
    {
        Minecraft mc = Minecraft.getInstance();
        Level level = Objects.requireNonNull(mc.level);
        if(level.getBlockEntity(message.getPos()) instanceof GrillBlockEntity grill)
        {
            grill.playFlipAnimation(message.getIndex());
        }
        else if(level.getBlockEntity(message.getPos()) instanceof FryingPanBlockEntity fryingPan)
        {
            fryingPan.playFlipAnimation(message.getIndex());
        }
    }

    public static void handleMessageUpdateMailboxes(MessageUpdateMailboxes message)
    {
        PostBoxScreen.updateMailboxes(message.getMailboxes());
    }

    public static void handleMessageClearMessage(MessageClearMessage message)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.screen instanceof PostBoxScreen postBox)
        {
            postBox.clearMessage();
        }
    }

    public static void handleMessageDoorbell(MessageDoorbellNotification message)
    {
        if(Config.CLIENT.doorbellNotification.get())
        {
            Minecraft mc = Minecraft.getInstance();
            Component title = Utils.translation("gui", "doorbell_rang");
            Component description = Component.literal(message.getName());
            mc.getToasts().addToast(new ItemToast(title, description, new ItemStack(Items.BELL)));
        }
    }

    public static void handleMessageSyncLink(MessageSyncLink message)
    {
        LinkHandler.get().setLinkingNode(message.getPos());
    }

    public static void handleMessageTelevisionChannel(MessageTelevisionChannel message)
    {
        Minecraft mc = Minecraft.getInstance();
        Level level = Objects.requireNonNull(mc.level);
        if(level.getBlockEntity(message.getPos()) instanceof TelevisionBlockEntity television)
        {
            television.setChannelFromId(message.getChannel());
        }
    }

    public static void handleMessageComputerState(MessageComputerState message)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player.containerMenu instanceof ComputerMenu menu)
        {
            ClientComputer computer = ((ClientComputer) menu.getComputer());
            computer.launchProgram(message.getId());
        }
    }

    @Nullable
    private static PaddleBallGraphics getPaddleGame()
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player.containerMenu instanceof ComputerMenu menu)
        {
            ClientComputer computer = ((ClientComputer) menu.getComputer());
            if(computer.getDisplayable() instanceof PaddleBallGraphics game)
            {
                return game;
            }
        }
        return null;
    }

    public static void handleMessageTennisGamePaddlePosition(MessagePaddleBall.PaddlePosition message)
    {
        PaddleBallGraphics game = getPaddleGame();
        if(game != null)
        {
            game.updatePaddles(message.getPlayerPos(), message.getOpponentPos());
        }
    }

    public static void handleMessageTennisGameBallUpdate(MessagePaddleBall.BallUpdate message)
    {
        PaddleBallGraphics game = getPaddleGame();
        if(game != null)
        {
            game.updateBall(message.getBallX(), message.getBallY(), message.getVelocityX(), message.getVelocityY());
        }
    }

    public static void handleMessagePaddleBallEvent(MessagePaddleBall.Event message)
    {
        PaddleBallGraphics game = getPaddleGame();
        if(game != null)
        {
            game.handleEvent(message.getEvent());
        }
    }

    public static void handleMessagePaddleBallOpponentName(MessagePaddleBall.OpponentName message)
    {
        PaddleBallGraphics game = getPaddleGame();
        if(game != null)
        {
            game.handleOpponentName(message.getName());
        }
    }

    public static void handleMessageToolAnimation(MessageToolAnimation message)
    {
        switch(message.getTool())
        {
            case SPATULA -> ToolAnimationRenderer.get().playSpatulaAnimation(message.getPos(), message.getDirection());
            case KNIFE -> ToolAnimationRenderer.get().playKnifeAnimation(message.getPos(), message.getDirection());
        }
    }

    public static void handleMessageFlushItem(MessageFlushItem message)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.level != null && mc.level.getEntity(message.getEntityId()) instanceof ItemEntity entity)
        {
            EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();
            RenderBuffers buffers = mc.renderBuffers();
            Vec3 pos = Vec3.atCenterOf(message.getPos());
            mc.particleEngine.add(new ItemFlushParticle(dispatcher, buffers, mc.level, entity, pos));
            mc.level.removeEntity(message.getEntityId(), Entity.RemovalReason.DISCARDED);
        }
    }

    public static void handleMessageWaterTapAnimation(MessageWaterTapAnimation message)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.level != null && mc.level.getBlockEntity(message.getPos()) instanceof IWaterTap tap)
        {
            tap.playWaterAnimation();
        }
    }

    public static void handleMessageWorkbenchItemCounts(MessageWorkbench.ItemCounts message)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.player.containerMenu instanceof WorkbenchMenu menu)
        {
            menu.updateItemCounts(message.getCounts());
        }
    }

    public static void handleMessageNameMailbox(MessageNameMailbox message)
    {
        FurnitureScreens.openNameableScreen(message.getPos(), Utils.translation("gui", "set_mailbox_name"), Mailbox.MAX_NAME_LENGTH);
    }

    public static void handleMessageShowDeliveryResult(MessageShowDeliveryResult message)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.screen instanceof PostBoxScreen postBox)
        {
            postBox.showResponse(message.getResult());
        }
    }
}
