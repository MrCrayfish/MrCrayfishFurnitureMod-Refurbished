package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageWithdrawExperience extends PlayMessage<MessageWithdrawExperience>
{
    @Override
    public void encode(MessageWithdrawExperience message, FriendlyByteBuf buffer) {}

    @Override
    public MessageWithdrawExperience decode(FriendlyByteBuf buffer)
    {
        return new MessageWithdrawExperience();
    }

    @Override
    public void handle(MessageWithdrawExperience message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageWithdrawExperience(message, context.getPlayer()));
        context.setHandled(true);
    }
}
