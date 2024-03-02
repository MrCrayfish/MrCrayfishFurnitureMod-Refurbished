package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageTogglePower()
{
    public static void encode(MessageTogglePower message, FriendlyByteBuf buffer) {}

    public static MessageTogglePower decode(FriendlyByteBuf buffer)
    {
        return new MessageTogglePower();
    }

    public static void handle(MessageTogglePower message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageToggleSwitch(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
