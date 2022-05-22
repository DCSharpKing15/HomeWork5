import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.*;
import java.util.Random;

public class AbstractArtDrawingXXX {

    //generates a new random line
    public static Line generateRandomLine() {
        Random rand = new Random(); // create a random-number generator
        int x1 = rand.nextInt(400) + 1; // get int in range 1-400
        int y1 = rand.nextInt(300) + 1; // get int in range 1-300
        int x2 = rand.nextInt(400) + 1; // get int in range 1-400
        while (x1 == x2) {
            x2 = rand.nextInt(400) + 1; // get int in range 1-400
        }
        int y2 = rand.nextInt(300) + 1; // get int in range 1-300
        Line line = new Line(x1, y1, x2, y2);
        return line;
    }

    // drawLine -- draws the line(color black)
    public static void drawLine(Line l, DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawLine((int) (l.start().getX()), (int) (l.start().getY()), (int) (l.end().getX()), (int) (l.end().getY()));
    }

    // drawMiddle -- draws the where the middle of the line is(blue circle with radios 3)
    public static void drawMiddle(Line l, DrawSurface d) {
        Point mid = l.middle();
        d.setColor(Color.BLUE);
        d.fillCircle((int) (mid.getX()), (int) (mid.getY()), 3);
    }

    // drawIntersection -- if there is intersection it draws where it is(red circle withe radios 3)
    // else does nothing
    public static void drawIntersection(Line l1, Line l2, DrawSurface d) {
        if (l1.isIntersecting(l2)) {
            Point intersection = l1.intersection(l2);
            d.setColor(Color.RED);
            d.fillCircle((int) intersection.getX(), (int) intersection.getY(), 3);
        }
    }


    public static void main(String[] args) {
        //Random rand = new Random(); // create a random-number generator
        // Create a window with the title "Random Line"
        // which is 400 pixels wide and 300 pixels high.
        GUI gui = new GUI("Random Line ", 400, 300);
        DrawSurface d = gui.getDrawSurface();
        Line[] lines = new Line[10];
        for (int i = 0; i < 10; ++i) {
            lines[i] = generateRandomLine();
            drawLine(lines[i], d);
            drawMiddle(lines[i], d);
            for (int j = 0; j < i; j++) {
                drawIntersection(lines[i], lines[j], d);
            }
        }
        gui.show(d);
    }
}
