import java.util.ArrayList;

public class GameEnvironment {
    java.util.List<Collidable> collidableList;

    public GameEnvironment() {
        this.collidableList = new ArrayList<Collidable>();
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        collidableList.add(c);
    }

    public java.util.List<Collidable> getCollidableList() {
        return this.collidableList;
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestIntersection = trajectory.closestIntersectionToStartOfLine(this.getCollidableList().get(0).getCollisionRectangle());
        Point nextHitPoint;
        double minDistant;
        double distant;
        int index = 0;
        for (int i = 4; i < this.collidableList.size(); i++) {
            nextHitPoint = trajectory.closestIntersectionToStartOfLine(this.collidableList.get(i).getCollisionRectangle());
            if (nextHitPoint != null) {
                distant = trajectory.start().distance(nextHitPoint);
                if (closestIntersection != null) {
                    minDistant = trajectory.start().distance(closestIntersection);
                    if (distant < minDistant) {
                        closestIntersection = nextHitPoint;
                        index = i;
                    }
                } else {
                    closestIntersection = nextHitPoint;
                    index = i;
                }
            }
        }
        if (closestIntersection == null) {
            return null;
        }
        return new CollisionInfo(closestIntersection, collidableList.get(index));
    }

}
