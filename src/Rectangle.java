import java.util.ArrayList;

public class Rectangle {
    // members
    private Point upperLeft;
    private double width;
    private double height;


    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }


    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intersectionList = new ArrayList<Point>();
        Point intersectionWithUpperSide = line.intersectionWith(this.upperSide());
        Point intersectionWithLeftSide = line.intersectionWith(this.leftSide());
        Point intersectionWithLowerSide = line.intersectionWith(this.lowerSide());
        Point intersectionWithRightSide = line.intersectionWith(this.rightSide());
        if (intersectionWithUpperSide != null) {
            intersectionList.add(intersectionWithUpperSide);
        }
        if (intersectionWithLeftSide != null) {
            intersectionList.add(intersectionWithLeftSide);
        }
        if (intersectionWithLowerSide != null) {
            intersectionList.add(intersectionWithLowerSide);
        }
        if (intersectionWithRightSide != null) {
            intersectionList.add(intersectionWithRightSide);
        }
        return intersectionList;
    }

    // Return the width and height of the rectangle
    public double getWidth() {
        return this.width;
    }

    // Return the height and height of the rectangle
    public double getHeight() {
        return this.height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    // Returns the upper-right point of the rectangle(not a member)
    public Point upperRight() {
        return new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
    }

    // Returns the lower-left point of the rectangle(not a member)
    public Point lowerLeft() {
        return new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
    }

    // Returns the lower-right point of the rectangle(not a member)
    public Point lowerRight() {
        return new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
    }

    //Returns the upper side of the rectangle(not a member)
    public Line upperSide() {
        return new Line(this.upperLeft, this.upperRight());
    }

    //Returns the left side of the rectangle(not a member)
    public Line leftSide() {
        return new Line(this.upperLeft, this.lowerLeft());
    }

    //Returns the lower side of the rectangle(not a member)
    public Line lowerSide() {
        return new Line(this.lowerLeft(), this.lowerRight());
    }

    //Returns the right side of the rectangle(not a member)
    public Line rightSide() {
        return new Line(this.upperRight(), this.lowerRight());
    }
}
