import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;


public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Paddle paddle;

    private Counter blockCounter;
    private BlockRemover blockRemover;
    private int initialBlocks;

    private Counter ballCounter;
    private BallRemover ballRemover;
    private int initialBalls;

    private Counter scoreCounter;
    private ScoreTrackingListener scoreTrackingListener;

    private final int SCORE_PER_HIT = 5;

    private AnimationRunner runner;
    private boolean running;

    private KeyboardSensor keyboard;

    private final int COUNT_FROM = 3;
    private final double NUM_OF_SECONDS = 0.005;

    private LevelInformation levelInformation;

    public GameLevel(LevelInformation levelInformation, GUI gui) {
        this.gui = gui;

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        this.initialBalls = levelInformation.numberOfBalls();
        this.initialBlocks = levelInformation.blocks().size() - 6;

        this.blockCounter = new Counter();
        this.blockCounter.increase(this.initialBlocks);
        this.blockRemover = new BlockRemover(this, this.blockCounter);

        this.ballCounter = new Counter();
        this.ballCounter.increase(this.initialBalls);
        this.ballRemover = new BallRemover(this, this.ballCounter);

        this.scoreCounter = new Counter();
        this.scoreTrackingListener = new ScoreTrackingListener(this.scoreCounter);

        this.levelInformation = levelInformation;
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
        this.sleeper = new Sleeper();
        this.runner = new AnimationRunner(this.gui, this.sleeper);
        this.keyboard = this.gui.getKeyboardSensor();
        this.paddle = new Paddle(this.gui, this.levelInformation.paddleWidth(), this.levelInformation.paddleSpeed());

        if (this.levelInformation.levelName().equals("Direct Hit")) {
            Ball ball = new Ball(new Point(400, 500), 4, Color.white);
            ball.setVelocity(this.levelInformation.initialBallVelocities().get(0));

            ball.setStartPerimeterX(50);
            ball.setStartPerimeterY(50);
            ball.setEndPerimeterX(750);
            ball.setEndPerimeterY(600);

            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
        } else {
            Ball ball;
            for (int i = 0; i < this.initialBalls; i++) {
                Random random = new Random();
                int x = random.nextInt(this.paddle.getWidth() + 1) + (800 - this.paddle.getWidth()) / 2;
                Point p = new Point(x, 520);
                ball = new Ball(p, 5, Color.white);
                ball.setVelocity(this.levelInformation.initialBallVelocities().get(i));

                ball.setStartPerimeterX(50);
                ball.setStartPerimeterY(50);
                ball.setEndPerimeterX(750);
                ball.setEndPerimeterY(600);

                ball.setGameEnvironment(this.environment);
                ball.addToGame(this);
            }

        }
        this.paddle.addToGame(this);

        this.levelInformation.blocks().get(4).addHitListener(this.ballRemover);

        for (int i = 6; i < this.initialBlocks + 6; i++) {
            this.levelInformation.blocks().get(i).addHitListener(this.blockRemover);
            this.levelInformation.blocks().get(i).addHitListener(this.scoreTrackingListener);
        }

        for (int i = 0; i < this.levelInformation.blocks().size(); i++) {
            this.levelInformation.blocks().get(i).addToGame(this);
        }

        ScoreIndicator scoreIndicator = new ScoreIndicator(this.levelInformation.blocks().get(5), this);
        scoreIndicator.addToGame(this);

        NameIndicator nameIndicator = new NameIndicator(this.levelInformation.blocks().get(5), this);
        nameIndicator.addToGame(this);

        return;
    }

    // Run the game -- start the animation loop.
    public void run() {
        this.runner.run(new CountdownAnimation(NUM_OF_SECONDS, COUNT_FROM, this.sprites)); // countdown before turn starts.
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.running = true;
        this.runner.run(this);
        //gui.close();
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

    public int getInitialBlocks() {
        return this.initialBlocks;
    }

    public Counter getBallCounter() {
        return this.ballCounter;
    }

    public int getInitialBalls() {
        return this.initialBalls;
    }

    public LevelInformation getLevelInformation() {
        return this.levelInformation;
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
        if (this.levelInformation.levelName().equals("Direct Hit")) {
            d.setColor(Color.black);
            d.fillRectangle(50, 50, 700, 500);
            drawBackground1(d);
        } else {
            d.setColor(Color.white);
            d.fillRectangle(50, 50, 700, 500);
            drawBackGround(d);
        }

        this.paddle.addToGame(this);

        this.sprites.drawAllOn(d);
        this.paddle.drawOn(d);

        this.sprites.notifyAllTimePassed();
        this.paddle.timePassed();

        this.scoreCounter.decrease(this.scoreCounter.getValue());
        this.scoreCounter.increase((this.initialBlocks - this.blockCounter.getValue()) * this.SCORE_PER_HIT);

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

    public void drawBackground1(DrawSurface d) {
        d.setColor(Color.blue);
        d.drawCircle(400, 165, 65);
        d.drawCircle(400, 165, 90);
        d.drawCircle(400, 165, 115);
        d.drawLine(400, 50, 400, 140);
        d.drawLine(425, 165, 545, 165);
        d.drawLine(400, 190, 400, 310);
        d.drawLine(375, 165, 255, 165);
    }

    public void drawBackGround(DrawSurface d) {
        d.setColor(Color.yellow);
        for (int i = 0; i < 100; i++) {
            d.drawLine(200, 150, 7 * i, 300);
        }
        d.setColor(new Color(245, 229, 185));
        d.fillCircle(200, 150, 60);
        d.setColor(new Color(234, 218, 149));
        d.fillCircle(200, 150, 50);
        d.setColor(new Color(255, 231, 28));
        d.fillCircle(200, 150, 40);
    }
}
