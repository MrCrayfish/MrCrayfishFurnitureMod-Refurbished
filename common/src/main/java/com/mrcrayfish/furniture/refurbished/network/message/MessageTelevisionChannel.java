package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public record MessageTelevisionChannel(BlockPos pos, ResourceLocation channel)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageTelevisionChannel> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.pos);
        buf.writeResourceLocation(message.channel);
    }, buf -> {
        BlockPos pos = buf.readBlockPos();
        ResourceLocation channel = buf.readResourceLocation();
        return new MessageTelevisionChannel(pos, channel);
    });

    public static void handle(MessageTelevisionChannel message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageTelevisionChannel(message));
        context.setHandled(true);
    }
}
