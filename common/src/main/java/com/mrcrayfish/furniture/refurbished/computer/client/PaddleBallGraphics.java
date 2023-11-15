package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.util.AudioHelper;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessagePaddleBall;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MrCrayfish
 */
public class PaddleBallGraphics extends DisplayableProgram<PaddleBall>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/program/paddle_ball.png");

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

    public PaddleBallGraphics(PaddleBall program)
    {
        super(program, PaddleBall.BOARD_WIDTH, PaddleBall.BOARD_HEIGHT);
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
        this.scoreAnimation = PaddleBall.RESET_COOLDOWN;
    }

    public void handleEvent(byte event)
    {
        switch(event)
        {
            case PaddleBall.EVENT_SOUND_HIT -> {
                AudioHelper.playUISound(ModSounds.UI_PADDLE_BALL_RETRO_HIT.get(), 1.0F, 0.5F);
            }
        }
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
        private final PaddleBallGraphics game;
        private final MenuButton playAiButton;
        private final MenuButton playVsButton;

        public MainMenuScene(PaddleBallGraphics game)
        {
            this.game = game;
            this.playAiButton = this.addWidget(new MenuButton(100, 16, this.game.translation("play_ai"), btn -> {
                Network.getPlay().sendToServer(new MessagePaddleBall.Action(PaddleBall.Action.JOIN_GAME, (byte) 0));
                game.setScene(new GameScene(game));
            }));
            this.playAiButton.setBackgroundHighlightColour(0xFF47403E);
            this.playAiButton.setTextHighlightColour(0xFF222225);
            this.playAiButton.setClickSound(ModSounds.UI_PADDLE_BALL_RETRO_CLICK.get());

            this.playVsButton = this.addWidget(new MenuButton(100, 16, this.game.translation("play_vs"), btn -> {
                Network.getPlay().sendToServer(new MessagePaddleBall.Action(PaddleBall.Action.JOIN_GAME, (byte) 1));
                game.setScene(new GameScene(game));
            }));
            this.playVsButton.setBackgroundHighlightColour(0xFF47403E);
            this.playVsButton.setTextHighlightColour(0xFF222225);
            this.playVsButton.setClickSound(ModSounds.UI_PADDLE_BALL_RETRO_CLICK.get());
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.playAiButton.setPosition(contentStart + (this.game.width - this.playAiButton.getWidth()) / 2, contentTop + 40);
            this.playVsButton.setPosition(contentStart + (this.game.width - this.playVsButton.getWidth()) / 2, contentTop + 60);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {
            graphics.blit(TEXTURE, (this.game.width - 128) / 2, 10, 16, 0, 128, 24);
        }

        private static class MenuButton extends ComputerButton
        {
            public MenuButton(int width, int height, Component label, OnPress onPress)
            {
                super(width, height, label, onPress);
            }

            @Override
            protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
            {
                super.renderWidget(graphics, mouseX, mouseY, partialTick);
                if(this.isActive() && this.isHoveredOrFocused())
                {
                    graphics.blit(TEXTURE, this.getX() - 6, this.getY() + 6, 12, 0, 4, 4);
                    graphics.blit(TEXTURE, this.getX() + this.getWidth() + 2, this.getY() + 6, 12, 0, 4, 4);
                }
            }
        }
    }

    private static class GameScene extends Scene
    {
        private final PaddleBallGraphics game;

        public GameScene(PaddleBallGraphics game)
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
            stack.translate(PaddleBall.BOARD_WIDTH / 2, 4, 0);

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
            graphics.blit(TEXTURE, 0, 0, 0, 0, PaddleBall.PADDLE_WIDTH + 2, PaddleBall.PADDLE_HEIGHT);
            stack.popPose();

            // Draw opponent paddle
            stack.pushPose();
            float smoothOpponentPos = Mth.lerp(partialTick, this.game.lastOpponentPos, this.game.opponentPos);
            stack.translate((PaddleBall.BOARD_WIDTH) - 8, smoothOpponentPos, 0);
            graphics.blit(TEXTURE, -2, 0, 6, 0, PaddleBall.PADDLE_WIDTH + 2, PaddleBall.PADDLE_HEIGHT);
            stack.popPose();

            // Draw ball
            stack.pushPose();
            float smoothBallX = Mth.lerp(partialTick, this.game.lastBallX, this.game.ballX);
            float smoothBallY = Mth.lerp(partialTick, this.game.lastBallY, this.game.ballY);
            stack.translate(smoothBallX, smoothBallY, 0);
            graphics.blit(TEXTURE, 0, 0, 12, 0, 4, 4);
            stack.popPose();

            if(this.game.scoreAnimation > 0 && (this.game.scoreAnimation / 5) % 2 == 0)
            {
                stack.pushPose();
                stack.translate((PaddleBall.BOARD_WIDTH - PaddleBall.PADDLE_WIDTH) * this.game.scoreSide, 0, 0);
                graphics.fill(0, 0, PaddleBall.PADDLE_WIDTH, PaddleBall.BOARD_HEIGHT, 0xFF653938);
                stack.popPose();
            }
        }

        private void sendInputToServer()
        {
            // TODO controllable support
            boolean up = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_UP);
            boolean down = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_DOWN);
            byte input = (byte) (up && down ? 0 : up ? 1 : down ? 2 : 0);
            Network.getPlay().sendToServer(new MessagePaddleBall.Action(PaddleBall.Action.INPUT, input));
        }

    }
}
