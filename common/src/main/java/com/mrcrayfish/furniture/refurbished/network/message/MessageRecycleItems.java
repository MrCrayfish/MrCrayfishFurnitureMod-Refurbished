package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageRecycleItems extends PlayMessage<MessageRecycleItems>
{
    @Override
    public void encode(MessageRecycleItems message, FriendlyByteBuf buffer) {}

    @Override
    public MessageRecycleItems decode(FriendlyByteBuf buffer)
    {
        return new MessageRecycleItems();
    }

    @Override
    public void handle(MessageRecycleItems message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageRecycleItems(message, context.getPlayer()));
        context.setHandled(true);
    }
}
