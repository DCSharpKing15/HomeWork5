// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx;
    private double dy;

    // constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        Point point = new Point(((double) p.getX()) + dx, ((double) p.getY()) + dy);
        return point;
    }

    // getDx -- returns dx
    public double getDx() {
        return this.dx;
    }

    // getDy -- returns dy
    public double getDy() {
        return this.dy;
    }

    //fromAngleAndSpeed -- returns the calculated velocity from the angle and speed
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);
        return new Velocity(dx, dy);
    }
}
