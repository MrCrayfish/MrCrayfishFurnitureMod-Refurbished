package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public record MessageToolAnimation(Tool tool, BlockPos pos, Direction direction)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageToolAnimation> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeEnum(message.tool);
        buf.writeBlockPos(message.pos);
        buf.writeEnum(message.direction);
    }, buf -> {
        Tool tool = buf.readEnum(Tool.class);
        BlockPos pos = buf.readBlockPos();
        Direction direction = buf.readEnum(Direction.class);
        return new MessageToolAnimation(tool, pos, direction);
    });

    public static void handle(MessageToolAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageToolAnimation(message));
        context.setHandled(true);
    }

    public enum Tool
    {
        SPATULA, KNIFE
    }
}
