package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public record MessageComputerState(BlockPos pos, @Nullable Identifier id)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageComputerState> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.pos);
        buf.writeBoolean(message.id != null);
        if(message.id != null) {
            buf.writeIdentifier(message.id);
        }
    }, buf -> {
        BlockPos pos = buf.readBlockPos();
        Identifier id = buf.readBoolean() ? buf.readIdentifier() : null;
        return new MessageComputerState(pos, id);
    });

    public static void handle(MessageComputerState message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageComputerState(message));
        context.setHandled(true);
    }
}
