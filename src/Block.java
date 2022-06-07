import java.awt.*;

import biuoop.DrawSurface;

import java.util.ArrayList;

public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle collisionRectangle;
    private Color color;
    private final String TYPE_NAME = "Block";
    private java.util.List<HitListener> hitListeners;

    public Block(Rectangle collisionRectangle, Color color) {
        this.collisionRectangle = collisionRectangle;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
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
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Boolean wasHit = false;

        // if the collision is with a horizontal side of the "Block" (can be also with a vertical side)
        if ((int) this.collisionRectangle.getUpperLeft().getY() == (int) collisionPoint.getY() ||
                (int) this.collisionRectangle.lowerLeft().getY() == (int) collisionPoint.getY()) {
            currentVelocity = new Velocity(currentVelocity.getDx(), (-1) * currentVelocity.getDy());

            wasHit = true;
        }

        // if the collision is with a vertical side of the "Block" (can be also with a horizontal side)
        if ((int) this.collisionRectangle.getUpperLeft().getX() == (int) collisionPoint.getX() ||
                (int) this.collisionRectangle.upperRight().getX() == (int) collisionPoint.getX()) {
            currentVelocity = new Velocity((-1) * currentVelocity.getDx(), currentVelocity.getDy());

            wasHit = true;
        }

        if (wasHit) {
            this.notifyHit(hitter);
        }

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

    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    // notify if this block was hit
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        java.util.List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
