package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public record MessageWaterTapAnimation(BlockPos pos)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageWaterTapAnimation> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.pos);
    }, buf -> {
        return new MessageWaterTapAnimation(buf.readBlockPos());
    });

    public static void handle(MessageWaterTapAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageWaterTapAnimation(message));
        context.setHandled(true);
    }
}
