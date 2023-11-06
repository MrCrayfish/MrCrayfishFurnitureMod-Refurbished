package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class MessageTelevisionChannel extends PlayMessage<MessageTelevisionChannel>
{
    private BlockPos pos;
    private ResourceLocation channel;

    public MessageTelevisionChannel() {}

    public MessageTelevisionChannel(BlockPos pos, ResourceLocation channel)
    {
        this.pos = pos;
        this.channel = channel;
    }

    @Override
    public void encode(MessageTelevisionChannel message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeResourceLocation(message.channel);
    }

    @Override
    public MessageTelevisionChannel decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        ResourceLocation channel = buffer.readResourceLocation();
        return new MessageTelevisionChannel(pos, channel);
    }

    @Override
    public void handle(MessageTelevisionChannel message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageTelevisionChannel(message));
        context.setHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public ResourceLocation getChannel()
    {
        return this.channel;
    }
}
