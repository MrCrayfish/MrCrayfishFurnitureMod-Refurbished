package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageSetMailboxName extends PlayMessage<MessageSetMailboxName>
{
    private BlockPos pos;
    private String name;

    public MessageSetMailboxName() {}

    public MessageSetMailboxName(BlockPos pos, String name)
    {
        this.pos = pos;
        this.name = name;
    }

    @Override
    public void encode(MessageSetMailboxName message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeUtf(message.name);
    }

    @Override
    public MessageSetMailboxName decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        String name = buffer.readUtf(32);
        return new MessageSetMailboxName(pos, name);
    }

    @Override
    public void handle(MessageSetMailboxName message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageSetMailboxName(message, context.getPlayer()));
        context.setHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public String getName()
    {
        return this.name;
    }
}
