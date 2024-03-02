package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageHomeControl
{
    public record Toggle(BlockPos pos)
    {
        public static void encode(Toggle message, FriendlyByteBuf buffer)
        {
            buffer.writeBlockPos(message.pos);
        }

        public static Toggle decode(FriendlyByteBuf buffer)
        {
            return new Toggle(buffer.readBlockPos());
        }

        public static void handle(Toggle message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageHomeControlToggle(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }

    public record UpdateAll(boolean state)
    {
        public static void encode(UpdateAll message, FriendlyByteBuf buffer)
        {
            buffer.writeBoolean(message.state);
        }

        public static UpdateAll decode(FriendlyByteBuf buffer)
        {
            return new UpdateAll(buffer.readBoolean());
        }

        public static void handle(UpdateAll message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageHomeControlUpdateAll(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }
}
