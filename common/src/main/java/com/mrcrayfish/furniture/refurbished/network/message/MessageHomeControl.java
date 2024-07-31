package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public class MessageHomeControl
{
    public record Toggle(BlockPos pos)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, Toggle> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeBlockPos(message.pos);
        }, buf -> {
            return new Toggle(buf.readBlockPos());
        });

        public static void handle(Toggle message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageHomeControlToggle(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }

    public record UpdateAll(boolean state)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, UpdateAll> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeBoolean(message.state);
        }, buf -> {
            return new UpdateAll(buf.readBoolean());
        });

        public static void handle(UpdateAll message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageHomeControlUpdateAll(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }
}
