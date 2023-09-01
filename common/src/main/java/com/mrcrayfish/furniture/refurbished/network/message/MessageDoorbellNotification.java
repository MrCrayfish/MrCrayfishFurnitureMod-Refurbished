package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageDoorbellNotification extends PlayMessage<MessageDoorbellNotification>
{
    private String name;

    public MessageDoorbellNotification() {}

    public MessageDoorbellNotification(String name)
    {
        this.name = name;
    }

    @Override
    public void encode(MessageDoorbellNotification message, FriendlyByteBuf buffer)
    {
        buffer.writeUtf(message.name);
    }

    @Override
    public MessageDoorbellNotification decode(FriendlyByteBuf buffer)
    {
        return new MessageDoorbellNotification(buffer.readUtf(32));
    }

    @Override
    public void handle(MessageDoorbellNotification message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageDoorbell(message));
        context.setHandled(true);
    }

    public String getName()
    {
        return this.name;
    }
}
