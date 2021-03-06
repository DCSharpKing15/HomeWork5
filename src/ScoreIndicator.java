import biuoop.DrawSurface;

public class ScoreIndicator implements Sprite {
    private Block block;
    private GameLevel game;

    public ScoreIndicator(Block block, GameLevel game) {
        this.block = block;
        this.game = game;
    }

    // draw the sprite to the screen
    public void drawOn(DrawSurface d) {
        //this.block.drawOn(d);
        d.drawText(350, 27, "Score: " + game.getScoreCounter().getValue(), 20);
    }

    // notify the sprite that time has passed
    public void timePassed() {
    }

    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
