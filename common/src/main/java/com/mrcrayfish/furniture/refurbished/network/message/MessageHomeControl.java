package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageHomeControl
{
    public static class Toggle extends PlayMessage<Toggle>
    {
        private BlockPos pos;

        public Toggle() {}

        public Toggle(BlockPos pos)
        {
            this.pos = pos;
        }

        @Override
        public void encode(Toggle message, FriendlyByteBuf buffer)
        {
            buffer.writeBlockPos(message.pos);
        }

        @Override
        public Toggle decode(FriendlyByteBuf buffer)
        {
            return new Toggle(buffer.readBlockPos());
        }

        @Override
        public void handle(Toggle message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageHomeKitToggle(message, context.getPlayer()));
            context.setHandled(true);
        }

        public BlockPos getPos()
        {
            return this.pos;
        }
    }
}
