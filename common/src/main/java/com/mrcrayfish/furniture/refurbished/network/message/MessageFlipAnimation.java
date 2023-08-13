package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageFlipAnimation extends PlayMessage<MessageFlipAnimation>
{
    private BlockPos pos;
    private int index;

    public MessageFlipAnimation() {}

    public MessageFlipAnimation(BlockPos pos, int index)
    {
        this.pos = pos;
        this.index = index;
    }

    @Override
    public void encode(MessageFlipAnimation message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeInt(message.index);
    }

    @Override
    public MessageFlipAnimation decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        int index = buffer.readInt();
        return new MessageFlipAnimation(pos, index);
    }

    @Override
    public void handle(MessageFlipAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageFlipAnimation(message));
        context.setHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public int getIndex()
    {
        return this.index;
    }
}
