package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public record MessageTogglePower()
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageTogglePower> STREAM_CODEC = StreamCodec.unit(new MessageTogglePower());

    public static void handle(MessageTogglePower message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageToggleSwitch(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
