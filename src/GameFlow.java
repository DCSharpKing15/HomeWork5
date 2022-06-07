import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.*;
import java.util.List;

public class GameFlow {
    private GUI gui;
    private int scoreCount;

    public GameFlow(GUI gui) {
        this.gui = gui;
        this.scoreCount = 0;
    }

    public void runLevels(List<LevelInformation> levels) {
        DrawSurface d = this.gui.getDrawSurface();
        KeyboardSensor keyboardSensor = this.gui.getKeyboardSensor();
        boolean becauseOfBalls = false;
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.gui);
            level.initialize();
            level.run();
            if (level.getLevelInformation().numberOfBlocksToRemove() == 0) {
                if (level.getLevelInformation().levelName().equals("Direct Hit")) {
                    scoreCount += 105;
                } else if (level.getLevelInformation().levelName().equals("Wide Easy")) {
                    scoreCount += 175;
                } else if (level.getLevelInformation().levelName().equals("Wide Medium")) {
                    scoreCount += 250;
                } else {
                    scoreCount += 325;
                }
            } else {
                becauseOfBalls = true;
                if (level.getLevelInformation().levelName().equals("Wide Easy")) {
                    scoreCount += (15 - level.getLevelInformation().numberOfBlocksToRemove()) * 5;
                } else if (level.getLevelInformation().levelName().equals("Wide Medium")) {
                    scoreCount += (30 - level.getLevelInformation().numberOfBlocksToRemove()) * 5;
                } else if (level.getLevelInformation().levelName().equals("Wide Hard")) {
                    scoreCount += (45 - level.getLevelInformation().numberOfBlocksToRemove()) * 5;
                }
                while (!keyboardSensor.isPressed(KeyboardSensor.ENTER_KEY)) {
                    long startTime = System.currentTimeMillis(); // timing
                    d.setColor(Color.white);
                    d.fillRectangle(0, 0, 800, 600);
                    d.setColor(Color.black);
                    d.drawText(390, 60, "Game Over. Your score is " + scoreCount, 30);

                    gui.show(d);

                    long usedTime = System.currentTimeMillis() - startTime;
                    long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
                    if (milliSecondLeftToSleep > 0) {
                        sleeper.sleepFor(milliSecondLeftToSleep);
                    }
                }
                break;
            }
        }

        while (!keyboardSensor.isPressed(KeyboardSensor.ENTER_KEY) && !becauseOfBalls) {
            long startTime = System.currentTimeMillis(); // timing

            d.setColor(Color.white);
            d.fillRectangle(0, 0, 800, 600);
            d.setColor(Color.black);
            d.drawText(390, 60, "You Win! Your score is " + scoreCount, 30);

            gui.show(d);

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
        this.gui.close();
    }
}
