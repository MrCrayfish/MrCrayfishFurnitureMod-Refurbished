package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.computer.app.TennisGame;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTennisGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MrCrayfish
 */
public class TennisGameGraphics extends DisplayableProgram<TennisGame>
{
    private float ballX;
    private float lastBallX;
    private float ballY;
    private float lastBallY;
    private float ballVelocityX;
    private float ballVelocityY;
    private float playerPos;
    private float lastPlayerPos;
    private float targetPlayerPos;
    private float opponentPos;
    private float lastOpponentPos;
    private float targetOpponentPos;
    private int playerScore;
    private int opponentScore;
    private int scoreSide;
    private int scoreAnimation;

    public TennisGameGraphics(TennisGame program)
    {
        super(program, TennisGame.BOARD_WIDTH, TennisGame.BOARD_HEIGHT);
        this.setWindowOutlineColour(0xFF47403E);
        this.setWindowTitleBarColour(0xFF5B5450);
        this.setScene(new MainMenuScene(this));
    }

    @Override
    public void tick()
    {
        this.lastBallX = this.ballX;
        this.lastBallY = this.ballY;
        this.lastPlayerPos = this.playerPos;
        this.lastOpponentPos = this.opponentPos;
        this.ballX += this.ballVelocityX;
        this.ballY += this.ballVelocityY;
        this.playerPos = this.targetPlayerPos;
        this.opponentPos = this.targetOpponentPos;
        super.tick();
    }

    public void updatePaddles(float playerPos, float opponentPos)
    {
        this.targetPlayerPos = playerPos;
        this.targetOpponentPos = opponentPos;
    }

    public void updateBall(float ballX, float ballY, float velocityX, float velocityY)
    {
        this.ballX = ballX;
        this.ballY = ballY;
        this.ballVelocityX = velocityX;
        this.ballVelocityY = velocityY;
    }

    public void updateScore(int playerScore, int opponentScore)
    {
        this.scoreSide = this.playerScore != playerScore ? 1 : 0;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        this.scoreAnimation = TennisGame.RESET_COOLDOWN;
    }

    private void reset()
    {
        this.playerScore = 0;
        this.opponentScore = 0;
        this.ballVelocityX = 0;
        this.ballVelocityY = 0;
    }

    private static class MainMenuScene extends Scene
    {
        private final TennisGameGraphics game;
        private final Button testButton;

        public MainMenuScene(TennisGameGraphics game)
        {
            this.game = game;
            this.testButton = this.addWidget(Button.builder(Component.literal("Play"), var1 -> {
                Network.getPlay().sendToServer(new MessageTennisGame.Action(TennisGame.Action.JOIN_GAME, (byte) 0));
                game.setScene(new GameScene(game));
            }).size(50, 20).build());
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.testButton.setX(contentStart - 5);
            this.testButton.setY(contentTop + 5);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {

        }
    }

    private static class GameScene extends Scene
    {
        private final TennisGameGraphics game;

        public GameScene(TennisGameGraphics game)
        {
            this.game = game;
            game.reset();
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop) {}

        @Override
        public void tick()
        {
            this.sendInputToServer();
            if(this.game.scoreAnimation > 0)
            {
                this.game.scoreAnimation--;
            }
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {
            Minecraft mc = Minecraft.getInstance();
            PoseStack stack = graphics.pose();

            stack.pushPose();
            stack.translate(TennisGame.BOARD_WIDTH / 2, 4, 0);

            // Draw player score
            stack.pushPose();
            String label = Integer.toString(this.game.playerScore);
            int width = mc.font.width(label) * 4;
            stack.translate(-width - 4, 0, 0);
            stack.scale(4, 4, 4);
            graphics.drawString(mc.font, label, 0, 0, 0xFF2F2F33, false);
            stack.popPose();

            // Draw opponent score
            stack.pushPose();
            stack.translate(4, 0, 0);
            stack.scale(4, 4, 4);
            graphics.drawString(mc.font, Integer.toString(this.game.opponentScore), 0, 0, 0xFF2F2F33, false);
            stack.popPose();

            stack.popPose();

            // Draw host paddle
            stack.pushPose();
            // TODO maybe let client control position instead of server
            float smoothHostPos = Mth.lerp(partialTick, this.game.lastPlayerPos, this.game.playerPos);
            stack.translate(4, smoothHostPos, 0);
            graphics.fill(0, 0, 4, TennisGame.PADDLE_HEIGHT, 0xFFFFFFFF);
            stack.popPose();

            // Draw opponent paddle
            stack.pushPose();
            float smoothOpponentPos = Mth.lerp(partialTick, this.game.lastOpponentPos, this.game.opponentPos);
            stack.translate((TennisGame.BOARD_WIDTH) - 8, smoothOpponentPos, 0);
            graphics.fill(0, 0, 4, TennisGame.PADDLE_HEIGHT, 0xFFFFFFFF);
            stack.popPose();

            // Draw ball
            stack.pushPose();
            float smoothBallX = Mth.lerp(partialTick, this.game.lastBallX, this.game.ballX);
            float smoothBallY = Mth.lerp(partialTick, this.game.lastBallY, this.game.ballY);
            stack.translate(smoothBallX, smoothBallY, 0);
            graphics.fill(0, 0, 4, 4, 0xFFFF0000);
            stack.popPose();

            if(this.game.scoreAnimation > 0 && (this.game.scoreAnimation / 5) % 2 == 0)
            {
                stack.pushPose();
                stack.translate((TennisGame.BOARD_WIDTH - TennisGame.PADDLE_WIDTH) * this.game.scoreSide, 0, 0);
                graphics.fill(0, 0, TennisGame.PADDLE_WIDTH, TennisGame.BOARD_HEIGHT, 0xFFFF0000);
                stack.popPose();
            }
        }

        private void sendInputToServer()
        {
            // TODO controllable support
            boolean up = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_UP);
            boolean down = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_DOWN);
            byte input = (byte) (up && down ? 0 : up ? 1 : down ? 2 : 0);
            Network.getPlay().sendToServer(new MessageTennisGame.Action(TennisGame.Action.INPUT, input));
        }

    }
}
