package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageTogglePower extends PlayMessage<MessageTogglePower>
{
    @Override
    public void encode(MessageTogglePower message, FriendlyByteBuf buffer) {}

    @Override
    public MessageTogglePower decode(FriendlyByteBuf buffer)
    {
        return new MessageTogglePower();
    }

    @Override
    public void handle(MessageTogglePower message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageToggleSwitch(message, context.getPlayer()));
        context.setHandled(true);
    }
}
