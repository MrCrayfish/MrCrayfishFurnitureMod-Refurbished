package com.mrcrayfish.furniture.refurbished.computer.client.graphics;

import com.mojang.blaze3d.platform.InputConstants;
import com.mrcrayfish.furniture.refurbished.client.util.AudioHelper;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessagePaddleBall;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MrCrayfish
 */
public class PaddleBallGraphics extends DisplayableProgram<PaddleBall>
{
    private static final Identifier TEXTURE = Utils.id("textures/gui/program/paddle_ball.png");

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
    private @Nullable Component displayLabel;
    private Boolean wonGame;
    private boolean playing;
    private boolean leftPaddle;
    private String opponentName = "Opponent";

    public PaddleBallGraphics(PaddleBall program)
    {
        super(program, PaddleBall.BOARD_WIDTH, PaddleBall.BOARD_HEIGHT);
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

    @Override
    public boolean blocksNavigation()
    {
        return this.playing;
    }

    public void updatePaddles(float playerPos, float opponentPos)
    {
        this.targetPlayerPos = this.leftPaddle ? playerPos : opponentPos;
        this.targetOpponentPos = this.leftPaddle ? opponentPos : playerPos;
    }

    public void updateBall(float ballX, float ballY, float velocityX, float velocityY)
    {
        this.ballX = ballX;
        this.ballY = ballY;
        this.ballVelocityX = velocityX;
        this.ballVelocityY = velocityY;
    }

    public void handleOpponentName(String name)
    {
        this.opponentName = name;
    }

    public void handleEvent(byte event)
    {
        switch(event)
        {
            case PaddleBall.EVENT_GAME_START -> {
                this.reset();
                this.playing = true;
                this.setScene(new GameScene(this));
            }
            case PaddleBall.EVENT_GAME_SIDE_LEFT -> {
                this.leftPaddle = true;
            }
            case PaddleBall.EVENT_GAME_SIDE_RIGHT -> {
                this.leftPaddle = false;
            }
            case PaddleBall.EVENT_GAME_WIN -> {
                AudioHelper.playUISound(ModSounds.UI_PADDLE_BALL_RETRO_WIN.get(), 1.0F, 0.5F);
                this.displayLabel = this.translation("win_game");
                this.wonGame = true;
                this.playing = false;
            }
            case PaddleBall.EVENT_GAME_LOSE -> {
                AudioHelper.playUISound(ModSounds.UI_PADDLE_BALL_RETRO_LOSE.get(), 1.0F, 0.5F);
                this.displayLabel = this.translation("lose_game");
                this.wonGame = false;
                this.playing = false;
            }
            case PaddleBall.EVENT_GAME_ROUND_WIN -> {
                this.playerScore++;
                this.scoreSide = 1;
                this.scoreAnimation = PaddleBall.RESET_COOLDOWN;
                if(this.playerScore < PaddleBall.POINTS_TO_WIN)
                {
                    AudioHelper.playUISound(ModSounds.UI_PADDLE_BALL_RETRO_SUCCESS.get(), 1.0F, 0.5F);
                }
            }
            case PaddleBall.EVENT_GAME_ROUND_LOSE -> {
                this.opponentScore++;
                this.scoreSide = 0;
                this.scoreAnimation = PaddleBall.RESET_COOLDOWN;
                if(this.opponentScore < PaddleBall.POINTS_TO_WIN)
                {
                    AudioHelper.playUISound(ModSounds.UI_PADDLE_BALL_RETRO_FAIL.get(), 1.0F, 0.5F);
                }
            }
            case PaddleBall.EVENT_GAME_OPPONENT_LEFT -> {
                this.displayLabel = this.translation("opponent_left");
                this.wonGame = false;
            }
            case PaddleBall.EVENT_SOUND_HIT -> {
                AudioHelper.playUISound(ModSounds.UI_PADDLE_BALL_RETRO_HIT.get(), 1.0F, 0.5F);
            }
        }
    }

    private void reset()
    {
        this.displayLabel = null;
        this.wonGame = null;
        this.playerScore = 0;
        this.opponentScore = 0;
        this.ballVelocityX = 0;
        this.ballVelocityY = 0;
        this.scoreAnimation = 0;
        this.opponentName = "Opponent";
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
            }));
            this.playAiButton.setClickSound(ModSounds.UI_PADDLE_BALL_RETRO_CLICK.get());

            this.playVsButton = this.addWidget(new MenuButton(100, 16, this.game.translation("play_vs"), btn -> {
                Network.getPlay().sendToServer(new MessagePaddleBall.Action(PaddleBall.Action.JOIN_GAME, (byte) 1));
                game.setScene(new PendingScene(game));
            }));
            this.playVsButton.setClickSound(ModSounds.UI_PADDLE_BALL_RETRO_CLICK.get());

            // Disable the vs player button if not in a server
            Minecraft mc = Minecraft.getInstance();
            if((mc.getSingleplayerServer() == null || mc.hasSingleplayerServer()) && mc.getCurrentServer() == null)
            {
                this.playVsButton.setTooltip(Tooltip.create(this.game.translation("server_required")));
                this.playVsButton.active = false;
            }

            game.playing = false;
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.playAiButton.setPosition(contentStart + (this.game.width - this.playAiButton.getWidth()) / 2, contentTop + 45);
            this.playVsButton.setPosition(contentStart + (this.game.width - this.playVsButton.getWidth()) / 2, contentTop + 65);
        }

        @Override
        public void render(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
        {
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, (this.game.width - 128) / 2, 10, 16, 0, 128, 24, 256, 256);
        }

        private static class MenuButton extends ComputerButton
        {
            public MenuButton(int width, int height, Component label, OnPress onPress)
            {
                super(width, height, label, onPress);
            }

            @Override
            public void extractContents(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
            {
                super.extractContents(extractor, mouseX, mouseY, partialTick);
                if(this.isActive() && this.isHoveredOrFocused())
                {
                    extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.getX() - 6, this.getY() + 6, 12, 0, 4, 4, 256, 256);
                    extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.getX() + this.getWidth() + 2, this.getY() + 6, 12, 0, 4, 4, 256, 256);
                }
            }
        }
    }

    private static class PendingScene extends Scene
    {
        private final PaddleBallGraphics game;
        private final MainMenuScene.MenuButton backButton;

        private PendingScene(PaddleBallGraphics game)
        {
            this.game = game;
            this.backButton = this.addWidget(new MainMenuScene.MenuButton(100, 16, this.game.translation("cancel"), btn -> {
                Network.getPlay().sendToServer(new MessagePaddleBall.Action(PaddleBall.Action.UPDATE_STATE, (byte) 0));
                game.setScene(new MainMenuScene(game));
            }));
            this.backButton.setClickSound(ModSounds.UI_PADDLE_BALL_RETRO_CLICK.get());
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.backButton.setPosition(contentStart + (this.game.width - this.backButton.getWidth()) / 2, contentTop + 65);
        }

        @Override
        public void render(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
        {
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, (this.game.width - 128) / 2, 10, 16, 0, 128, 24, 256, 256);

            String loading = switch((int) (Util.getMillis() / 300L % 4L)) {
                default -> "O o o";
                case 1, 3 -> "o O o";
                case 2 -> "o o O";
            };
            Minecraft mc = Minecraft.getInstance();
            extractor.centeredText(mc.font, loading, this.game.width / 2, 50, 0xFFD3CCBE);
            extractor.centeredText(mc.font, this.game.translation("searching_players"), this.game.width / 2, 40, 0xFFD3CCBE);
        }
    }

    private static class GameScene extends Scene
    {
        private final PaddleBallGraphics game;
        private final MainMenuScene.MenuButton backButton;

        public GameScene(PaddleBallGraphics game)
        {
            this.game = game;
            this.backButton = this.addWidget(new MainMenuScene.MenuButton(100, 16, this.game.translation("main_menu"), btn -> {
                game.setScene(new MainMenuScene(game));
            }));
            this.backButton.setBackgroundHighlightColour(0xFF47403E);
            this.backButton.setTextHighlightColour(0xFF222225);
            this.backButton.setClickSound(ModSounds.UI_PADDLE_BALL_RETRO_CLICK.get());
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.backButton.setPosition(contentStart + (this.game.width - this.backButton.getWidth()) / 2, contentTop + 60);
        }

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
        public void render(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
        {
            this.backButton.active = this.game.wonGame != null;
            this.backButton.visible = this.game.wonGame != null;

            Minecraft mc = Minecraft.getInstance();

            extractor.pose().pushMatrix();
            extractor.pose().translate(PaddleBall.BOARD_WIDTH / 2, 4);

            // Draw player score
            extractor.pose().pushMatrix();
            String label = Integer.toString(this.game.playerScore);
            int width = mc.font.width(label) * 2;
            extractor.pose().translate(-width - 7, 0);
            extractor.pose().scale(2, 2);
            extractor.text(mc.font, label, 0, 0, 0xFF2F2F33, false);
            extractor.pose().popMatrix();

            extractor.pose().pushMatrix();
            extractor.pose().scale(2, 2);
            int breakWidth = mc.font.width(label) / 2;
            extractor.text(mc.font, "-", -breakWidth, 0, 0xFF2F2F33, false);
            extractor.pose().popMatrix();

            // Draw opponent score
            extractor.pose().pushMatrix();
            extractor.pose().translate(7, 0);
            extractor.pose().scale(2, 2);
            extractor.text(mc.font, Integer.toString(this.game.opponentScore), 0, 0, 0xFF2F2F33, false);
            extractor.pose().popMatrix();

            extractor.pose().popMatrix();

            // Draw name tags
            extractor.text(mc.font, this.game.translation("you"), 5, 5, 0xFF2F2F33, false);
            String opponentName = this.game.opponentName;
            extractor.text(mc.font, opponentName, PaddleBall.BOARD_WIDTH - 5 - mc.font.width(opponentName), 5, 0xFF2F2F33, false);

            // Draw host paddle
            extractor.pose().pushMatrix();
            float smoothHostPos = Mth.lerp(partialTick, this.game.lastPlayerPos, this.game.playerPos);
            extractor.pose().translate(4, smoothHostPos);
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, 0, 0, PaddleBall.PADDLE_WIDTH + 2, PaddleBall.PADDLE_HEIGHT, PaddleBall.PADDLE_WIDTH + 2, PaddleBall.PADDLE_HEIGHT, 256, 256);
            extractor.pose().popMatrix();

            // Draw opponent paddle
            extractor.pose().pushMatrix();
            float smoothOpponentPos = Mth.lerp(partialTick, this.game.lastOpponentPos, this.game.opponentPos);
            extractor.pose().translate((PaddleBall.BOARD_WIDTH) - 8, smoothOpponentPos);
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, -2, 0, 6, 0, PaddleBall.PADDLE_WIDTH + 2, PaddleBall.PADDLE_HEIGHT, PaddleBall.PADDLE_WIDTH + 2, PaddleBall.PADDLE_HEIGHT, 256, 256);
            extractor.pose().popMatrix();

            // Draw ball
            extractor.pose().pushMatrix();
            float smoothBallX = Mth.lerp(partialTick, this.game.lastBallX, this.game.ballX);
            float smoothBallY = Mth.lerp(partialTick, this.game.lastBallY, this.game.ballY);
            smoothBallX = this.game.leftPaddle ? smoothBallX : PaddleBall.BOARD_WIDTH - smoothBallX;
            extractor.pose().translate(smoothBallX, smoothBallY);
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, 12, 0, 4, 4, 4, 4, 256, 256);
            extractor.pose().popMatrix();

            if(this.game.scoreAnimation > 0 && (this.game.scoreAnimation / 5) % 2 == 0)
            {
                extractor.pose().pushMatrix();
                extractor.pose().translate((PaddleBall.BOARD_WIDTH - PaddleBall.PADDLE_WIDTH) * this.game.scoreSide, 0);
                extractor.fill(0, 0, PaddleBall.PADDLE_WIDTH, PaddleBall.BOARD_HEIGHT, 0xFF653938);
                extractor.pose().popMatrix();
            }

            if(this.game.wonGame != null && this.game.displayLabel != null)
            {
                Component bannerLabel = this.game.displayLabel;
                int bannerColour = this.game.wonGame ? 0xFF376337 : 0xFF653938;
                extractor.centeredText(mc.font, bannerLabel, this.game.width / 2, 40, bannerColour);
            }
        }

        private void sendInputToServer()
        {
            // TODO controllable support
            boolean up = InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_UP);
            boolean down = InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_DOWN);
            byte input = (byte) (up && down ? 0 : up ? 1 : down ? 2 : 0);
            Network.getPlay().sendToServer(new MessagePaddleBall.Action(PaddleBall.Action.INPUT, input));
        }

    }
}
