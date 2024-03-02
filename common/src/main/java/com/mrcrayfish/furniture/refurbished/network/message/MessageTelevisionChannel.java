package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public record MessageTelevisionChannel(BlockPos pos, ResourceLocation channel)
{
    public static void encode(MessageTelevisionChannel message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeResourceLocation(message.channel);
    }

    public static MessageTelevisionChannel decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        ResourceLocation channel = buffer.readResourceLocation();
        return new MessageTelevisionChannel(pos, channel);
    }

    public static void handle(MessageTelevisionChannel message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageTelevisionChannel(message));
        context.setHandled(true);
    }
}
