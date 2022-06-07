import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelTwo implements LevelInformation {
    private final int INITIAL_NUMBER_OF_BALLS = 10;

    private List<Velocity> initialBallVelocities;

    private final int PADDLE_SPEED = 1;
    private int PADDLE_WIDTH = 600;

    private final String LEVEL_NAME = "Wide Easy";

    private Sprite background;

    private List<Block> blocks;

    private final Color BACKGROUND_COLOR = Color.white;

    private int numberOfBlocksToRemove;

    public LevelTwo() {
        Random random = new Random();
        this.initialBallVelocities = new ArrayList<Velocity>();
        int angle;
        int speed = 5;
        for (int i = 0; i < this.INITIAL_NUMBER_OF_BALLS; i++) {
            angle = random.nextInt(181);
            this.initialBallVelocities.add(Velocity.fromAngleAndSpeed(-angle * Math.PI / 180, speed));
        }

        Block upperSide = new Block(new Rectangle(new Point(0, 0), 800, 50), Color.gray);
        Block leftSide = new Block(new Rectangle(new Point(0, 50), 50, 550), Color.gray);
        Block lowerSide = new Block(new Rectangle(new Point(50, 570), 700, 50), Color.gray);
        Block rightSide = new Block(new Rectangle(new Point(750, 50), 50, 550), Color.gray);

        Block deathRegion = new Block(new Rectangle(new Point(50, 550), 700, 50), Color.white);

        Block indicator = new Block(new Rectangle(new Point(0, 0), 800, 30), Color.white);

        this.blocks = new ArrayList<Block>();

        this.blocks.add(upperSide);
        this.blocks.add(leftSide);
        this.blocks.add(lowerSide);
        this.blocks.add(rightSide);
        this.blocks.add(deathRegion);
        this.blocks.add(indicator);

        this.background = new Block(new Rectangle(new Point(50, 50), 500, 700), this.BACKGROUND_COLOR);

        Block block;
        Color color;
        int atMiddle = 0;
        int afterMiddle = 0;
        for (int i = 0; i < 15; i++) {
            if (i < 2) {
                color = Color.red;
            } else if (i < 4) {
                color = Color.orange;
            } else if (i < 6) {
                color = Color.yellow;
            } else if (i < 9) {
                color = Color.green;
                if (i == 7) {
                    atMiddle = 10;
                }
                if (i == 8) {
                    atMiddle = 0;
                    afterMiddle = 10;
                }
            } else if (i < 11) {
                color = Color.blue;
            } else if (i < 13) {
                color = Color.pink;
            } else {
                color = Color.cyan;
            }

            block = new Block(new Rectangle(new Point(50 + 46 * i + afterMiddle, 300), 46 + atMiddle, 25), color);
            this.blocks.add(block);
        }
        this.numberOfBlocksToRemove = this.blocks.size();
    }

    @Override
    public int numberOfBalls() {
        return this.INITIAL_NUMBER_OF_BALLS;
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
        return this.numberOfBlocksToRemove;
    }

    public Color getBACKGROUND_COLOR() {
        return this.BACKGROUND_COLOR;
    }

    public void setCurrentNumOfBlocksToRemove(int numberOfBlocksToRemove) {
        this.numberOfBlocksToRemove = numberOfBlocksToRemove;
    }

}
