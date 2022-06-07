import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelOne implements LevelInformation {
    private int initialNumberOfBalls;

    private List<Velocity> initialBallVelocities;

    private final int PADDLE_SPEED = 1;
    private int PADDLE_WIDTH = 100;

    private final String LEVEL_NAME = "Direct Hit";

    private Sprite background;

    private List<Block> blocks;
    private int numberOfBlocksToRemove;

    private final Color BACKGROUND_COLOR = Color.black;

    public LevelOne() {
        this.initialBallVelocities = new ArrayList<Velocity>();
        this.initialBallVelocities.add(Velocity.fromAngleAndSpeed(-Math.PI / 2, 5));

        Block upperSide = new Block(new Rectangle(new Point(0, 0), 800, 50), Color.gray);
        Block leftSide = new Block(new Rectangle(new Point(0, 50), 50, 550), Color.gray);
        Block lowerSide = new Block(new Rectangle(new Point(50, 570), 700, 50), Color.gray);
        Block rightSide = new Block(new Rectangle(new Point(750, 50), 50, 550), Color.gray);

        Block deathRegion = new Block(new Rectangle(new Point(50, 550), 700, 50), Color.black);

        Block indicator = new Block(new Rectangle(new Point(0, 0), 800, 30), Color.white);

        Rectangle rectangle = new Rectangle(new Point(385, 150), 30, 30);
        Block block = new Block(rectangle, Color.red);

        this.blocks = new ArrayList<Block>();

        this.blocks.add(upperSide);
        this.blocks.add(leftSide);
        this.blocks.add(lowerSide);
        this.blocks.add(rightSide);
        this.blocks.add(deathRegion);
        this.blocks.add(indicator);
        this.blocks.add(block);

        this.background = new Block(new Rectangle(new Point(50, 50), 500, 700), this.BACKGROUND_COLOR);
    }

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }

    @Override
    public int paddleSpeed() {
        return this.PADDLE_SPEED;
    }

    @Override
    public int paddleWidth() {
        return this.PADDLE_WIDTH;
    }

    @Override
    public String levelName() {
        return this.LEVEL_NAME;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }

    public Color getBACKGROUND_COLOR() {
        return this.BACKGROUND_COLOR;
    }
}
