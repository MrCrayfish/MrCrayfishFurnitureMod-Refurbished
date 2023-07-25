package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageFlipGrillItem extends PlayMessage<MessageFlipGrillItem>
{
    private BlockPos pos;
    private int index;

    public MessageFlipGrillItem() {}

    public MessageFlipGrillItem(BlockPos pos, int index)
    {
        this.pos = pos;
        this.index = index;
    }

    @Override
    public void encode(MessageFlipGrillItem message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeInt(message.index);
    }

    @Override
    public MessageFlipGrillItem decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        int index = buffer.readInt();
        return new MessageFlipGrillItem(pos, index);
    }

    @Override
    public void handle(MessageFlipGrillItem message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageFlipGrillItem(message));
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
