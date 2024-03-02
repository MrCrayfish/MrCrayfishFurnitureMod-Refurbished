package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageWaterTapAnimation(BlockPos pos)
{
    public static void encode(MessageWaterTapAnimation message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
    }

    public static MessageWaterTapAnimation decode(FriendlyByteBuf buffer)
    {
        return new MessageWaterTapAnimation(buffer.readBlockPos());
    }

    public static void handle(MessageWaterTapAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageWaterTapAnimation(message));
        context.setHandled(true);
    }
}
