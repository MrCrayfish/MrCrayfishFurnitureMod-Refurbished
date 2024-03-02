package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageDeleteLink(BlockPos a, BlockPos b)
{
    public static void encode(MessageDeleteLink message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.a);
        buffer.writeBlockPos(message.b);
    }

    public static MessageDeleteLink decode(FriendlyByteBuf buffer)
    {
        BlockPos a = buffer.readBlockPos();
        BlockPos b = buffer.readBlockPos();
        return new MessageDeleteLink(a, b);
    }

    public static void handle(MessageDeleteLink message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageDeleteLink(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
