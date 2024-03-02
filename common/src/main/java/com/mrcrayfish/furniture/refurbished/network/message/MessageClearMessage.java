package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageClearMessage()
{
    public static void encode(MessageClearMessage message, FriendlyByteBuf buffer) {}

    public static MessageClearMessage decode(FriendlyByteBuf buffer)
    {
        return new MessageClearMessage();
    }

    public static void handle(MessageClearMessage message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageClearMessage(message));
        context.setHandled(true);
    }
}
