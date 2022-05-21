import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;

import java.util.ArrayList;


public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Paddle paddle;
    private Counter counter;
    private BlockRemover blockRemover;
    private final int INITIAL_BLOCKS = 57;

    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        this.counter = new Counter();
        this.counter.increase(INITIAL_BLOCKS);
        this.blockRemover = new BlockRemover(this, counter);
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

        Ball ball = new Ball(new Point(100, 100), 5, Color.black);
        ball.setVelocity(3, 4);


        Ball ball2 = new Ball(new Point(400, 57), 6, Color.gray);
        ball2.setVelocity(4, 4);

        Block upperSide = new Block(new Rectangle(new Point(0, 0), 800, 50), Color.gray);
        Block leftSide = new Block(new Rectangle(new Point(0, 50), 50, 550), Color.gray);
        Block lowerSide = new Block(new Rectangle(new Point(50, 550), 700, 50), Color.gray);
        Block rightSide = new Block(new Rectangle(new Point(750, 50), 50, 550), Color.gray);

        upperSide.addToGame(this);
        leftSide.addToGame(this);
        lowerSide.addToGame(this);
        rightSide.addToGame(this);

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
            }
        }
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
    }

    // Run the game -- start the animation loop.
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (counter.getValue() > 0) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.orange);
            d.fillRectangle(0, 0, 800, 600);

            this.paddle.addToGame(this);

            this.sprites.drawAllOn(d);
            this.paddle.drawOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            this.paddle.timePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
        gui.close();
    }


    public void removeCollidable(Collidable c) {
        this.environment.getCollidableList().remove(c);
    }

    public void removeSprite(Sprite s) {
        this.sprites.getSpriteList().remove(s);
    }

    public Counter getCounter() {
        return this.counter;
    }
}
