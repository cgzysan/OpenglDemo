package ysan.opengldemo.game;


import java.util.ArrayList;
import java.util.Vector;

import ysan.opengldemo.game.geom.RectBox;
import ysan.opengldemo.utils.GLEx;


public class Sprites {

    public void add(ISprite sprite) {

        for (int i = sprites.size() - 1; i >= 0; i--) {
            if (sprites.get(i).getLayer() <= sprite.getLayer()) {
                sprites.insertElementAt(sprite, i);
                return;
            }
        }
        sprites.add(sprite);
    }

    public void remove(ISprite sprite) {
        sprites.remove(sprite);
    }

    public ISprite[] getCollided(RectBox box) {
        ArrayList<ISprite> colliedes = new ArrayList<ISprite>();
        for (ISprite sprite : sprites) {
            if (sprite.getCollided().intersect(box)) {
                colliedes.add(sprite);
            }
        }
        return (ISprite[]) colliedes.toArray();
    }

    public ISprite getOnlyColliede(RectBox box) {
        for (ISprite sprite : sprites) {
            if (sprite.getCollided().intersect(box)) {
                return sprite;
            }
        }
        return null;
    }

    private Vector<ISprite> sprites = new Vector<ISprite>();

    public void draw(GLEx gl) {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).draw(gl);
        }
    }

    public void update(long elapsedTime) {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).update(elapsedTime);
        }
    }
}
