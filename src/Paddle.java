import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.*;

public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Block block;
    private Color color;
    private final int MOVE_PADDLE_X = 1;
    private final int START_PERIMETER_X = 50;
    private final int END_PERIMETER_X = 750;
    private final int START_PERIMETER_Y = 50;
    private final int END_PERIMETER_Y = 550;
    private final int WIDTH = 100;
    private final String TYPE_NAME = "Paddle";
    private int upperLeftX;

    public Paddle(GUI gui) {
        this.keyboard = gui.getKeyboardSensor();
        this.color = Color.cyan;
        this.upperLeftX = 350;
        this.block = new Block(new Rectangle(new Point(this.upperLeftX, 530), this.WIDTH, 20), color);
    }

    @Override
    public String getTypeName() {
        return this.TYPE_NAME;
    }

    public void moveLeft() {
        if (this.block.getCollisionRectangle().getUpperLeft().getX() - MOVE_PADDLE_X > START_PERIMETER_X) {
            this.block = new Block(new Rectangle(new Point(this.block.getCollisionRectangle().getUpperLeft().getX() - MOVE_PADDLE_X, 530), this.WIDTH, 20), color);
            this.upperLeftX = (int) this.block.getCollisionRectangle().getUpperLeft().getX();
        } else {
            this.block = new Block(new Rectangle(new Point(START_PERIMETER_X, 530), this.WIDTH, 20), color);
            this.upperLeftX = START_PERIMETER_X;
        }
    }

    public void moveRight() {
        if (this.block.getCollisionRectangle().upperRight().getX() + MOVE_PADDLE_X < END_PERIMETER_X) {
            this.block = new Block(new Rectangle(new Point(this.block.getCollisionRectangle().getUpperLeft().getX() + MOVE_PADDLE_X, 530), this.WIDTH, 20), color);
            this.upperLeftX = (int) this.block.getCollisionRectangle().getUpperLeft().getX();
        } else {
            this.block = new Block(new Rectangle(new Point(END_PERIMETER_X - this.WIDTH, 530), this.WIDTH, 20), color);
            this.upperLeftX = END_PERIMETER_X - this.WIDTH;
        }
    }

    // Sprite
    public void timePassed() {

        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle rect = this.block.getCollisionRectangle();
        Point upperLeft = rect.getUpperLeft();
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        d.setColor(Color.black);
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    // Collidable
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double angle;
        double speed;
        // if the collision is with a horizontal side of the "Block" (can be also with a vertical side)
        if ((int) this.block.getCollisionRectangle().getUpperLeft().getY() == (int) collisionPoint.getY() ||
                (int) this.block.getCollisionRectangle().lowerLeft().getY() == (int) collisionPoint.getY()) {
            if (upperLeftX <= (int) collisionPoint.getX() && (int) collisionPoint.getX() <= upperLeftX + WIDTH / 5) {
                angle = Math.atan(currentVelocity.getDy() / currentVelocity.getDx());
                speed = currentVelocity.getDx() / Math.cos(angle);
                currentVelocity = Velocity.fromAngleAndSpeed(300, speed);
            } else if (upperLeftX + WIDTH / 5 < (int) collisionPoint.getX() && (int) collisionPoint.getX() <= upperLeftX + 2 * WIDTH / 5) {
                angle = Math.atan(currentVelocity.getDy() / currentVelocity.getDx());
                speed = currentVelocity.getDx() / Math.cos(angle);
                currentVelocity = Velocity.fromAngleAndSpeed(330, speed);
            } else if (upperLeftX + 2 * WIDTH / 5 < (int) collisionPoint.getX() && (int) collisionPoint.getX() <= upperLeftX + 3 * WIDTH / 5) {
                currentVelocity = new Velocity(currentVelocity.getDx(), (-1) * currentVelocity.getDy());
            } else if (upperLeftX + 3 * WIDTH / 5 < (int) collisionPoint.getX() && (int) collisionPoint.getX() <= upperLeftX + 4 * WIDTH / 5) {
                angle = Math.atan(currentVelocity.getDy() / currentVelocity.getDx());
                speed = currentVelocity.getDx() / Math.cos(angle);
                currentVelocity = Velocity.fromAngleAndSpeed(30, speed);
            } else {
                angle = Math.atan(currentVelocity.getDy() / currentVelocity.getDx());
                speed = currentVelocity.getDx() / Math.cos(angle);
                currentVelocity = Velocity.fromAngleAndSpeed(60, speed);
            }
        }

        // if the collision is with a vertical side of the "Block" (can be also with a horizontal side)
        if ((int) this.block.getCollisionRectangle().getUpperLeft().getX() == (int) collisionPoint.getX() ||
                (int) this.block.getCollisionRectangle().upperRight().getX() == (int) collisionPoint.getX()) {
            currentVelocity = new Velocity((-1) * currentVelocity.getDx(), currentVelocity.getDy());

        }

        return currentVelocity;
    }

    // Add this paddle to the game.
    public void addToGame(GameLevel g) {
        for (int i = 0; i < g.getEnvironment().getCollidableList().size(); i++) {
            if (g.getEnvironment().getCollidableList().get(i).getTypeName().equals(this.TYPE_NAME)) {
                g.getEnvironment().getCollidableList().remove(i);
            }
        }
        g.addCollidable(this);
        g.addSprite(this);
    }

}