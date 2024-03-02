package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageFlushItem(int entityId, BlockPos pos)
{
    public static void encode(MessageFlushItem message, FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(message.entityId);
        buffer.writeBlockPos(message.pos);
    }

    public static MessageFlushItem decode(FriendlyByteBuf buffer)
    {
        int entityId = buffer.readVarInt();
        BlockPos pos = buffer.readBlockPos();
        return new MessageFlushItem(entityId, pos);
    }

    public static void handle(MessageFlushItem message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageFlushItem(message));
        context.setHandled(true);
    }
}
