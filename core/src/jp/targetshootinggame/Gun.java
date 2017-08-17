package jp.targetshootinggame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Gun extends Sprite {
    static final float GUN_WIDTH = 22 / 5 * 4;
    static final float GUN_HEIGHT = 128 / 5 * 4;

    static final float GUN_MOVE_SPEED = 150;

    public Gun(Texture texture, int x, int y, int w, int h){
        super(texture, x, y, w, h);
        setSize(GUN_WIDTH, GUN_HEIGHT);
    }

    public void moveLeft(){
        setPosition(getX() - GUN_MOVE_SPEED / 60, getY());
    }

    public void moveRight(){
        setPosition(getX() + GUN_MOVE_SPEED/ 60, getY());
    }
}
