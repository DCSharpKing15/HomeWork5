import biuoop.DrawSurface;

public class NameIndicator implements Sprite {
    private Block block;
    private GameLevel game;

    public NameIndicator(Block block, GameLevel game) {
        this.block = block;
        this.game = game;
    }

    // draw the sprite to the screen
    public void drawOn(DrawSurface d) {
        //this.block.drawOn(d);
        d.drawText(550, 27, "Level Name: " + this.game.getLevelInformation().levelName(), 20);
    }

    // notify the sprite that time has passed
    public void timePassed() {
    }

    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
