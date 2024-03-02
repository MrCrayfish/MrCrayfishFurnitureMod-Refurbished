package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageToolAnimation(Tool tool, BlockPos pos, Direction direction)
{
    public static void encode(MessageToolAnimation message, FriendlyByteBuf buffer)
    {
        buffer.writeEnum(message.tool);
        buffer.writeBlockPos(message.pos);
        buffer.writeEnum(message.direction);
    }

    public static MessageToolAnimation decode(FriendlyByteBuf buffer)
    {
        Tool tool = buffer.readEnum(Tool.class);
        BlockPos pos = buffer.readBlockPos();
        Direction direction = buffer.readEnum(Direction.class);
        return new MessageToolAnimation(tool, pos, direction);
    }

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
