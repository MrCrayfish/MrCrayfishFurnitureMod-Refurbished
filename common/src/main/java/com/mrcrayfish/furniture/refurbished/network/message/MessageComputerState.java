package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public record MessageComputerState(BlockPos pos, @Nullable ResourceLocation id)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageComputerState> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.pos);
        buf.writeBoolean(message.id != null);
        if(message.id != null) {
            buf.writeResourceLocation(message.id);
        }
    }, buf -> {
        BlockPos pos = buf.readBlockPos();
        ResourceLocation id = buf.readBoolean() ? buf.readResourceLocation() : null;
        return new MessageComputerState(pos, id);
    });

    public static void handle(MessageComputerState message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageComputerState(message));
        context.setHandled(true);
    }
}
