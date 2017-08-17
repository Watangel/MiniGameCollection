package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

public class StopButton extends ItemObject {

    public static final float STOPBUTTON_WIDTH = 420 / 10;
    public static final float STOPBUTTON_HEIGHT = 420 / 10;

    public StopButton(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(STOPBUTTON_WIDTH, STOPBUTTON_HEIGHT);
    }
}
