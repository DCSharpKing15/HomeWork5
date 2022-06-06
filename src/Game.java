import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.*;

import java.util.ArrayList;


public class Game implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Paddle paddle;

    private Counter blockCounter;
    private BlockRemover blockRemover;
    private final int INITIAL_BLOCKS = 57;

    private Counter ballCounter;
    private BallRemover ballRemover;
    private final int INITIAL_BALLS = 3;

    private Counter scoreCounter;
    private ScoreTrackingListener scoreTrackingListener;

    private final int SCORE_PER_HIT = 5;

    private AnimationRunner runner;
    private boolean running;

    private KeyboardSensor keyboard;

    private final int COUNT_FROM = 3;
    private final double NUM_OF_SECONDS = 0.005;

    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        this.blockCounter = new Counter();
        this.blockCounter.increase(this.INITIAL_BLOCKS);
        this.blockRemover = new BlockRemover(this, this.blockCounter);

        this.ballCounter = new Counter();
        this.ballCounter.increase(this.INITIAL_BALLS);
        this.ballRemover = new BallRemover(this, this.ballCounter);

        this.scoreCounter = new Counter();
        this.scoreTrackingListener = new ScoreTrackingListener(this.scoreCounter);
    }

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    public GameEnvironment getEnvironment() {
        return environment;
    }

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {

        this.gui = new GUI("Arkanoid", 800, 600);
        this.sleeper = new Sleeper();
        this.paddle = new Paddle(this.gui);
        this.runner = new AnimationRunner(this.gui, this.sleeper);
        this.keyboard = this.gui.getKeyboardSensor();

        Ball ball = new Ball(new Point(200, 400), 4, Color.black);
        ball.setVelocity(-3, -4);


        Ball ball2 = new Ball(new Point(400, 60), 4, Color.blue);
        ball2.setVelocity(4, 4);

        Ball ball3 = new Ball(new Point(200, 500), 4, Color.green);
        ball3.setVelocity(3, -4);

        Block upperSide = new Block(new Rectangle(new Point(0, 0), 800, 50), Color.gray);
        Block leftSide = new Block(new Rectangle(new Point(0, 50), 50, 550), Color.gray);
        Block lowerSide = new Block(new Rectangle(new Point(50, 570), 700, 50), Color.gray);
        Block rightSide = new Block(new Rectangle(new Point(750, 50), 50, 550), Color.gray);

        upperSide.addToGame(this);
        leftSide.addToGame(this);
        lowerSide.addToGame(this);
        rightSide.addToGame(this);

        Block deathRegion = new Block(new Rectangle(new Point(50, 550), 700, 20), Color.black);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(ballRemover);

        Color color;
        Block block;
        Rectangle rectangle;

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                color = Color.gray;
            } else if (i == 1) {
                color = Color.red;
            } else if (i == 2) {
                color = Color.yellow;
            } else if (i == 3) {
                color = Color.blue;
            } else if (i == 4) {
                color = Color.pink;
            } else {
                color = Color.green;
            }
            for (int j = 0; j < 12 - i; j++) {
                rectangle = new Rectangle(new Point(165 + 45 * (i + j + 1), 100 + 30 * (i + 1)), 45, 30);
                block = new Block(rectangle, color);
                block.addToGame(this);

                block.addHitListener(this.blockRemover);
                block.addHitListener(this.scoreTrackingListener);
            }
        }
        block = new Block(new Rectangle(new Point(0, 0), 800, 30), Color.white);
        ScoreIndicator scoreIndicator = new ScoreIndicator(block, this);
        scoreIndicator.addToGame(this);

        ball.setStartPerimeterX(50);
        ball.setStartPerimeterY(50);
        ball.setEndPerimeterX(750);
        ball.setEndPerimeterY(550);

        ball.setGameEnvironment(this.environment);
        ball.addToGame(this);


        ball2.setStartPerimeterX(50);
        ball2.setStartPerimeterY(50);
        ball2.setEndPerimeterX(750);
        ball2.setEndPerimeterY(550);

        ball2.setGameEnvironment(this.environment);
        ball2.addToGame(this);

        ball3.setStartPerimeterX(50);
        ball3.setStartPerimeterY(50);
        ball3.setEndPerimeterX(750);
        ball3.setEndPerimeterY(550);

        ball3.setGameEnvironment(this.environment);
        ball3.addToGame(this);
    }

    // Run the game -- start the animation loop.
    public void run() {
        //this.createBallsOnTopOfPaddle(); // or a similar method<---------------------------------do that later
        this.runner.run(new CountdownAnimation(NUM_OF_SECONDS, COUNT_FROM, this.sprites)); // countdown before turn starts.
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.running = true;
        this.runner.run(this);
        gui.close();
        return;
    }

    public void removeCollidable(Collidable c) {
        this.environment.getCollidableList().remove(c);
    }

    public void removeSprite(Sprite s) {
        this.sprites.getSpriteList().remove(s);
    }

    public Counter getBlockCounter() {
        return this.blockCounter;
    }

    public int getINITIAL_BLOCKS() {
        return this.INITIAL_BLOCKS;
    }

    public Counter getBallCounter() {
        return this.ballCounter;
    }

    public int getINITIAL_BALLS() {
        return this.INITIAL_BALLS;
    }

    public Counter getScoreCounter() {
        return this.scoreCounter;
    }

    public boolean shouldStop() {
        return !this.running;
    }

    public void doOneFrame(DrawSurface d) {
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }

        d.setColor(Color.orange);
        d.fillRectangle(0, 0, 800, 600);

        this.paddle.addToGame(this);

        this.sprites.drawAllOn(d);
        this.paddle.drawOn(d);

        this.sprites.notifyAllTimePassed();
        this.paddle.timePassed();

        this.scoreCounter.decrease(this.scoreCounter.getValue());
        this.scoreCounter.increase((this.INITIAL_BLOCKS - this.blockCounter.getValue()) * this.SCORE_PER_HIT);

        if (this.blockCounter.getValue() == 0) {
            this.running = false;
            this.scoreCounter.increase(100);
            this.sleeper.sleepFor(100);
        } else if (this.ballCounter.getValue() == 0) {
            this.running = false;
        } else {
            this.running = true;
        }
    }
}
