package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageFlipAnimation(BlockPos pos, int index)
{
    public static void encode(MessageFlipAnimation message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeInt(message.index);
    }

    public static MessageFlipAnimation decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        int index = buffer.readInt();
        return new MessageFlipAnimation(pos, index);
    }

    public static void handle(MessageFlipAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageFlipAnimation(message));
        context.setHandled(true);
    }
}
