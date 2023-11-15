package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class MessageComputerOpenProgram extends PlayMessage<MessageComputerOpenProgram>
{
    private ResourceLocation id;

    public MessageComputerOpenProgram() {}

    public MessageComputerOpenProgram(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public void encode(MessageComputerOpenProgram message, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(message.id != null);
        if(message.id != null)
        {
            buffer.writeResourceLocation(message.id);
        }
    }

    @Override
    public MessageComputerOpenProgram decode(FriendlyByteBuf buffer)
    {
        ResourceLocation id = buffer.readBoolean() ? buffer.readResourceLocation() : null;
        return new MessageComputerOpenProgram(id);
    }

    @Override
    public void handle(MessageComputerOpenProgram message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageComputerOpenProgram(message, context.getPlayer()));
        context.setHandled(true);
    }

    @Nullable
    public ResourceLocation getId()
    {
        return this.id;
    }
}
