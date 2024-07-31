package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public record MessageFlushItem(int entityId, BlockPos pos)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageFlushItem> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeVarInt(message.entityId);
        buf.writeBlockPos(message.pos);
    }, buf -> {
        int entityId = buf.readVarInt();
        BlockPos pos = buf.readBlockPos();
        return new MessageFlushItem(entityId, pos);
    });

    public static void handle(MessageFlushItem message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageFlushItem(message));
        context.setHandled(true);
    }
}
