package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessagePaddleBall
{
    public static class PaddlePosition extends PlayMessage<PaddlePosition>
    {
        private float playerPos;
        private float opponentPos;

        public PaddlePosition() {}

        public PaddlePosition(float playerPos, float opponentPos)
        {
            this.playerPos = playerPos;
            this.opponentPos = opponentPos;
        }

        @Override
        public void encode(PaddlePosition message, FriendlyByteBuf buffer)
        {
            buffer.writeFloat(message.playerPos);
            buffer.writeFloat(message.opponentPos);
        }

        @Override
        public PaddlePosition decode(FriendlyByteBuf buffer)
        {
            float playerPos = buffer.readFloat();
            float opponentPos = buffer.readFloat();
            return new PaddlePosition(playerPos, opponentPos);
        }

        @Override
        public void handle(PaddlePosition message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageTennisGamePaddlePosition(message));
            context.setHandled(true);
        }

        public float getPlayerPos()
        {
            return this.playerPos;
        }

        public float getOpponentPos()
        {
            return this.opponentPos;
        }
    }

    public static class BallUpdate extends PlayMessage<BallUpdate>
    {
        private float ballX;
        private float ballY;
        private float velocityX;
        private float velocityY;

        public BallUpdate() {}

        public BallUpdate(float ballX, float ballY, float velocityX, float velocityY)
        {
            this.ballX = ballX;
            this.ballY = ballY;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
        }

        @Override
        public void encode(BallUpdate message, FriendlyByteBuf buffer)
        {
            buffer.writeFloat(message.ballX);
            buffer.writeFloat(message.ballY);
            buffer.writeFloat(message.velocityX);
            buffer.writeFloat(message.velocityY);
        }

        @Override
        public BallUpdate decode(FriendlyByteBuf buffer)
        {
            float ballX = buffer.readFloat();
            float ballY = buffer.readFloat();
            float velocityX = buffer.readFloat();
            float velocityY = buffer.readFloat();
            return new BallUpdate(ballX, ballY, velocityX, velocityY);
        }

        @Override
        public void handle(BallUpdate message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageTennisGameBallUpdate(message));
            context.setHandled(true);
        }

        public float getBallX()
        {
            return this.ballX;
        }

        public float getBallY()
        {
            return this.ballY;
        }

        public float getVelocityX()
        {
            return this.velocityX;
        }

        public float getVelocityY()
        {
            return this.velocityY;
        }
    }

    public static class Action extends PlayMessage<Action>
    {
        private PaddleBall.Action action;
        private byte data;

        public Action() {}

        public Action(PaddleBall.Action action, byte data)
        {
            this.action = action;
            this.data = data;
        }

        @Override
        public void encode(Action message, FriendlyByteBuf buffer)
        {
            buffer.writeEnum(message.action);
            buffer.writeByte(message.data);
        }

        @Override
        public Action decode(FriendlyByteBuf buffer)
        {
            PaddleBall.Action action = buffer.readEnum(PaddleBall.Action.class);
            byte data = buffer.readByte();
            return new Action(action, data);
        }

        @Override
        public void handle(Action message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageTennisGame(message, context.getPlayer()));
            context.setHandled(true);
        }

        public PaddleBall.Action getMode()
        {
            return this.action;
        }

        public byte getData()
        {
            return this.data;
        }
    }

    public static class Score extends PlayMessage<Score>
    {
        private int playerScore;
        private int opponentScore;

        public Score() {}

        public Score(int playerScore, int opponentScore)
        {
            this.playerScore = playerScore;
            this.opponentScore = opponentScore;
        }

        @Override
        public void encode(Score message, FriendlyByteBuf buffer)
        {
            buffer.writeInt(message.playerScore);
            buffer.writeInt(message.opponentScore);
        }

        @Override
        public Score decode(FriendlyByteBuf buffer)
        {
            int playerScore = buffer.readInt();
            int opponentScore = buffer.readInt();
            return new Score(playerScore, opponentScore);
        }

        @Override
        public void handle(Score message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageTennisGameScore(message));
            context.setHandled(true);
        }

        public int getPlayerScore()
        {
            return this.playerScore;
        }

        public int getOpponentScore()
        {
            return this.opponentScore;
        }
    }

    public static class Event extends PlayMessage<Event>
    {
        private byte data;

        public Event() {}

        public Event(byte event)
        {
            this.data = event;
        }

        @Override
        public void encode(Event message, FriendlyByteBuf buffer)
        {
            buffer.writeByte(message.data);
        }

        @Override
        public Event decode(FriendlyByteBuf buffer)
        {
            byte event = buffer.readByte();
            return new Event(event);
        }

        @Override
        public void handle(Event message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessagePaddleBallEvent(message));
            context.setHandled(true);
        }

        public byte getEvent()
        {
            return this.data;
        }
    }
}
