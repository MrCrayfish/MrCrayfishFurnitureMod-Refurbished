package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.LinkRenderer;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.PostBoxScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.toast.ItemToast;
import com.mrcrayfish.furniture.refurbished.network.message.MessageDoorbellNotification;
import com.mrcrayfish.furniture.refurbished.network.message.MessageFlipAnimation;
import com.mrcrayfish.furniture.refurbished.network.message.MessageClearMessage;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncLink;
import com.mrcrayfish.furniture.refurbished.network.message.MessageUpdateMailboxes;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

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
        if(level.getBlockEntity(message.getPos()) instanceof KitchenSinkBlockEntity sink) // TODO abstract
        {
            sink.onSyncFluid(message.getFluid(), message.getAmount());
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
        LinkRenderer.get().setLastNodePos(message.getPos());
    }
}
