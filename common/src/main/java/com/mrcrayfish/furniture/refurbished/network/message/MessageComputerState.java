package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public record MessageComputerState(BlockPos pos, @Nullable ResourceLocation id)
{
    public static void encode(MessageComputerState message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeBoolean(message.id != null);
        if(message.id != null)
        {
            buffer.writeResourceLocation(message.id);
        }
    }

    public static MessageComputerState decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        ResourceLocation id = buffer.readBoolean() ? buffer.readResourceLocation() : null;
        return new MessageComputerState(pos, id);
    }

    public static void handle(MessageComputerState message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageComputerState(message));
        context.setHandled(true);
    }
}
