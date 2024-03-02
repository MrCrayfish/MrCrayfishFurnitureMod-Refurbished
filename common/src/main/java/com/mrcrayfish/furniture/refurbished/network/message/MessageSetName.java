package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageSetName(BlockPos pos, String name)
{
    public static void encode(MessageSetName message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeUtf(message.name);
    }

    public static MessageSetName decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        String name = buffer.readUtf();
        return new MessageSetName(pos, name);
    }

    public static void handle(MessageSetName message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageSetName(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
