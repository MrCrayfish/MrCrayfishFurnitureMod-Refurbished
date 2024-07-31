package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public record MessageComputerOpenProgram(ResourceLocation id)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageComputerOpenProgram> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBoolean(message.id != null);
        if(message.id != null) {
            buf.writeResourceLocation(message.id);
        }
    }, buf -> {
        ResourceLocation id = buf.readBoolean() ? buf.readResourceLocation() : null;
        return new MessageComputerOpenProgram(id);
    });

    public static void handle(MessageComputerOpenProgram message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageComputerOpenProgram(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
