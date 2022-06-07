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
        if (this.gameScreen.getSpriteList().size() == 10) {
            d.setColor(Color.black);
            d.fillRectangle(50, 50, 700, 500);
            drawBackground1(d);
        } else {
            d.setColor(Color.white);
            d.fillRectangle(50, 50, 700, 500);
            drawBackground(d);
        }
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.red);
        d.drawText(390, 60, "" + (countFrom - usedSeconds()), 30);
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

    public void drawBackground(DrawSurface d) {
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
