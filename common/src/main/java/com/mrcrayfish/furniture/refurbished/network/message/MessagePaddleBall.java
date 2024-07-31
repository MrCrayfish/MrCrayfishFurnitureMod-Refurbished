package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class MessagePaddleBall
{
    public record PaddlePosition(float playerPos, float opponentPos)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, PaddlePosition> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeFloat(message.playerPos);
            buf.writeFloat(message.opponentPos);
        }, buf -> {
            float playerPos = buf.readFloat();
            float opponentPos = buf.readFloat();
            return new PaddlePosition(playerPos, opponentPos);
        });

        public static void handle(PaddlePosition message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageTennisGamePaddlePosition(message));
            context.setHandled(true);
        }
    }

    public record BallUpdate(float ballX, float ballY, float velocityX, float velocityY)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, BallUpdate> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeFloat(message.ballX);
            buf.writeFloat(message.ballY);
            buf.writeFloat(message.velocityX);
            buf.writeFloat(message.velocityY);
        }, buf -> {
            float ballX = buf.readFloat();
            float ballY = buf.readFloat();
            float velocityX = buf.readFloat();
            float velocityY = buf.readFloat();
            return new BallUpdate(ballX, ballY, velocityX, velocityY);
        });

        public static void handle(BallUpdate message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageTennisGameBallUpdate(message));
            context.setHandled(true);
        }
    }

    public record Action(PaddleBall.Action action, byte data)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, Action> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeEnum(message.action);
            buf.writeByte(message.data);
        }, buf -> {
            PaddleBall.Action action = buf.readEnum(PaddleBall.Action.class);
            byte data = buf.readByte();
            return new Action(action, data);
        });

        public static void handle(Action message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageTennisGame(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }

    public record OpponentName(String name)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, OpponentName> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeUtf(message.name);
        }, buf -> {
            String name = buf.readUtf();
            return new OpponentName(name);
        });

        public static void handle(OpponentName message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessagePaddleBallOpponentName(message));
            context.setHandled(true);
        }
    }

    public record Event(byte data)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, Event> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeByte(message.data);
        }, buf -> {
            return new Event(buf.readByte());
        });

        public static void handle(Event message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessagePaddleBallEvent(message));
            context.setHandled(true);
        }
    }
}
