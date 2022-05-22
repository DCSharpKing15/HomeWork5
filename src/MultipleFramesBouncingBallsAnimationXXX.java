import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimationXXX {

    private final static int startPerimeterX1 = 50;
    private final static int endPerimeterX1 = 500;
    private final static int startPerimeterY1 = 50;
    private final static int endPerimeterY1 = 500;

    private final static int startPerimeterX2 = 450;
    private final static int endPerimeterX2 = 600;
    private final static int startPerimeterY2 = 450;
    private final static int endPerimeterY2 = 600;

    // randomBallGenerator -- generates a random ball
    public static Ball randomBallGenerator(int r, int whichPart) {
        Random rand = new Random();
        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        int x = 0;
        int y = 0;
        if (whichPart == 1) {
            x = rand.nextInt(endPerimeterY1 - r - (startPerimeterY1 + r + 1)) + startPerimeterY1 + r + 1;
            //x = rand.nextInt(startPerimeterX1 + r + 1, endPerimeterX1 - r);
            y = rand.nextInt(endPerimeterY1 - r - (startPerimeterY1 + r + 1)) + startPerimeterY1 + r + 1;
            //y = rand.nextInt(startPerimeterY1 + r + 1, endPerimeterY1 - r);
        } else {
            x = rand.nextInt(endPerimeterX2 - r - (startPerimeterX2 + r + 1)) + startPerimeterX2 + r + 1;
            //x = rand.nextInt(startPerimeterX2 + r + 1, endPerimeterX2 - r);
            y = rand.nextInt(endPerimeterY2 - r - (startPerimeterY2 + r + 1)) + startPerimeterY2 + r + 1;
            //y = rand.nextInt(startPerimeterY2 + r + 1, endPerimeterY2 - r);
        }
        double angle = rand.nextInt(360);
        double speed;
        if (r > 50) {
            speed = 1;
        } else {
            speed = (double) 50 / r;
        }
        Ball ball = new Ball(new Point(x, y), r, color);
        ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));//<-why do I need Velocity., fromAngle... is a static
        return ball;
    }

    //draws an animation of a ball
    static private void drawAnimation(Ball[] balls) {
        GUI gui = new GUI("title", 700, 700);
        Sleeper sleeper = new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.gray);
            d.fillRectangle(startPerimeterX1, startPerimeterY1, endPerimeterX1 - startPerimeterX1, endPerimeterY1 - startPerimeterY1);
            d.setColor(Color.yellow);
            d.fillRectangle(startPerimeterX2, startPerimeterY2, endPerimeterX2 - startPerimeterX2, endPerimeterY2 - startPerimeterY2);
            for (int i = 0; i < balls.length; i++) {
                balls[i].moveOneStep();
                balls[i].drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(10);  // wait for 50 milliseconds.
        }
    }

    public static void main(String[] args) {

        Ball[] balls = new Ball[args.length];
        for (int i = 0; i < balls.length; i++) {
            if (i < balls.length / 2) {
                balls[i] = randomBallGenerator(Integer.parseInt(args[i]), 1);
                balls[i].setStartPerimeterX(startPerimeterX1);
                balls[i].setEndPerimeterX(endPerimeterX1);
                balls[i].setStartPerimeterY(startPerimeterY1);
                balls[i].setEndPerimeterY(endPerimeterY1);
            } else {
                balls[i] = randomBallGenerator(Integer.parseInt(args[i]), 0);
                balls[i].setStartPerimeterX(startPerimeterX2);
                balls[i].setEndPerimeterX(endPerimeterX2);
                balls[i].setStartPerimeterY(startPerimeterY2);
                balls[i].setEndPerimeterY(endPerimeterY2);
            }
        }
        drawAnimation(balls);
    }
}
