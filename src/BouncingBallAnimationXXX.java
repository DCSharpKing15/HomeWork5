import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

public class BouncingBallAnimationXXX {

    //draws an animation of a ball
    static private void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title", 200, 200);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball((int) start.getX(), (int) start.getY(), 10, java.awt.Color.BLACK);
        //added this to be correct with my code
        ball.setStartPerimeterX(0);
        ball.setEndPerimeterX(200);
        ball.setStartPerimeterY(0);
        ball.setEndPerimeterY(200);
        //
        ball.setVelocity(dx, dy);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }

    public static void main(String[] args) {
        Point p = new Point(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        drawAnimation(p, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
    }


}
