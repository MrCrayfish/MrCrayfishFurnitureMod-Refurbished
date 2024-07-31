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
public record MessageSetName(BlockPos pos, String name)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSetName> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.pos);
        buf.writeUtf(message.name);
    }, buf -> {
        BlockPos pos = buf.readBlockPos();
        String name = buf.readUtf();
        return new MessageSetName(pos, name);
    });

    public static void handle(MessageSetName message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageSetName(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
