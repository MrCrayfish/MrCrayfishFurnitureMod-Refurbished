package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public record MessageSyncLink(@Nullable BlockPos pos)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSyncLink> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBoolean(message.pos != null);
        if(message.pos != null) {
            buf.writeBlockPos(message.pos);
        }
    }, buf -> {
        BlockPos pos = buf.readBoolean() ? buf.readBlockPos() : null;
        return new MessageSyncLink(pos);
    });

    public static void handle(MessageSyncLink message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageSyncLink(message));
        context.setHandled(true);
    }
}
