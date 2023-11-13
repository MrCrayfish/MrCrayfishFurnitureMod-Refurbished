package com.mrcrayfish.furniture.refurbished.computer.app;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.computer.IService;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTennisGame;
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
public class TennisGame extends Program
{
    public static final MatchmakingService SERVICE = new MatchmakingService();
    public static final int BOARD_WIDTH = 200;
    public static final int BOARD_HEIGHT = 100;
    public static final int PADDLE_WIDTH = 4;
    public static final int PADDLE_HEIGHT = 28;
    public static final float PADDLE_SPEED = 4.0F;
    public static final int RESET_COOLDOWN = 40;

    private Game activeGame;
    private State state;
    private PlayerController controller;

    public TennisGame(ResourceLocation id, IComputer computer)
    {
        super(id, computer);
        this.state = State.MAIN_MENU;
    }

    @Override
    public void tick()
    {
        if(this.computer.isServer() && this.activeGame != null)
        {
            if(this.computer.getUser() == null)
            {
                this.activeGame = null;
                this.controller = null;
                return;
            }
        }
    }

    /**
     *
     * @param action
     * @param data
     */
    public void update(Action action, byte data)
    {
        if(action == Action.JOIN_GAME && this.activeGame == null)
        {
            if(data == 0)
            {
                this.controller = new PlayerController(this.computer.getUser(), this);
                this.activeGame = SERVICE.createAiGame(this.controller);
                this.state = State.IN_GAME;
            }
            else if(data == 1)
            {
                this.controller = new PlayerController(this.computer.getUser(), this);
                this.activeGame = SERVICE.createMultiplayerGame(this.controller);
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
         *
         */
        protected void updateBoundingBox()
        {
            this.updateBoundingBox(this.pos.x, this.pos.y);
        }

        /**
         *
         * @param x
         * @param y
         */
        protected void updateBoundingBox(float x, float y)
        {
            this.x1 = x;
            this.y1 = y;
            this.x2 = x + this.width;
            this.y2 = y + this.height;
        }

        /**
         *
         * @param game
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
         *
         * @return
         */
        protected abstract boolean isConnected();

        /**
         *
         */
        protected abstract void update();

        /**
         *
         * @param side
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
         *
         */
        public void moveToCenter()
        {
            this.pos = new Vector2f(this.side.getPosition(), (BOARD_HEIGHT - PADDLE_HEIGHT) / 2.0F);
            this.updateBoundingBox();
        }

        public void sendUpdate(UpdateType type) {}
    }

    protected static class PlayerController extends Controller
    {
        protected final Player player;
        protected final TennisGame program;
        protected boolean inputUp;
        protected boolean inputDown;

        private PlayerController(Player player, TennisGame program)
        {
            this.player = player;
            this.program = program;
        }

        /**
         *
         * @param inputUp
         */
        public void setInputUp(boolean inputUp)
        {
            this.inputUp = inputUp;
        }

        /**
         *
         * @param inputDown
         */
        public void setInputDown(boolean inputDown)
        {
            this.inputDown = inputDown;
        }

        @Override
        public boolean isConnected()
        {
            IComputer computer = this.program.getComputer();
            return this.player.equals(computer.getUser()) && computer.getProgram() == this.program && this.program.activeGame == this.game;
        }

        @Override
        public void update()
        {
            if(!this.game.freezePaddles)
            {
                if(this.inputUp)
                {
                    this.pos.y = Math.max(0, this.pos.y - PADDLE_SPEED);
                    this.updateBoundingBox();
                }
                else if(this.inputDown)
                {
                    this.pos.y = Math.min(BOARD_HEIGHT - PADDLE_HEIGHT, this.pos.y + PADDLE_SPEED);
                    this.updateBoundingBox();
                }
            }
        }

        @Override
        public void sendUpdate(UpdateType type)
        {
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
            if(UpdateType.SCORE.is(type))
            {
                ServerPlayer player = (ServerPlayer) this.player;
                Network.getPlay().sendToPlayer(() -> player, this.game.createScoreMessage());
            }
        }
    }

    protected static class AiController extends Controller
    {
        public final float paddleSpeed;
        public final float tolerance;

        public AiController(float speed, float tolerance)
        {
            this.paddleSpeed = speed;
            this.tolerance = tolerance;
        }

        @Override
        public boolean isConnected()
        {
            return true;
        }

        @Override
        public void update()
        {
            if(!this.game.freezePaddles && this.game.ball.velocity.x > 0)
            {
                if(this.game.ball.pos.y < this.pos.y + (PADDLE_HEIGHT / 2F) - this.tolerance)
                {
                    this.pos.y -= Math.min(this.paddleSpeed, this.pos.y + (PADDLE_HEIGHT / 2F) - this.game.ball.pos.y);
                }
                else if(this.game.ball.pos.y > this.pos.y + (PADDLE_HEIGHT / 2F) + this.tolerance)
                {
                    this.pos.y += Math.min(this.paddleSpeed, this.game.ball.pos.y - this.pos.y + (PADDLE_HEIGHT / 2F));
                }
                this.pos.y = Mth.clamp(this.pos.y, 0, BOARD_HEIGHT - PADDLE_HEIGHT);
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
            this.pos = new Vector2f(BOARD_WIDTH / 2.0F, (BOARD_HEIGHT - PADDLE_HEIGHT) / 2.0F);
            this.updateBoundingBox();
        }

        /**
         *
         */
        public void randomVelocity()
        {
            this.velocity.x = 8;
            this.reflectYVelocity(this.game.random.nextFloat());
        }

        /**
         *
         */
        public void move()
        {
            this.pos.add(this.velocity);
            this.updateBoundingBox();
            if(this.performCollision())
            {
                this.game.sendUpdateToPlayers(UpdateType.BALL);
            }
        }

        @Override
        protected void updateBoundingBox()
        {
            this.updateBoundingBox(this.pos.x - 2, this.pos.y - 2);
        }

        /**
         *
         * @param hit
         */
        public void reflectYVelocity(float hit)
        {
            hit = Math.clamp(hit, 0.0F, 1.0F);
            hit = (hit - 0.5F) * 2F;
            this.velocity.y = 5F * hit;
        }

        /**
         *
         * @return
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
         *
         * @return
         */
        public Controller getHost()
        {
            return this.host;
        }

        /**
         *
         * @param controller
         */
        public void join(Controller controller)
        {
            controller.setGame(this);
            controller.setSide(Side.RIGHT);
            this.opponent = controller;
            this.startNewGame();
        }

        /**
         *
         * @return
         */
        public boolean isRunning()
        {
            return this.running;
        }

        /**
         *
         * @return
         */
        public boolean isFinished()
        {
            return this.finished;
        }

        /**
         *
         */
        public void update()
        {
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

                if(!this.host.isConnected() || !this.opponent.isConnected())
                {
                    this.finished = true;
                    this.running = false;
                    return;
                }

                this.host.update();
                this.opponent.update();
                this.sendUpdateToPlayers(UpdateType.PADDLES);
                this.ball.move();
                this.testForScore();
            }
        }

        /**
         * Starts a new game.
         */
        private void startNewGame()
        {
            this.resetBoard();
            this.host.score = 0;
            this.opponent.score = 0;
            this.running = true;
            this.finished = false;
            this.freezePaddles = true;
            this.cooldown = 20;
        }

        /**
         * Resets the game board
         */
        private void resetBoard()
        {
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
                this.scoreAndCooldown(this.opponent);
                return;
            }
            if(this.ball.x2 >= BOARD_WIDTH)
            {
                this.scoreAndCooldown(this.host);
            }
        }

        /**
         * Increments the score of the given controller and starts a cooldown before
         * resetting the board.
         *
         * @param controller the controller that scored
         */
        private void scoreAndCooldown(Controller controller)
        {
            controller.score++;
            this.freezePaddles = true;
            this.cooldown = RESET_COOLDOWN;
            this.sendUpdateToPlayers(UpdateType.SCORE);
        }

        /**
         * Sends an update to the connected players. The type determines the data that is
         * sent. This could be the paddle positions, ball position/velocity, score, etc.
         *
         * @param type the update type
         */
        private void sendUpdateToPlayers(UpdateType type)
        {
            this.host.sendUpdate(type);
            this.opponent.sendUpdate(type);
        }

        public MessageTennisGame.PaddlePosition createPaddlePositionMessage()
        {
            return new MessageTennisGame.PaddlePosition(this.host.pos.y, this.opponent.pos.y);
        }

        public MessageTennisGame.BallUpdate createBallUpdateMessage()
        {
            return new MessageTennisGame.BallUpdate(this.ball.pos.x, this.ball.pos.y, this.ball.velocity.x, this.ball.velocity.y);
        }

        public MessageTennisGame.Score createScoreMessage()
        {
            return new MessageTennisGame.Score(this.host.score, this.opponent.score);
        }
    }

    public static class MatchmakingService implements IService
    {
        private final Queue<Game> pendingGames = new ArrayDeque<>();
        private final List<Game> activeGames = new ArrayList<>();

        /**
         * Creates a tennis game against an AI controller
         *
         * @param controller the host player
         * @return The game instance
         */
        protected Game createAiGame(PlayerController controller)
        {
            Game game = new Game(controller);
            game.join(new AiController(4F, 8F));
            this.activeGames.add(game);
            return game;
        }

        /**
         * Creates a tennis game against another real player. This will first attempt to
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
            this.pendingGames.offer(game);
            return game;
        }

        @Override
        public void tick()
        {
            this.pendingGames.removeIf(game -> !game.getHost().isConnected());
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
        BALL, PADDLES, SCORE;

        public boolean is(UpdateType type)
        {
            return this == type;
        }
    }
}
