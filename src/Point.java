public class Point {
    private double x;
    private double y;

    // constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
        double dis = Math.sqrt((this.x - other.getX()) * (this.x - other.getX()) + (this.y - other.getY()) * (this.y - other.getY()));
        return dis;
    }

    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        if (this.x == other.getX() && this.y == other.getY()) {
            return true;
        }
        return false;
    }

    // Return the x and y values of this point
    public double getX() {
        return (double) this.x;
    }

    public double getY() {
        return (double) this.y;
    }

    //returns true if the next move will be in the collidable object
    public boolean checkIfNextMoveIsInTheCollidable(Collidable collisionObject) {
        if ((int) this.getX() > (int) collisionObject.getCollisionRectangle().getUpperLeft().getX() &&
                (int) this.getX() < (int) collisionObject.getCollisionRectangle().upperRight().getX() &&
                (int) this.getY() > (int) collisionObject.getCollisionRectangle().getUpperLeft().getY() &&
                (int) this.getY() < (int) collisionObject.getCollisionRectangle().lowerLeft().getY()) {
            return true;
        }
        return false;
    }
}
