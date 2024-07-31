package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public record MessageWithdrawExperience()
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageWithdrawExperience> STREAM_CODEC = StreamCodec.unit(new MessageWithdrawExperience());

    public static void handle(MessageWithdrawExperience message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageWithdrawExperience(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
