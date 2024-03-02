package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessagePaddleBall
{
    public record PaddlePosition(float playerPos, float opponentPos)
    {
        public static void encode(PaddlePosition message, FriendlyByteBuf buffer)
        {
            buffer.writeFloat(message.playerPos);
            buffer.writeFloat(message.opponentPos);
        }

        public static PaddlePosition decode(FriendlyByteBuf buffer)
        {
            float playerPos = buffer.readFloat();
            float opponentPos = buffer.readFloat();
            return new PaddlePosition(playerPos, opponentPos);
        }

        public static void handle(PaddlePosition message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageTennisGamePaddlePosition(message));
            context.setHandled(true);
        }
    }

    public record BallUpdate(float ballX, float ballY, float velocityX, float velocityY)
    {
        public static void encode(BallUpdate message, FriendlyByteBuf buffer)
        {
            buffer.writeFloat(message.ballX);
            buffer.writeFloat(message.ballY);
            buffer.writeFloat(message.velocityX);
            buffer.writeFloat(message.velocityY);
        }

        public static BallUpdate decode(FriendlyByteBuf buffer)
        {
            float ballX = buffer.readFloat();
            float ballY = buffer.readFloat();
            float velocityX = buffer.readFloat();
            float velocityY = buffer.readFloat();
            return new BallUpdate(ballX, ballY, velocityX, velocityY);
        }

        public static void handle(BallUpdate message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageTennisGameBallUpdate(message));
            context.setHandled(true);
        }
    }

    public record Action(PaddleBall.Action action, byte data)
    {
        public static void encode(Action message, FriendlyByteBuf buffer)
        {
            buffer.writeEnum(message.action);
            buffer.writeByte(message.data);
        }

        public static Action decode(FriendlyByteBuf buffer)
        {
            PaddleBall.Action action = buffer.readEnum(PaddleBall.Action.class);
            byte data = buffer.readByte();
            return new Action(action, data);
        }

        public static void handle(Action message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageTennisGame(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }

    public record OpponentName(String name)
    {
        public static void encode(OpponentName message, FriendlyByteBuf buffer)
        {
            buffer.writeUtf(message.name);
        }

        public static OpponentName decode(FriendlyByteBuf buffer)
        {
            String name = buffer.readUtf();
            return new OpponentName(name);
        }

        public static void handle(OpponentName message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessagePaddleBallOpponentName(message));
            context.setHandled(true);
        }
    }

    public record Event(byte data)
    {
        public static void encode(Event message, FriendlyByteBuf buffer)
        {
            buffer.writeByte(message.data);
        }

        public static Event decode(FriendlyByteBuf buffer)
        {
            byte event = buffer.readByte();
            return new Event(event);
        }

        public static void handle(Event message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessagePaddleBallEvent(message));
            context.setHandled(true);
        }
    }
}
