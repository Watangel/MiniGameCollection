package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

public class Tree extends ItemObject {

    public static final float TREE_WIDTH = 16 * 2.8f;
    public static final float TREE_HEIGHT = 64 * 2.8f;

    public Tree(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(TREE_WIDTH, TREE_HEIGHT);
    }
}
