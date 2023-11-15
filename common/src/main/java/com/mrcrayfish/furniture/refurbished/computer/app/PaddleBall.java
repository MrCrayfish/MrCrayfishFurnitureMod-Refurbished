package com.mrcrayfish.furniture.refurbished.computer.app;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.computer.IService;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessagePaddleBall;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.joml.Intersectionf;
import org.joml.Math;
import org.joml.Vector2f;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Author: MrCrayfish
 */
public class PaddleBall extends Program
{
    public static final MatchmakingService SERVICE = new MatchmakingService();
    public static final int BOARD_WIDTH = 200;
    public static final int BOARD_HEIGHT = 100;
    public static final int PADDLE_WIDTH = 4;
    public static final int PADDLE_HEIGHT = 28;
    public static final float PADDLE_SPEED = 4.0F;
    public static final int RESET_COOLDOWN = 40;
    public static final int POINTS_TO_WIN = 7;

    public static final byte EVENT_GAME_WIN = 1;
    public static final byte EVENT_GAME_LOSE = 2;
    public static final byte EVENT_GAME_ROUND_WIN = 3;
    public static final byte EVENT_GAME_ROUND_LOSE = 4;
    public static final byte EVENT_SOUND_HIT = 64;

    private Game activeGame;
    private State state;
    private PlayerController controller;

    public PaddleBall(ResourceLocation id, IComputer computer)
    {
        super(id, computer);
        this.state = State.MAIN_MENU;
    }

    @Override
    public void tick()
    {
        if(this.computer.isServer() && this.activeGame != null)
        {
            if(this.computer.getUser() == null || this.activeGame.finished)
            {
                this.state = State.MAIN_MENU;
                this.activeGame = null;
                this.controller = null;
            }
        }
    }

    @Override
    public void onClose(boolean remote)
    {
        this.activeGame = null;
    }

    /**
     * Handles an action update sent from clients.
     *
     * @param action the action that was performed on the client
     * @param data   the data associated with the action
     */
    public void update(Action action, byte data)
    {
        if(action == Action.JOIN_GAME && this.activeGame == null)
        {
            if(data == 0)
            {
                this.controller = new PlayerController(this.computer.getUser(), this);
                SERVICE.createAiGame(this.controller);
                this.state = State.IN_GAME;
            }
            else if(data == 1)
            {
                this.controller = new PlayerController(this.computer.getUser(), this);
                SERVICE.createMultiplayerGame(this.controller);
                this.state = this.activeGame.isRunning() ? State.IN_GAME : State.PENDING;
            }
        }
        else if(action == Action.UPDATE_STATE)
        {
            if(data == 0)
            {
                this.state = State.MAIN_MENU;
                this.activeGame = null;
                this.controller = null;
            }
            else if(data == 1)
            {
                this.state = State.PENDING;
            }
            else if(data == 2)
            {
                this.state = State.IN_GAME;
            }
        }
        else if(action == Action.INPUT)
        {
            this.controller.setInputUp(data == 1);
            this.controller.setInputDown(data == 2);
        }
    }

    protected abstract static class GameObject
    {
        protected final float width, height;
        protected float x1, y1, x2, y2;
        protected Vector2f pos = new Vector2f();
        protected Game game;

        public GameObject(float width, float height)
        {
            this.width = width;
            this.height = height;
            this.updateBoundingBox(0, 0);
        }

        /**
         * Utility method to update the bounding box bounds to the {@link #pos} coordinates
         */
        protected void updateBoundingBox()
        {
            this.updateBoundingBox(this.pos.x, this.pos.y);
        }

        /**
         * Updates the bounding box bounds to be adjusted to the given x and y position
         *
         * @param x the new x position of the bounds
         * @param y the new y position of the bounds
         */
        protected void updateBoundingBox(float x, float y)
        {
            this.x1 = x;
            this.y1 = y;
            this.x2 = x + this.width;
            this.y2 = y + this.height;
        }

        /**
         * Sets the game this game object has been added to
         *
         * @param game the game that added this object
         */
        public void setGame(Game game)
        {
            this.game = game;
        }
    }

    protected static abstract class Controller extends GameObject
    {
        protected Side side;
        protected int score;

        public Controller()
        {
            super(PADDLE_WIDTH, PADDLE_HEIGHT);
            this.side = Side.LEFT;
            this.moveToCenter();
        }

        /**
         * @return True if the controller is playing the game
         */
        protected abstract boolean isPlaying();

        /**
         * Runs an update on the controller for movement
         */
        protected abstract void update();

        /**
         * Sets the side this paddle is positioned on the board
         * @param side the side
         */
        public void setSide(Side side)
        {
            this.side = side;
            if(this.pos != null)
            {
                this.pos.x = side.getPosition();
            }
        }

        /**
         * Moves the paddle to the center on the vertical axis.
         */
        public void moveToCenter()
        {
            this.pos = new Vector2f(this.side.getPosition(), (BOARD_HEIGHT - PADDLE_HEIGHT) / 2.0F);
            this.updateBoundingBox();
        }

        /**
         * Sends an update to the controller. Only implemented in {@link PlayerController} to send
         * updates about the game, like ball velocity, positions, etc.
         *
         * @param type the type of update. See {@link UpdateType}
         */
        public void sendUpdate(UpdateType type) {}

        public void sendEvent(byte event) {}
    }

    protected static class PlayerController extends Controller
    {
        protected final Player player;
        protected final PaddleBall program;
        protected boolean inputUp;
        protected boolean inputDown;

        private PlayerController(Player player, PaddleBall program)
        {
            this.player = player;
            this.program = program;
        }

        /**
         * Sets the up input state
         * @param inputUp true if the paddle should move up
         */
        public void setInputUp(boolean inputUp)
        {
            this.inputUp = inputUp;
        }

        /**
         * Sets the down input state
         * @param inputDown true if the paddle should move down
         */
        public void setInputDown(boolean inputDown)
        {
            this.inputDown = inputDown;
        }

        @Override
        public boolean isPlaying()
        {
            IComputer computer = this.program.getComputer();
            return this.player.equals(computer.getUser()) && computer.getProgram() == this.program && this.program.activeGame == this.game;
        }

        @Override
        public void update()
        {
            if(!this.game.freezePaddles)
            {
                this.pos.y += this.inputUp && !this.inputDown ? -PADDLE_SPEED : !this.inputUp && this.inputDown ? PADDLE_SPEED : 0;
                this.pos.y = Mth.clamp(this.pos.y, 3, BOARD_HEIGHT - PADDLE_HEIGHT - 3);
                this.updateBoundingBox();
            }
        }

        @Override
        public void sendUpdate(UpdateType type)
        {
            // Don't send if not playing
            if(!this.isPlaying())
                return;

            if(UpdateType.BALL.is(type))
            {
                ServerPlayer player = (ServerPlayer) this.player;
                Network.getPlay().sendToPlayer(() -> player, this.game.createBallUpdateMessage());
            }
            if(UpdateType.PADDLES.is(type))
            {
                ServerPlayer player = (ServerPlayer) this.player;
                Network.getPlay().sendToPlayer(() -> player, this.game.createPaddlePositionMessage());
            }
        }

        @Override
        public void sendEvent(byte event)
        {
            if(!this.isPlaying())
                return;

            ServerPlayer player = (ServerPlayer) this.player;
            Network.getPlay().sendToPlayer(() -> player, new MessagePaddleBall.Event(event));
        }

        @Override
        public void setGame(Game game)
        {
            super.setGame(game);
            this.program.activeGame = game;
        }
    }

    protected static class AiController extends Controller
    {
        @Override
        public boolean isPlaying()
        {
            return true;
        }

        @Override
        public void update()
        {
            if(!this.game.freezePaddles && this.game.ball.velocity.x > 0)
            {
                float tolerance = this.game.difficulty.aiTolerance;
                float paddleSpeed = this.game.difficulty.aiPaddleSpeed;
                if(this.game.ball.pos.y < this.pos.y + (PADDLE_HEIGHT / 2F) - tolerance)
                {
                    this.pos.y -= Math.min(paddleSpeed, this.pos.y + (PADDLE_HEIGHT / 2F) - this.game.ball.pos.y);
                }
                else if(this.game.ball.pos.y > this.pos.y + (PADDLE_HEIGHT / 2F) + tolerance)
                {
                    this.pos.y += Math.min(paddleSpeed, this.game.ball.pos.y - this.pos.y + (PADDLE_HEIGHT / 2F));
                }
                this.pos.y = Mth.clamp(this.pos.y, 3, BOARD_HEIGHT - PADDLE_HEIGHT - 3);
                this.updateBoundingBox(this.pos.x, this.pos.y);
            }
        }
    }

    public static class Ball extends GameObject
    {
        protected Vector2f velocity = new Vector2f();

        protected Ball(Game game)
        {
            super(4, 4);
            this.game = game;
            this.pos = new Vector2f(BOARD_WIDTH / 2.0F, (BOARD_HEIGHT - 4) / 2.0F);
            this.updateBoundingBox();
        }

        /**
         * Creates a random velocity for the ball
         */
        public void randomVelocity()
        {
            this.velocity.x = 8;
            this.reflectYVelocity(this.game.random.nextFloat());
        }

        /**
         * Performs a move update for the ball
         */
        public void move()
        {
            this.pos.add(this.velocity);
            this.updateBoundingBox();
            if(this.performCollision())
            {
                this.game.sendUpdateToPlayers(UpdateType.BALL);
                this.game.sendEventToPlayers(EVENT_SOUND_HIT);
            }
        }

        @Override
        protected void updateBoundingBox()
        {
            this.updateBoundingBox(this.pos.x - 2, this.pos.y - 2);
        }

        /**
         * Updates the y velocity of the ball depending on the given hit factor. The hit factor is
         * the position the ball hit a paddle. If the ball hit the top of the paddle, the value is 0.
         * If the ball hit the bottom, the value is one. This value is then used to calculate a new
         * y velocity for the ball. If the value is closer to 0, the ball will move in an up direction,
         * while a value closer to 1 will move in a down direction.
         *
         * @param hit the position the ball hit on the paddle from 0 to 1
         */
        public void reflectYVelocity(float hit)
        {
            hit = Math.clamp(hit, 0.0F, 1.0F);
            hit = (hit - 0.5F) * 2F;
            this.velocity.y = this.game.difficulty.ballYSpeed * hit;
        }

        /**
         * Performs the collision detection for the ball. This will check for collisions against the
         * top and bottom walls, and the paddles.
         *
         * @return True if collided with something
         */
        private boolean performCollision()
        {
            boolean collided = false;

            // Perform collision with the top wall
            if(this.velocity.y < 0 && this.y1 < 0)
            {
                float startX = -100;
                float endX = BOARD_WIDTH + 100;
                Vector2f point = new Vector2f();
                if(Intersectionf.intersectLineLine(startX, 0, endX, 0, this.x1, this.y1, this.x1 - this.velocity.x, this.y1 - this.velocity.y, point))
                {
                    this.pos.x = point.x + 2;
                    this.pos.y = point.y + 2;
                    this.velocity.y = -this.velocity.y;
                    this.updateBoundingBox();
                    collided = true;
                }
            }

            // Perform collision with the bottom wall
            if(this.velocity.y > 0 && this.y2 > BOARD_HEIGHT)
            {
                float startX = -100;
                float endX = BOARD_WIDTH + 100;
                Vector2f point = new Vector2f();
                if(Intersectionf.intersectLineLine(startX, BOARD_HEIGHT, endX, BOARD_HEIGHT, this.x2, this.y2, this.x2 - this.velocity.x, this.y2 - this.velocity.y, point))
                {
                    this.pos.x = point.x - 2;
                    this.pos.y = point.y - 2;
                    this.velocity.y = -this.velocity.y;
                    this.updateBoundingBox();
                    collided = true;
                }
            }

            // Perform collision with left paddle
            if(this.velocity.x < 0 && this.x1 < this.game.host.x2)
            {
                // Test the top left and bottom left corners
                boolean checkBottomCorner = true;
                Vector2f point = new Vector2f();
                if(Intersectionf.intersectLineLine(this.game.host.x2, this.game.host.y1, this.game.host.x2, this.game.host.y2, this.x1, this.y1, this.x1 - this.velocity.x, this.y1 - this.velocity.y, point))
                {
                    // Check if intersection point is within line segment
                    if(point.y >= this.game.host.y1 && point.y < this.game.host.y2)
                    {
                        this.pos.x = point.x + 2;
                        this.pos.y = point.y + 2;
                        this.velocity.x = -this.velocity.x;
                        this.reflectYVelocity((this.pos.y - this.game.host.y1) / PADDLE_HEIGHT);
                        this.updateBoundingBox();
                        collided = true;
                        checkBottomCorner = false;
                    }
                }
                if(checkBottomCorner && Intersectionf.intersectLineLine(this.game.host.x2, this.game.host.y1, this.game.host.x2, this.game.host.y2, this.x1, this.y2, this.x1 - this.velocity.x, this.y2 - this.velocity.y, point))
                {
                    // Check if intersection point is within line segment
                    if(point.y > this.game.host.y1 && point.y < this.game.host.y2)
                    {
                        this.pos.x = point.x + 2;
                        this.pos.y = point.y - 2;
                        this.velocity.x = -this.velocity.x;
                        this.reflectYVelocity((this.pos.y - this.game.host.y1) / PADDLE_HEIGHT);
                        this.updateBoundingBox();
                        collided = true;
                    }
                }
            }

            // Perform collision with right paddle
            if(this.velocity.x > 0 && this.x2 > this.game.opponent.x1)
            {
                // Test the top right and bottom right corners
                boolean checkBottomCorner = true;
                Vector2f point = new Vector2f();
                if(Intersectionf.intersectLineLine(this.game.opponent.x1, this.game.opponent.y1, this.game.opponent.x1, this.game.opponent.y2, this.x2, this.y1, this.x2 - this.velocity.x, this.y1 - this.velocity.y, point))
                {
                    // Check if intersection point is within line segment
                    if(point.y > this.game.opponent.y1 && point.y < this.game.opponent.y2)
                    {
                        this.pos.x = point.x - 2;
                        this.pos.y = point.y + 2;
                        this.velocity.x = -this.velocity.x;
                        this.reflectYVelocity((this.pos.y - this.game.opponent.y1) / PADDLE_HEIGHT);
                        this.updateBoundingBox();
                        collided = true;
                        checkBottomCorner = false;
                    }
                }
                if(checkBottomCorner && Intersectionf.intersectLineLine(this.game.opponent.x1, this.game.opponent.y1, this.game.opponent.x1, this.game.opponent.y2, this.x2, this.y2, this.x2 - this.velocity.x, this.y2 - this.velocity.y, point))
                {
                    // Check if intersection point is within line segment
                    if(point.y >= this.game.opponent.y1 && point.y <= this.game.opponent.y2)
                    {
                        this.pos.x = point.x - 2;
                        this.pos.y = point.y - 2;
                        this.velocity.x = -this.velocity.x;
                        this.reflectYVelocity((this.pos.y - this.game.opponent.y1) / PADDLE_HEIGHT);
                        this.updateBoundingBox();
                        collided = true;
                    }
                }
            }

            return collided;
        }
    }

    protected static class Game
    {
        private final RandomSource random = RandomSource.create();
        private final Controller host;
        private Controller opponent;
        private Ball ball;
        private Difficulty difficulty;
        private boolean running;
        private boolean freezePaddles;
        private boolean finished;
        private int cooldown;

        private Game(PlayerController controller)
        {
            this.host = controller;
            controller.setSide(Side.LEFT);
            controller.setGame(this);
        }

        /**
         * @return The Controller that is hosting the game
         */
        public Controller getHost()
        {
            return this.host;
        }

        /**
         * @return True if the game is currently being played
         */
        public boolean isRunning()
        {
            return this.running;
        }

        /**
         * @return True if this game is finished and marked for removal
         */
        public boolean isFinished()
        {
            return this.finished;
        }

        /**
         * Sets the difficulty of the game. This affects the AI and the speed of the ball
         *
         * @param difficulty the difficulty. See {@link Difficulty} for options
         */
        public void setDifficulty(Difficulty difficulty)
        {
            this.difficulty = difficulty;
        }

        /**
         * Joins the given Controller as an opponent/second player of this game
         *
         * @param controller the controller to join
         */
        public void join(Controller controller)
        {
            if(this.opponent != null)
                return;

            controller.setGame(this);
            controller.setSide(Side.RIGHT);
            this.opponent = controller;
            this.startNewGame();
        }

        /**
         * Runs a game update
         */
        public void update()
        {
            if(!this.host.isPlaying() || (this.opponent != null && !this.opponent.isPlaying()))
            {
                this.finished = true;
                this.running = false;
                return;
            }

            if(this.running && !this.finished)
            {
                if(this.cooldown-- > 0)
                {
                    if(this.cooldown == 0)
                    {
                        this.resetBoard();
                    }
                    return;
                }

                this.ball.move();
                this.host.update();
                this.opponent.update();
                this.sendUpdateToPlayers(UpdateType.PADDLES);
                this.testForScore();
            }
        }

        /**
         * Starts a new game.
         */
        private void startNewGame()
        {
            Preconditions.checkNotNull(this.opponent, "Game cannot be started without an opponent present");
            this.ball = new Ball(this);
            this.host.moveToCenter();
            this.opponent.moveToCenter();
            this.host.score = 0;
            this.opponent.score = 0;
            this.running = true;
            this.finished = false;
            this.freezePaddles = true;
            this.cooldown = 20;
            this.sendUpdateToPlayers(UpdateType.BALL);
            this.sendUpdateToPlayers(UpdateType.PADDLES);
        }

        /**
         * Resets the game board
         */
        private void resetBoard()
        {
            Preconditions.checkNotNull(this.opponent, "Board cannot be reset without an opponent present");
            this.ball = new Ball(this);
            this.ball.randomVelocity();
            this.host.moveToCenter();
            this.opponent.moveToCenter();
            this.freezePaddles = false;
            this.sendUpdateToPlayers(UpdateType.BALL);
            this.sendUpdateToPlayers(UpdateType.PADDLES);
        }

        /**
         * Tests if the ball has scored and increments the score of the controller
         * that won the point.
         */
        private void testForScore()
        {
            if(this.ball.x1 <= 0)
            {
                this.opponent.sendEvent(EVENT_GAME_ROUND_WIN);
                this.host.sendEvent(EVENT_GAME_ROUND_LOSE);
                if(this.scoreAndCooldown(this.opponent))
                {
                    this.opponent.sendEvent(EVENT_GAME_WIN);
                    this.host.sendEvent(EVENT_GAME_LOSE);
                    this.finished = true;
                }
            }
            else if(this.ball.x2 >= BOARD_WIDTH)
            {
                this.host.sendEvent(EVENT_GAME_ROUND_WIN);
                this.opponent.sendEvent(EVENT_GAME_ROUND_LOSE);
                if(this.scoreAndCooldown(this.host))
                {
                    this.host.sendEvent(EVENT_GAME_WIN);
                    this.opponent.sendEvent(EVENT_GAME_LOSE);
                    this.finished = true;
                }
            }
        }

        /**
         * Increments the score of the given controller and starts a cooldown before
         * resetting the board.
         *
         * @param controller the controller that scored
         */
        private boolean scoreAndCooldown(Controller controller)
        {
            controller.score++;
            if(controller.score >= POINTS_TO_WIN)
            {
                return true;
            }
            this.freezePaddles = true;
            this.cooldown = RESET_COOLDOWN;
            return false;
        }

        /**
         * Sends an update to the connected players. The type determines the data that is
         * sent. This could be the paddle positions, ball position/velocity, score, etc.
         *
         * @param type the update type
         */
        private void sendUpdateToPlayers(UpdateType type)
        {
            Preconditions.checkNotNull(this.opponent, "Game updates cannot be sent without an opponent present");
            this.host.sendUpdate(type);
            this.opponent.sendUpdate(type);
        }

        private void sendEventToPlayers(byte event)
        {
            Preconditions.checkNotNull(this.opponent, "Game events cannot be sent without an opponent present");
            this.host.sendEvent(event);
            this.opponent.sendEvent(event);
        }

        public MessagePaddleBall.PaddlePosition createPaddlePositionMessage()
        {
            return new MessagePaddleBall.PaddlePosition(this.host.pos.y, this.opponent.pos.y);
        }

        public MessagePaddleBall.BallUpdate createBallUpdateMessage()
        {
            return new MessagePaddleBall.BallUpdate(this.ball.pos.x, this.ball.pos.y, this.ball.velocity.x, this.ball.velocity.y);
        }
    }

    public static class MatchmakingService implements IService
    {
        private final Queue<Game> pendingGames = new ArrayDeque<>();
        private final List<Game> activeGames = new ArrayList<>();

        /**
         * Creates a paddle ball game against an AI controller
         *
         * @param controller the host player
         * @return The game instance
         */
        protected Game createAiGame(PlayerController controller)
        {
            Game game = new Game(controller);
            game.setDifficulty(Difficulty.HARD);
            game.join(new AiController());
            this.activeGames.add(game);
            return game;
        }

        /**
         * Creates a paddle ball game against another real player. This will first attempt to
         * see if another player is waiting for a game and join that, otherwise the game
         * will be put into a queue.
         *
         * @param controller   the player creating the game
         * @return a game instance
         */
        protected Game createMultiplayerGame(PlayerController controller)
        {
            if(!this.pendingGames.isEmpty())
            {
                Game game = this.pendingGames.poll();
                game.join(controller);
                this.activeGames.add(game);
                return game;
            }
            Game game = new Game(controller);
            game.setDifficulty(Difficulty.HARD);
            this.pendingGames.offer(game);
            return game;
        }

        @Override
        public void tick()
        {
            this.pendingGames.removeIf(game -> !game.getHost().isPlaying());
            this.activeGames.removeIf(Game::isFinished);
            this.activeGames.forEach(Game::update);
        }
    }

    public enum Side
    {
        LEFT(PADDLE_WIDTH),
        RIGHT(BOARD_WIDTH - PADDLE_WIDTH * 2);

        private final int position;

        Side(int position)
        {
            this.position = position;
        }

        public int getPosition()
        {
            return this.position;
        }
    }

    public enum Difficulty
    {
        EASY(3.5F, 4.0F, 5.0F),
        MEDIUM(4.0F, 8.0F, 6.0F),
        HARD(5.0F, 15.0F, 8.0F);

        private final float aiPaddleSpeed;
        private final float aiTolerance;
        private final float ballYSpeed;

        Difficulty(float aiPaddleSpeed, float aiTolerance, float ballYSpeed)
        {
            this.aiPaddleSpeed = aiPaddleSpeed;
            this.aiTolerance = aiTolerance;
            this.ballYSpeed = ballYSpeed;
        }
    }

    public enum State
    {
        MAIN_MENU,
        PENDING,
        IN_GAME
    }

    public enum Action
    {
        JOIN_GAME,
        UPDATE_STATE,
        INPUT
    }

    public enum UpdateType
    {
        BALL, PADDLES;

        public boolean is(UpdateType type)
        {
            return this == type;
        }
    }
}
