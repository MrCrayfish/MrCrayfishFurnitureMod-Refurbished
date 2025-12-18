package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.PlayMessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

/**
 * Author: MrCrayfish
 */
public record MessageComputerOpenProgram(Identifier id)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageComputerOpenProgram> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBoolean(message.id != null);
        if(message.id != null) {
            buf.writeIdentifier(message.id);
        }
    }, buf -> {
        Identifier id = buf.readBoolean() ? buf.readIdentifier() : null;
        return new MessageComputerOpenProgram(id);
    });

    public static void handle(MessageComputerOpenProgram message, PlayMessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageComputerOpenProgram(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
