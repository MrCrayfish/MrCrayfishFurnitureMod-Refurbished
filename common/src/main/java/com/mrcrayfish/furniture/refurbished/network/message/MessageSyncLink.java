package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class MessageSyncLink extends PlayMessage<MessageSyncLink>
{
    private BlockPos pos;

    public MessageSyncLink() {}

    public MessageSyncLink(@Nullable BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void encode(MessageSyncLink message, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(message.pos != null);
        if(message.pos != null)
        {
            buffer.writeBlockPos(message.pos);
        }
    }

    @Override
    public MessageSyncLink decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBoolean() ? buffer.readBlockPos() : null;
        return new MessageSyncLink(pos);
    }

    @Override
    public void handle(MessageSyncLink message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageSyncLink(message));
        context.setHandled(true);
    }

    @Nullable
    public BlockPos getPos()
    {
        return this.pos;
    }
}
