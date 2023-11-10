package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class MessageComputerState extends PlayMessage<MessageComputerState>
{
    private BlockPos pos;
    private @Nullable ResourceLocation id;

    public MessageComputerState() {}

    public MessageComputerState(BlockPos pos, @Nullable ResourceLocation id)
    {
        this.pos = pos;
        this.id = id;
    }

    @Override
    public void encode(MessageComputerState message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeBoolean(message.id != null);
        if(message.id != null)
        {
            buffer.writeResourceLocation(message.id);
        }
    }

    @Override
    public MessageComputerState decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        ResourceLocation id = buffer.readBoolean() ? buffer.readResourceLocation() : null;
        return new MessageComputerState(pos, id);
    }

    @Override
    public void handle(MessageComputerState message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageComputerState(message));
        context.setHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    @Nullable
    public ResourceLocation getId()
    {
        return this.id;
    }
}
