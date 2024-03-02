package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageNameMailbox(BlockPos pos)
{
    public static void encode(MessageNameMailbox message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
    }

    public static MessageNameMailbox decode(FriendlyByteBuf buffer)
    {
        return new MessageNameMailbox(buffer.readBlockPos());
    }

    public static void handle(MessageNameMailbox message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageNameMailbox(message));
        context.setHandled(true);
    }
}
