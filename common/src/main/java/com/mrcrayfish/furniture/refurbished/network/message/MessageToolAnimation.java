package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageToolAnimation extends PlayMessage<MessageToolAnimation>
{
    private Tool tool;
    private BlockPos pos;
    private Direction direction;

    public MessageToolAnimation() {}

    public MessageToolAnimation(Tool tool, BlockPos pos, Direction direction)
    {
        this.tool = tool;
        this.pos = pos;
        this.direction = direction;
    }

    @Override
    public void encode(MessageToolAnimation message, FriendlyByteBuf buffer)
    {
        buffer.writeEnum(message.tool);
        buffer.writeBlockPos(message.pos);
        buffer.writeEnum(message.direction);
    }

    @Override
    public MessageToolAnimation decode(FriendlyByteBuf buffer)
    {
        Tool tool = buffer.readEnum(Tool.class);
        BlockPos pos = buffer.readBlockPos();
        Direction direction = buffer.readEnum(Direction.class);
        return new MessageToolAnimation(tool, pos, direction);
    }

    @Override
    public void handle(MessageToolAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageToolAnimation(message));
        context.setHandled(true);
    }

    public Tool getTool()
    {
        return this.tool;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public Direction getDirection()
    {
        return this.direction;
    }

    public enum Tool
    {
        SPATULA, KNIFE
    }
}
