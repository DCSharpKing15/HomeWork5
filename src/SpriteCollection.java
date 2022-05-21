import biuoop.DrawSurface;

import java.util.ArrayList;

public class SpriteCollection {
    public java.util.List<Sprite> spriteList;

    public SpriteCollection() {
        this.spriteList = new ArrayList<Sprite>();
    }

    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    public java.util.List<Sprite> getSpriteList() {
        return this.spriteList;
    }

    // call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        for (int i = 0; i < this.spriteList.size(); i++) {
            this.spriteList.get(i).timePassed();
        }
    }

    // call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.spriteList.size(); i++) {
            this.spriteList.get(i).drawOn(d);
        }
    }
}
