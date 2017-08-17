package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerLevel {
    static final int EXPERIENCE = 100;

    static final float FRAME_WIDTH = 440;
    static final float FRAME_HEIGHT = 140;

    static final float EXP_WIDTH = 400;
    static final float EXP_HEIGHT = 100;


    Preferences levPrefs, momentExpPrefs;

    int count;
    int countResult;
    int level;
    int coin;
    int high;
    int beforeExp;
    int momentExp;
    int difference;

    boolean checkOnce;
    boolean checkCoin;
    boolean checkHigh;

    Texture expFrameTexture = new Texture("experience_frame.png");
    Texture expTexture = new Texture("experience.png");
    Texture resultTexture = new Texture("result.png");

    Sprite expFrame = new Sprite(expFrameTexture, 0, 0, 440, 140);
    Sprite exp = new Sprite(expTexture, 0, 0 , 400, 100);
    Sprite resultCoin = new Sprite(resultTexture, 0, 0, 24, 24);
    Sprite resultHigh = new Sprite(resultTexture, 32, 32, 24, 24);
    Sprite resultExp = new Sprite(resultTexture, 64, 64 , 48, 24);

    BitmapFont resultFont;

    public PlayerLevel(int exp, int coin, int high){
        levPrefs = Gdx.app.getPreferences("jp.Lev");
        momentExpPrefs = Gdx.app.getPreferences("jp.Exp");
        resultFont = new BitmapFont(Gdx.files.internal("font_count.fnt"), Gdx.files.internal("font_count_0.png"), false);
        resultFont.setColor(Color.GRAY);

        //テスト用リセット
        levPrefs.putInteger("Lev", 0);
        levPrefs.flush();
        momentExpPrefs.putInteger("Exp", 0);
        momentExpPrefs.flush();

        beforeExp = momentExpPrefs.getInteger("Exp", 0);
        momentExp = exp;
        difference = momentExp - beforeExp;
        level = levPrefs.getInteger("Lev", 0);
        count = 0;
        this.coin = coin;
        this.high = high;
        checkOnce = true;
        checkCoin = true;
        checkHigh = false;
        System.out.println("Difference" + difference);
    }

    public void levelDraw(float xcenter, float ycenter, SpriteBatch batch){
        expFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        expFrame.setPosition(xcenter - FRAME_WIDTH / 2, ycenter - FRAME_HEIGHT / 2);
        if (count < difference) {
            count++;
            if (count + beforeExp > (level + 1) * EXPERIENCE) {
                difference -= count;
                count = 0;
                beforeExp = 0;
                level++;
                momentExpPrefs.putInteger("Exp", 0);
                momentExpPrefs.flush();
                System.out.println("レベルアップ");
                System.out.println("Level" + levPrefs.getInteger("Lev", 0));
            }
            exp.setSize(EXP_WIDTH / ((level + 1) * EXPERIENCE) * (count + beforeExp), EXP_HEIGHT);
            exp.setPosition(xcenter - EXP_WIDTH / 2, ycenter - EXP_HEIGHT / 2);
            System.out.println("COUNT" + count);
        }
        /*} else if (count < 361) {
            exp.setPosition(xcenter - EXP_WIDTH / 2, ycenter - EXP_HEIGHT / 2);
            System.out.println("空白タイム" + count);
        }*/

        if (checkCoin){

        }

        if (count == difference && checkOnce){
            momentExpPrefs.putInteger("Exp", beforeExp + momentExp);
            momentExpPrefs.flush();
            System.out.println("EXP" + momentExpPrefs.getInteger("Exp", 0));
            levPrefs.putInteger("Lev", level);
            levPrefs.flush();
            System.out.println("LEV" + levPrefs.getInteger("Lev", 0));
            System.out.println("保存");
            checkOnce = false;
        }
        /*} else if (count < difference){
            exp.setSize(EXP_WIDTH / ((Level + 1) * EXPERIENCE) * (count + beforeExp), EXP_HEIGHT);
            exp.setPosition(xcenter - EXP_WIDTH / 2, ycenter - EXP_HEIGHT / 2);

            System.out.println("じゃいｄｊふぃｓｊふぃあｊふぃｓ");

        }*/
        expFrame.draw(batch);
        exp.draw(batch);
    }
}
