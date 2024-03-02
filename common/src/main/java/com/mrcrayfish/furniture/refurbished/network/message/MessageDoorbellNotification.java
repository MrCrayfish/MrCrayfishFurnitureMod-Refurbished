package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageDoorbellNotification(String name)
{
    public static void encode(MessageDoorbellNotification message, FriendlyByteBuf buffer)
    {
        buffer.writeUtf(message.name);
    }

    public static MessageDoorbellNotification decode(FriendlyByteBuf buffer)
    {
        return new MessageDoorbellNotification(buffer.readUtf(32));
    }

    public static void handle(MessageDoorbellNotification message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageDoorbell(message));
        context.setHandled(true);
    }
}
