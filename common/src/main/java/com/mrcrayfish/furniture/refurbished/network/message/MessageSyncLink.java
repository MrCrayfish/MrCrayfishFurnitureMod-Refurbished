package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public record MessageSyncLink(@Nullable BlockPos pos)
{
    public static void encode(MessageSyncLink message, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(message.pos != null);
        if(message.pos != null)
        {
            buffer.writeBlockPos(message.pos);
        }
    }

    public static MessageSyncLink decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBoolean() ? buffer.readBlockPos() : null;
        return new MessageSyncLink(pos);
    }

    public static void handle(MessageSyncLink message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageSyncLink(message));
        context.setHandled(true);
    }
}
