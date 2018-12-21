package ysan.opengldemo.game.core;


import ysan.opengldemo.game.ISprite;
import ysan.opengldemo.utils.GLEx;
import ysan.opengldemo.utils.LTexture;

public abstract class Screen {

    public LTexture background;
    int width, height;

    public void setBackground(String filename) {
        this.background = new LTexture(filename);
    }

    public void paint(GLEx g) {
        if (background != null)
            g.drawTexture(background, 0, 0, width, height);
        draw(g);
    }

    public void update(long elapsedTime) {
        alter(elapsedTime);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected void onPressed(int keycode) {
        // TODO Auto-generated method stub

    }

    protected void onReleased(int keycode) {

    }

    public void addSprite(ISprite sprite) {

    }

    public void addSprite(ISprite sprite, int layer) {

    }

    public void dispose() {
    }

    ;

    public abstract void draw(GLEx g);

    public abstract void alter(long elapsedTime);

}
