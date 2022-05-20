import biuoop.DrawSurface;

import java.awt.*;

public class Ball implements Sprite {
    private Point center;
    private int r;
    private Color color;
    private Velocity v;
    private int startPerimeterX;
    private int endPerimeterX;
    private int startPerimeterY;
    private int endPerimeterY;
    private GameEnvironment gameEnvironment;


    // constructor
    public Ball(Point center, int r, Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }

    public Ball(int x, int y, int r, Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }

    // getters
    public int getX() {
        return (int) this.center.getX();
    }

    public int getY() {
        return (int) this.center.getY();
    }

    public int getSize() {
        return this.r;
    }

    public Color getColor() {
        return this.color;
    }

    public Velocity getVelocity() {
        return this.v;
    }

    public int getStartPerimeterX() {
        return this.startPerimeterX;
    }

    public int getEndPerimeterX() {
        return this.endPerimeterX;
    }

    public int getStartPerimeterY() {
        return this.startPerimeterY;
    }

    public int getEndPerimeterY() {
        return this.endPerimeterY;
    }

    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment;
    }

    // setters
    public void setStartPerimeterX(int startPerimeterX) {
        this.startPerimeterX = startPerimeterX;
    }

    public void setEndPerimeterX(int endPerimeterX) {
        this.endPerimeterX = endPerimeterX;
    }

    public void setStartPerimeterY(int startPerimeterY) {
        this.startPerimeterY = startPerimeterY;
    }

    public void setEndPerimeterY(int endPerimeterY) {
        this.endPerimeterY = endPerimeterY;
    }

    public void setVelocity(Velocity v) {
        this.v = v;
    }

    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(getX(), getY(), this.r);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }


    // trajectory -- returns the trajectory of the ball "like it didn't hit any obstacles"
    public Line trajectory() {
        Point endOfTrajectory = this.center;//will be the ent of the trajectory
        while (endOfTrajectory.getX() < this.endPerimeterX && endOfTrajectory.getX() > this.startPerimeterX &&
                endOfTrajectory.getY() < this.endPerimeterY && endOfTrajectory.getY() > this.startPerimeterY) {
            endOfTrajectory = this.getVelocity().applyToPoint(endOfTrajectory);
        }

        return new Line(this.center, endOfTrajectory);
    }

    // calculateNextMove -- returns the next place where the center will be
    public Point calculateNextMove() {
        Point copyCenter = this.center;
        return this.getVelocity().applyToPoint(this.getVelocity().applyToPoint(copyCenter));
    }


    // moveOneStep -- moves the ball according to the perimeter and velocity(if its about to get out
    // the dx changes to -dx or dy changes to -dy)
    public void moveOneStep() {
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(this.trajectory());
        if (collisionInfo != null) {
            Point nextMove = this.calculateNextMove();
            if (nextMove.checkIfNextMoveIsInTheCollidable(collisionInfo.collisionObject())) {
                this.setVelocity(collisionInfo.collisionObject().hit(collisionInfo.collisionPoint(), this.v));
            }

        } else {
            if (this.calculateNextMove().getX() >= this.endPerimeterX || this.calculateNextMove().getX() <= this.startPerimeterX) {
                setVelocity((-1) * this.v.getDx(), this.v.getDy());
            }
            if (this.calculateNextMove().getY() >= this.endPerimeterY || this.calculateNextMove().getY() <= this.startPerimeterY) {
                setVelocity(this.v.getDx(), (-1) * this.v.getDy());
            }
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
