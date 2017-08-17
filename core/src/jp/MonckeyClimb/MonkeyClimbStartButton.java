package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MonkeyClimbStartButton extends Sprite {

    public static final float BUTTON_WIDTH = 185;
    public static final float BUTTON_HEIGHT = 98;

    public MonkeyClimbStartButton(TextureRegion texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    }
}
