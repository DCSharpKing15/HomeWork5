import java.awt.*;

import biuoop.DrawSurface;

public class Block implements Collidable, Sprite {
    private Rectangle collisionRectangle;
    private Color color;
    private final String TYPE_NAME = "Block";

    public Block(Rectangle collisionRectangle, Color color) {
        this.collisionRectangle = collisionRectangle;
        this.color = color;
    }

    // Return the "collision shape" of the object.
    public Rectangle getCollisionRectangle() {
        return this.collisionRectangle;
    }

    @Override
    public String getTypeName() {
        return this.TYPE_NAME;
    }

    public Color getColor() {
        return this.color;
    }

    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {


        // if the collision is with a horizontal side of the "Block" (can be also with a vertical side)
        if ((int) this.collisionRectangle.getUpperLeft().getY() == (int) collisionPoint.getY() ||
                (int) this.collisionRectangle.lowerLeft().getY() == (int) collisionPoint.getY()) {
            currentVelocity = new Velocity(currentVelocity.getDx(), (-1) * currentVelocity.getDy());
        }

        // if the collision is with a vertical side of the "Block" (can be also with a horizontal side)
        if ((int) this.collisionRectangle.getUpperLeft().getX() == (int) collisionPoint.getX() ||
                (int) this.collisionRectangle.upperRight().getX() == (int) collisionPoint.getX()) {
            currentVelocity = new Velocity((-1) * currentVelocity.getDx(), currentVelocity.getDy());
        }

        /*
        if ((int) this.collisionRectangle.getUpperLeft().getY() == (int) collisionPoint.getY()) {
            currentVelocity = new Velocity(currentVelocity.getDx(), (-1) * currentVelocity.getDy());
        } else if ((int) this.collisionRectangle.getUpperLeft().getX() == (int) collisionPoint.getX()) {
            currentVelocity = new Velocity((-1) * currentVelocity.getDx(), currentVelocity.getDy());
        } else if ((int) this.collisionRectangle.lowerLeft().getY() == (int) collisionPoint.getY()) {
            currentVelocity = new Velocity(currentVelocity.getDx(), (-1) * currentVelocity.getDy());
        } else if ((int) this.collisionRectangle.upperRight().getX() == (int) collisionPoint.getX()) {
            currentVelocity = new Velocity((-1) * currentVelocity.getDx(), currentVelocity.getDy());
        }*/

        return currentVelocity;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle rect = this.collisionRectangle;
        Point upperLeft = rect.getUpperLeft();
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        d.setColor(Color.black);
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    public void timePassed() {
    }

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
