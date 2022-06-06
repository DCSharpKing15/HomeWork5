import biuoop.DrawSurface;

import java.awt.*;

public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private final long START_TIME = System.currentTimeMillis(); // timing

    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.gameScreen = gameScreen;
        this.countFrom = countFrom;
        this.numOfSeconds = numOfSeconds;
    }

    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.orange);
        d.fillRectangle(0, 0, 800, 600);
        this.gameScreen.drawAllOn(d);
        d.drawText(350, 60, "" + (countFrom - usedSeconds()), 40);
    }

    public boolean shouldStop() {
        if (usedSeconds() > this.countFrom)
            return true;
        return false;
    }

    public int usedSeconds() {
        int usedTime = (int) ((System.currentTimeMillis() - START_TIME) * (numOfSeconds / countFrom));
        return usedTime;
    }
}
