package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public record MessageDoorbellNotification(String name)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageDoorbellNotification> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeUtf(message.name);
    }, buf -> {
        return new MessageDoorbellNotification(buf.readUtf(32));
    });

    public static void handle(MessageDoorbellNotification message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageDoorbell(message));
        context.setHandled(true);
    }
}
