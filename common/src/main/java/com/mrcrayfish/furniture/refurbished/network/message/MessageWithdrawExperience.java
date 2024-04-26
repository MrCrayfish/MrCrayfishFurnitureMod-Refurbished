package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageWithdrawExperience()
{
    public static void encode(MessageWithdrawExperience message, FriendlyByteBuf buffer) {}

    public static MessageWithdrawExperience decode(FriendlyByteBuf buffer)
    {
        return new MessageWithdrawExperience();
    }

    public static void handle(MessageWithdrawExperience message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageWithdrawExperience(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
