import javax.lang.model.type.NullType;
import java.util.ArrayList;

public class Line {
    private Point start;
    private Point end;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double xMid = (double) (this.start.getX() + this.end.getX()) / 2;
        double yMid = (double) (this.start.getY() + this.end.getY()) / 2;
        Point mid = new Point(xMid, yMid);
        return mid;
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        double slope1 = (double) slope(this);
        double slope2 = (double) slope(other);
        Point intersection;
        //if true then they are the same line with different starts and ends
        if (slope1 == slope2) {
            intersection = this.doThisIfTheyHaveSameSlopes(other);
            if (intersection != null) {
                if (intersection.getX() == -1) {
                    return false;
                }
            }
            return true;
        }

        if (this.intersectionWith(other) != null) {
            return true;
        }
        return false;
    }

    // Returns the slope of the line;
    public double slope(Line line) {
        /*if (line.start().getX() == line.end().getX()) {
            return 0;
        }*/
        double slope = (double) (line.start().getY() - line.end().getY()) / (line.start().getX() - line.end().getX());
        return slope;
    }

    //haveInfiniteIntersectionsFromStartOrEnd -- returns true if they have infinite intersections for the start or end points
    // else returns true
    public boolean haveInfiniteIntersectionsFromStartOrEnd(Line other, int Start) {
        if (Start == 1) {
            if (this.end.getX() < this.start.getX() && other.end().getX() < other.start().getX()) {
                // if they have "infinite" intersections
                return true;
            } else if (this.end.getX() > this.start.getX() && other.end().getX() > other.start().getX()) {
                // if they have "infinite" intersections
                return true;
            } else {
                return false;
            }
        } else {
            if (this.start.getX() < this.end.getX() && other.end().getX() < other.start().getX()) {
                // if they have "infinite" intersections
                return true;
            } else if (this.start.getX() > this.end.getX() && other.end().getX() > other.start().getX()) {
                // if they have "infinite" intersections
                return true;
            } else {
                return false;
            }
        }
    }

    //doThisIfTheyHaveSameSlopes -- returns null if they have infinite intersections
    // else it returns Point(-1,-1) as default to not intersecting
    // else the intersection point
    public Point doThisIfTheyHaveSameSlopes(Line other) {
        //if they intersect at the start of other
        if (this.start.equals(other.start())) {
            if (this.haveInfiniteIntersectionsFromStartOrEnd(other, 1)) {
                return null;
            } else {
                return other.start();
            }

        } else if (this.end.equals(other.start())) {
            if (this.haveInfiniteIntersectionsFromStartOrEnd(other, 0)) {
                // if they have "infinite" intersections
                return null;
            } else {
                return other.start();
            }

        }

        //else if they intersect at the end of other
        if (this.start.equals(other.end())) {
            if (other.haveInfiniteIntersectionsFromStartOrEnd(this, 0)) {
                // if they have "infinite" intersections
                return null;
            } else {
                return other.end();
            }
        } else if (this.end.equals(other.end())) {
            if (other.haveInfiniteIntersectionsFromStartOrEnd(this, 1)) {
                // if they have "infinite" intersections
                return null;
            } else {
                return other.end();
            }
        }
        // if they do not intersect
        return new Point(-1, -1);
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        double slope1 = (double) slope(this);
        double slope2 = (double) slope(other);
        Point intersection;
        //if true then they are the same line with different starts and ends
        if (slope1 == slope2) {
            intersection = this.doThisIfTheyHaveSameSlopes(other);
            if (intersection != null) {
                if (intersection.getX() != -1) {
                    return intersection;
                }
            }
            return null;
        }
        intersection = this.intersection(other);

        if (this.inTheFiniteLine(intersection) && other.inTheFiniteLine(intersection)) {
            return intersection;
        }
        return null;
    }

    // intersection -- returns the intersection of the "infinite" lines
    public Point intersection(Line other) {
        double slope1 = (double) slope(this);
        double slope2 = (double) slope(other);

        double x1 = (double) this.start.getX();
        double y1 = (double) this.start.getY();

        double x2 = (double) other.start().getX();
        double y2 = (double) other.start().getY();

        double intersectionX;
        double intersectionY;

        if (x2 == other.end().getX()) {
            intersectionX = x2;
            intersectionY = slope1 * x2 - slope1 * x1 + y1;
        } else if (x1 == this.end.getX()) {
            intersectionX = x1;
            intersectionY = slope2 * x1 - slope2 * x2 + y2;
        } else {
            //after many calculations I got this for the x of the intersection:
            intersectionX = (double) (slope1 * x1 + y2 - y1 - slope2 * x2) / (slope1 - slope2);

            //after many calculations I got this for the y of the intersection:
            intersectionY = (double) (slope1 * slope2 * x1 + slope1 * y2 - slope1 * slope2 * x2 - slope2 * y1) / (slope1 - slope2);
        }

        Point intersection = new Point(intersectionX, intersectionY);
        return intersection;
    }

    //if the point is between the start and end points in the line
    public boolean inTheFiniteLine(Point p) {
        if (Math.min(this.start.getX(), this.end.getX()) <= p.getX() &&
                p.getX() <= Math.max(this.start.getX(), this.end.getX())) {
            if (Math.min(this.start.getY(), this.end.getY()) <= p.getY() &&
                    p.getY() <= Math.max(this.start.getY(), this.end.getY())) {
                return true;
            }
        }
        return false;
    }

    // equals -- return true if the lines are equal, false otherwise
    public boolean equals(Line other) {
        if ((this.start.equals(other.start()) || this.start.equals(other.end())) &&
                (this.end.equals(other.start()) || this.end.equals(other.end()))) {
            return true;
        }
        return false;
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersectionList = rect.intersectionPoints(this);
        if (intersectionList.size() == 0) {
            return null;
        }
        int index = 0;
        double minDistant = this.start.distance(intersectionList.get(index));
        double currentDistant;
        for (int i = 1; i < intersectionList.size(); i++) {
            currentDistant = this.start.distance(intersectionList.get(i));
            if (minDistant > currentDistant) {
                minDistant = currentDistant;
                index = i;
            }
        }
        return intersectionList.get(index);
    }
}
