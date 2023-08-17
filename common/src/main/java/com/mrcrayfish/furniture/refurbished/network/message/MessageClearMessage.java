package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageClearMessage extends PlayMessage<MessageClearMessage>
{
    @Override
    public void encode(MessageClearMessage message, FriendlyByteBuf buffer) {}

    @Override
    public MessageClearMessage decode(FriendlyByteBuf buffer)
    {
        return new MessageClearMessage();
    }

    @Override
    public void handle(MessageClearMessage message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageClearMessage(message));
        context.setHandled(true);
    }
}
