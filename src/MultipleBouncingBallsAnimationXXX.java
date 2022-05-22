import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

public class MultipleBouncingBallsAnimationXXX {

    private final static int perimeterX = 600;
    private final static int perimeterY = 600;

    // randomBallGenerator -- generates a random ball
    public static Ball randomBallGenerator(int r) {
        Random rand = new Random();
        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        int x = rand.nextInt(1 + perimeterX - r);
        int y = rand.nextInt(1 + perimeterY - r);
        double angle = rand.nextInt(360);
        double speed;
        if (r > 50) {
            speed = 0.2;
        } else {
            speed = (double) 10 / r;
        }
        Ball ball = new Ball(new Point(x, y), r, color);
        ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));//<-why do I need Velocity., fromAngle... is a static
        return ball;
    }

    //draws an animation of a ball
    static private void drawAnimation(Ball[] balls) {
        GUI gui = new GUI("title", perimeterX, perimeterY);
        Sleeper sleeper = new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (int i = 0; i < balls.length; i++) {
                balls[i].moveOneStep();
                balls[i].drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(0);  // wait for 50 milliseconds.
        }
    }

    public static void main(String[] args) {
        Ball[] balls = new Ball[args.length];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = randomBallGenerator(Integer.parseInt(args[i]));
            balls[i].setStartPerimeterX(0);
            balls[i].setEndPerimeterX(perimeterX);
            balls[i].setStartPerimeterY(0);
            balls[i].setEndPerimeterY(perimeterY);
        }
        drawAnimation(balls);
    }
}
