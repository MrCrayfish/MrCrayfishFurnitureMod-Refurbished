package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageDeleteLink extends PlayMessage<MessageDeleteLink>
{
    private BlockPos a;
    private BlockPos b;

    public MessageDeleteLink() {}

    public MessageDeleteLink(BlockPos a, BlockPos b)
    {
        this.a = a;
        this.b = b;
    }

    @Override
    public void encode(MessageDeleteLink message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.a);
        buffer.writeBlockPos(message.b);
    }

    @Override
    public MessageDeleteLink decode(FriendlyByteBuf buffer)
    {
        BlockPos a = buffer.readBlockPos();
        BlockPos b = buffer.readBlockPos();
        return new MessageDeleteLink(a, b);
    }

    @Override
    public void handle(MessageDeleteLink message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageDeleteLink(message, context.getPlayer()));
        context.setHandled(true);
    }

    public BlockPos getPosA()
    {
        return this.a;
    }

    public BlockPos getPosB()
    {
        return this.b;
    }
}
