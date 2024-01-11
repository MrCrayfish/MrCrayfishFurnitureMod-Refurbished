package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageNameMailbox extends PlayMessage<MessageNameMailbox>
{
    private BlockPos pos;

    public MessageNameMailbox() {}

    public MessageNameMailbox(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void encode(MessageNameMailbox message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageNameMailbox decode(FriendlyByteBuf buffer)
    {
        return new MessageNameMailbox(buffer.readBlockPos());
    }

    @Override
    public void handle(MessageNameMailbox message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageNameMailbox(message));
        context.setHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}
