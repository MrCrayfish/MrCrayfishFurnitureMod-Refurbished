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
public record MessageDeleteLink(BlockPos a, BlockPos b)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageDeleteLink> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.a);
        buf.writeBlockPos(message.b);
    }, buf -> {
        BlockPos a = buf.readBlockPos();
        BlockPos b = buf.readBlockPos();
        return new MessageDeleteLink(a, b);
    });

    public static void handle(MessageDeleteLink message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageDeleteLink(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
