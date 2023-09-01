package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageSetName extends PlayMessage<MessageSetName>
{
    private BlockPos pos;
    private String name;

    public MessageSetName() {}

    public MessageSetName(BlockPos pos, String name)
    {
        this.pos = pos;
        this.name = name;
    }

    @Override
    public void encode(MessageSetName message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeUtf(message.name);
    }

    @Override
    public MessageSetName decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        String name = buffer.readUtf();
        return new MessageSetName(pos, name);
    }

    @Override
    public void handle(MessageSetName message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageSetName(message, context.getPlayer()));
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
