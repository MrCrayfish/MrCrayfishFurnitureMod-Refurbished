package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageFlushItem extends PlayMessage<MessageFlushItem>
{
    private int entityId;
    private BlockPos pos;

    public MessageFlushItem() {}

    public MessageFlushItem(int entityId, BlockPos pos)
    {
        this.entityId = entityId;
        this.pos = pos;
    }

    @Override
    public void encode(MessageFlushItem message, FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(message.entityId);
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageFlushItem decode(FriendlyByteBuf buffer)
    {
        int entityId = buffer.readVarInt();
        BlockPos pos = buffer.readBlockPos();
        return new MessageFlushItem(entityId, pos);
    }

    @Override
    public void handle(MessageFlushItem message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageFlushItem(message));
        context.setHandled(true);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}
