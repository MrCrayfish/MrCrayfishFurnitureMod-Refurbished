package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageWaterTapAnimation extends PlayMessage<MessageWaterTapAnimation>
{
    private BlockPos pos;

    public MessageWaterTapAnimation() {}

    public MessageWaterTapAnimation(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void encode(MessageWaterTapAnimation message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageWaterTapAnimation decode(FriendlyByteBuf buffer)
    {
        return new MessageWaterTapAnimation(buffer.readBlockPos());
    }

    @Override
    public void handle(MessageWaterTapAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageWaterTapAnimation(message));
        context.setHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}
