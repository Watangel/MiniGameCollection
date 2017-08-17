package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;

public class BombFire extends GameObject {
    public int count = 0;
    public BombFire(Texture texture, int srcX, int srcY, int srcW, int srcH){
        super(texture, srcX, srcY, srcW, srcH);
    }
}
