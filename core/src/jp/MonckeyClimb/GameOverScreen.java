package jp.MonckeyClimb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import menu.ButtonInputListener;
import menu.MiniGameCollection;
import menu.PlayerLevel;
import menu.TestHomeScreen;

public class GameOverScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 600;
    static final float CAMERA_HEIGHT = 900;

    static final float BUTTON_BG_WIDTH = 600;
    static final float BUTTON_BG_HEIGHT = 800;
    static final float BUTTON_BG_X = CAMERA_WIDTH / 2 - BUTTON_BG_WIDTH / 2;
    static final float BUTTON_BG_Y = CAMERA_HEIGHT / 2 - BUTTON_BG_HEIGHT / 2;

    static final float LEVEL_X_CENTER = BUTTON_BG_WIDTH / 2;
    static final float LEVEL_Y_CENTER = BUTTON_BG_HEIGHT / 3 * 2;

    static final float BUTTON_WIDTH = 420 / 5;
    static final float BUTTON_HEIGHT = 420 / 5;
    static final float BUTTON_1_X = BUTTON_BG_X + BUTTON_BG_WIDTH / 3 - BUTTON_WIDTH / 2;
    static final float BUTTON_2_X = BUTTON_BG_X + BUTTON_BG_WIDTH / 3 * 2 - BUTTON_WIDTH / 2;
    static final float BUTTON_Y = BUTTON_BG_Y + BUTTON_BG_HEIGHT / 3 - BUTTON_WIDTH / 2;

    private MiniGameCollection minioriginGame;
    Sprite Bg, buttonBg;
    OrthographicCamera mCamera;
    FitViewport mViewPort;
    BitmapFont scoreFont, retryFont, highScoreFont;

    Texture buttonBackGround;
    TextureRegion retryButtonRegion;
    TextureRegion homeButtonRegion;

    Button retryButton;
    Button homeButton;
    Button.ButtonStyle retryButtonStyle;
    Button.ButtonStyle homeButtonStyle;

    PlayerLevel playerLevel;

    Stage stage;

    public GameOverScreen(MiniGameCollection mOriginGame,int coin, int score){
        minioriginGame = mOriginGame;

        playerLevel = new PlayerLevel(10000, coin, score);

        Texture backGround = new Texture("background_result.png");
        Bg = new Sprite(new TextureRegion(backGround, 0, 0, 600, 900));
        Bg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);

        //カメラとビューポート
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        //フォント
        scoreFont = new BitmapFont(Gdx.files.internal("fontscore.fnt"), Gdx.files.internal("fontscore_0.png"), false);
        scoreFont.setColor(Color.GRAY);
        scoreFont.getData().setScale(1);

        retryFont = new BitmapFont(Gdx.files.internal("fontretry.fnt"), Gdx.files.internal("fontretry_0.png"), false);
        retryFont.setColor(Color.GRAY);
        retryFont.getData().setScale(1);

        //ハイスコア
        highScoreFont = new BitmapFont(Gdx.files.internal("fonthighscore.fnt"), Gdx.files.internal("fonthighscore_0.png"), false);
        highScoreFont.setColor(Color.GRAY);
        highScoreFont.getData().setScale(1);

        //ボタンバックグラウンド
        buttonBackGround = new Texture("buttonbackground.png");
        buttonBg = new Sprite(new TextureRegion(buttonBackGround, 0, 0, 600, 400));
        buttonBg.setSize(BUTTON_BG_WIDTH, BUTTON_BG_HEIGHT);
        buttonBg.setPosition(BUTTON_BG_X, BUTTON_BG_Y);

        //ここからステージ
        //リトライボタン
        retryButtonRegion = new TextureRegion(new Texture("monkeyretrybutton.png"), 420, 420);
        retryButtonStyle = new Button.ButtonStyle();
        retryButtonStyle.up = new TextureRegionDrawable(retryButtonRegion);
        retryButtonStyle.down = new TextureRegionDrawable(retryButtonRegion);
        retryButton = new Button(retryButtonStyle);
        retryButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        retryButton.setPosition(BUTTON_1_X, BUTTON_Y);
        retryButton.addListener(new ButtonInputListener(retryButton, 0.96f){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                retryButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
                retryButton.setPosition(BUTTON_1_X, BUTTON_Y);
                stage.dispose();
                minioriginGame.setScreen(new PlayScreen(minioriginGame));
            }
        });

        homeButtonRegion = new TextureRegion(new Texture("monkeyhomebutton.png"), 420, 420);
        homeButtonStyle = new Button.ButtonStyle();
        homeButtonStyle.up = new TextureRegionDrawable(homeButtonRegion);
        homeButtonStyle.down = new TextureRegionDrawable(homeButtonRegion);
        homeButton = new Button(homeButtonStyle);
        homeButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        homeButton.setPosition(BUTTON_2_X, BUTTON_Y);
        homeButton.addListener(new ButtonInputListener(homeButton, 0.96f){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                homeButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
                homeButton.setPosition(BUTTON_2_X, BUTTON_Y);
                stage.dispose();
                minioriginGame.setScreen(new TestHomeScreen(minioriginGame));
            }
        });

        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        stage.addActor(retryButton);
        stage.addActor(homeButton);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);
        Matrix4 cameraMatrix = stage.getViewport().getCamera().combined;
        minioriginGame.batch.setProjectionMatrix(cameraMatrix);
        stage.act();
        stage.getViewport().getCamera().update();

        mCamera.update();
        minioriginGame.batch.setProjectionMatrix(mCamera.combined);


        minioriginGame.batch.begin();

        Bg.draw(minioriginGame.batch);
        buttonBg.draw(minioriginGame.batch);
        playerLevel.levelDraw(LEVEL_X_CENTER, LEVEL_Y_CENTER, minioriginGame.batch);
        minioriginGame.batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
    }
}
