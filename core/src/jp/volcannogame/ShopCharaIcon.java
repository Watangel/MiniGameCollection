package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ShopCharaIcon extends Sprite {
    public static final float ICON_WIDTH = 57f;
    public static final float ICON_HEIGHT = 108f;

    public boolean bought;

    public ShopCharaIcon(Texture texture, int x, int y, int w, int h){
        super(texture, x, y, w, h);
        setSize(ICON_WIDTH , ICON_HEIGHT);
        bought = false;
    }
}
