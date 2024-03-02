package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public record MessageComputerOpenProgram(ResourceLocation id)
{
    public static void encode(MessageComputerOpenProgram message, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(message.id != null);
        if(message.id != null)
        {
            buffer.writeResourceLocation(message.id);
        }
    }

    public static MessageComputerOpenProgram decode(FriendlyByteBuf buffer)
    {
        ResourceLocation id = buffer.readBoolean() ? buffer.readResourceLocation() : null;
        return new MessageComputerOpenProgram(id);
    }

    public static void handle(MessageComputerOpenProgram message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageComputerOpenProgram(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
