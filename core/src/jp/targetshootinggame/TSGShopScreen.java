package jp.targetshootinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import menu.MiniGameCollection;
import menu.SelectShop;

public class TSGShopScreen extends ScreenAdapter{
    static final float CAMERA_WIDTH = 300;
    static final float CAMERA_HEIGHT = 450;

    static final int STATE_NOMAL = 0;
    static final int STATE_CHECK_PURCHASE = 1;

    static final int CHECK_PURCHASE_BIPOD = 0;
    static final int CHECK_PURCHASE_SCOPE = 1;
    static final int CHECK_PURCHASE_METAL_FRAME = 2;
    static final int CHECK_PURCHASE_MGAZINE = 3;

    private int customPrice;

    private int checkPurchaseState;

    private int mState;

    MiniGameCollection mGame;

    Sprite mBg;

    //ボタン
    Button backButton;

    //ステージ
    Stage stage;
    Stage purchaseStage;

    //Preference
    Preferences coinPrefs;
    Preferences mPrefs;

    //フォント
    BitmapFont mFont;

    //shop素材
    Sprite nomalGun;
    Sprite bipod;
    Sprite scopeEight;
    Sprite gunFrame1;
    Sprite gunFrame2;
    Sprite magazine;
    Sprite gunState;
    Sprite gunStateGageSpr;

    //checkPurchase
    Sprite checkPurchaseBg;

    public TSGShopScreen(MiniGameCollection game){
        mGame = game;

        //Preference
        coinPrefs = Gdx.app.getPreferences("jp.MiniGameCollection");
        mPrefs = Gdx.app.getPreferences("TargetShootingGame");

        //初期化
/*        mPrefs.putInteger("BIPOD", 0);
        mPrefs.putInteger("8xSCOPE", 0);
        mPrefs.putInteger("MAGAZINE", 0);
        mPrefs.putInteger("METAL FRAME", 0);
        mPrefs.flush();*/
        mPrefs.putInteger("MUZZLE VELOCITY", 2);
        mPrefs.putInteger("ACCURACY", 1);
        mPrefs.putInteger("RATE OF FIRE", 3);
        mPrefs.flush();

        mState = STATE_NOMAL;


        //背景
        Texture bgTexture = new Texture("ShopBg.png");
        mBg = new Sprite(bgTexture, 0, 0, 300, 450);

        //フォント
        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        mFont.setColor(Color.GOLD);

        //BackButton
        TextureRegion backRegion = new TextureRegion(new Texture("backbutton.png"), 420, 420);
        Button.ButtonStyle backButtonStyle = new Button.ButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(backRegion);
        backButtonStyle.down = new TextureRegionDrawable(backRegion);

        backButton = new Button(backButtonStyle);
        backButton.setSize(50, 50);
        backButton.setPosition(5, CAMERA_HEIGHT - 55);
        backButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle back = new Rectangle(0, 0, backButton.getWidth(), backButton.getHeight());
                if(back.contains(x, y)){
                    backButton.setSize(50 * 0.96f, 50 * 0.96f);
                    backButton.setPosition(5 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                }else {
                    backButton.setSize(50, 50);
                    backButton.setPosition(5, CAMERA_HEIGHT - 55);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                backButton.setSize(50 * 0.96f, 50 * 0.96f);
                backButton.setPosition(5 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setSize(50, 50);
                backButton.setPosition(5, CAMERA_HEIGHT - 55);
                Rectangle back = new Rectangle(0, 0, backButton.getWidth(), backButton.getHeight());
                if(back.contains(x, y)) {
                    stage.dispose();
                    mGame.setScreen(new SelectShop(mGame));
                }
            }
        });

        //shop素材
        nomalGun = new Sprite(new Texture("Gun.png"), 44, 256);
        nomalGun.setSize(44 * 1.3f, 256 * 1.3f);
        nomalGun.setPosition(CAMERA_WIDTH / 4 * 3 - nomalGun.getWidth() / 2, 0);
        bipod = new Sprite(new Texture("bi_pod.png"), 106, 22);
        bipod.setSize(106 * 1.3f, 22 * 1.3f);
        bipod.setPosition(CAMERA_WIDTH / 4 * 3 - bipod.getWidth() / 2, nomalGun.getHeight() / 3 * 2);
        scopeEight = new Sprite(new Texture("8xScope.png"), 22, 94);
        scopeEight.setSize(22 * 1.3f, 94 * 1.3f);
        scopeEight.setPosition(CAMERA_WIDTH / 4 * 3 - scopeEight.getWidth() / 2, nomalGun.getHeight() / 5 * 1);
        gunFrame1 = new Sprite(new Texture("gun_frame1.png"), 44, 256);
        gunFrame1.setSize(44 * 1.3f, 256 * 1.3f);
        gunFrame1.setPosition(CAMERA_WIDTH / 4 * 3 - nomalGun.getWidth() / 2, 0);
        gunFrame2 = new Sprite(new Texture("gun_frame2.png"), 44, 256);
        gunFrame2.setSize(44 * 1.3f, 256 * 1.3f);
        gunFrame2.setPosition(CAMERA_WIDTH / 4 * 3 - nomalGun.getWidth() / 2, 0);
        magazine = new Sprite(new Texture("magazine.png"), 64, 43);
        magazine.setSize(64 * 1.3f, 43 * 1.3f);
        magazine.setPosition(CAMERA_WIDTH / 4 * 3 - magazine.getWidth(), nomalGun.getHeight() / 5 * 2);

        //ボタン
            //8xScope
        TextureRegion scope8xRegion = new TextureRegion(new Texture("8x_scope_button.png"), 600, 100);
        Button.ButtonStyle scope8xButtonStyle = new Button.ButtonStyle();
        scope8xButtonStyle.up = new TextureRegionDrawable(scope8xRegion);
        scope8xButtonStyle.down = new TextureRegionDrawable(scope8xRegion);
        Button scope8xButton = new Button(scope8xButtonStyle);
        scope8xButton.setSize(600 / 4, 100 / 4);
        scope8xButton.setPosition(CAMERA_WIDTH / 15, CAMERA_HEIGHT / 10 * 5);
        scope8xButton.addListener(new ButtonInputListener(scope8xButton, 0.96f){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                super.touchUp(event, x, y, pointer, button);
                Rectangle startRect = new Rectangle(0, 0, mButton.getWidth(), mButton.getHeight());
                if(startRect.contains(x, y)){
                    if(mPrefs.getInteger("8xSCOPE", 0) == 0){
                        checkPurchaseState = CHECK_PURCHASE_SCOPE;
                        checkPurchase(500);
                    }
                    if(mPrefs.getInteger("8xSCOPE", 0) == 1){
                        mPrefs.putInteger("8xSCOPE", 2);
                        mPrefs.putInteger("ACCURACY", mPrefs.getInteger("ACCURACY", 1) + 1);
                    }
                    if(mPrefs.getInteger("8xSCOPE", 0) == 2){
                        mPrefs.putInteger("8xSCOPE", 1);
                        mPrefs.putInteger("ACCURACY", mPrefs.getInteger("ACCURACY", 1) - 1);
                    }
                    mPrefs.flush();
                }
            }
        });
            //バイポッド
        TextureRegion bipodRegion = new TextureRegion(new Texture("bipod_button.png"), 600, 100);
        Button.ButtonStyle bipodButtonStyle = new Button.ButtonStyle();
        bipodButtonStyle.up = new TextureRegionDrawable(bipodRegion);
        bipodButtonStyle.down = new TextureRegionDrawable(bipodRegion);
        Button bipodButton = new Button(bipodButtonStyle);
        bipodButton.setSize(600 / 4, 100 / 4);
        bipodButton.setPosition(CAMERA_WIDTH / 15, CAMERA_HEIGHT / 10 * 4);
        bipodButton.addListener(new ButtonInputListener(bipodButton, 0.96f){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                super.touchUp(event, x, y, pointer, button);
                Rectangle startRect = new Rectangle(0, 0, mButton.getWidth(), mButton.getHeight());
                if(startRect.contains(x, y)){
                    if(mPrefs.getInteger("BIPOD", 0) == 0){
                        checkPurchaseState = CHECK_PURCHASE_BIPOD;
                        checkPurchase(500);
                    }
                    if(mPrefs.getInteger("BIPOD", 0) == 1){
                        mPrefs.putInteger("BIPOD", 2);
                        mPrefs.putInteger("ACCURACY", mPrefs.getInteger("ACCURACY", 1) + 2);
                    }
                    if(mPrefs.getInteger("BIPOD", 0) == 2){
                        mPrefs.putInteger("BIPOD", 1);
                        mPrefs.putInteger("ACCURACY", mPrefs.getInteger("ACCURACY", 1) - 2);
                    }
                    mPrefs.flush();
                }
            }
        });
            //マガジン
        TextureRegion magazineRegion = new TextureRegion(new Texture("magazine_button.png"), 600, 100);
        Button.ButtonStyle magazineButtonStyle = new Button.ButtonStyle();
        magazineButtonStyle.up = new TextureRegionDrawable(magazineRegion);
        magazineButtonStyle.down = new TextureRegionDrawable(magazineRegion);
        Button magazineButton = new Button(magazineButtonStyle);
        magazineButton.setSize(600 / 4, 100 / 4);
        magazineButton.setPosition(CAMERA_WIDTH / 15, CAMERA_HEIGHT / 10 * 3);
        magazineButton.addListener(new ButtonInputListener(magazineButton, 0.96f){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                super.touchUp(event, x, y, pointer, button);
                Rectangle startRect = new Rectangle(0, 0, mButton.getWidth(), mButton.getHeight());
                if(startRect.contains(x, y)){
                    if(mPrefs.getInteger("MAGAZINE", 0) == 0){
                        checkPurchaseState = CHECK_PURCHASE_MGAZINE;
                        checkPurchase(800);
                    }
                    if(mPrefs.getInteger("MAGAZINE", 0) == 1){
                        mPrefs.putInteger("MAGAZINE", 2);
                        mPrefs.putInteger("RATE OF FIRE", mPrefs.getInteger("RATE OF FIRE", 3) + 1);
                    }
                    if(mPrefs.getInteger("MAGAZINE", 0) == 2){
                        mPrefs.putInteger("MAGAZINE", 1);
                        mPrefs.putInteger("RATE OF FIRE", mPrefs.getInteger("RATE OF FIRE", 3) - 1);
                    }
                    mPrefs.flush();
                }
            }
        });
            //フレーム
        TextureRegion frameRegion = new TextureRegion(new Texture("frame_button.png"), 600, 100);
        Button.ButtonStyle frameButtonStyle = new Button.ButtonStyle();
        frameButtonStyle.up = new TextureRegionDrawable(frameRegion);
        frameButtonStyle.down = new TextureRegionDrawable(frameRegion);
        Button frameButton = new Button(frameButtonStyle);
        frameButton.setSize(600 / 4, 100 / 4);
        frameButton.setPosition(CAMERA_WIDTH / 15, CAMERA_HEIGHT / 10 * 2);
        frameButton.addListener(new ButtonInputListener(frameButton, 0.96f){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                super.touchUp(event, x, y, pointer, button);
                Rectangle startRect = new Rectangle(0, 0, mButton.getWidth(), mButton.getHeight());
                if(startRect.contains(x, y)){
                    if(mPrefs.getInteger("METAL FRAME", 0) == 0){
                        checkPurchaseState = CHECK_PURCHASE_METAL_FRAME;
                        checkPurchase(700);
                    }
                    if(mPrefs.getInteger("METAL FRAME", 0) == 1){
                        mPrefs.putInteger("METAL FRAME", 2);
                        mPrefs.putInteger("MUZZLE VELOCITY", mPrefs.getInteger("MUZZLE VELOCITY", 2) + 2);
                    }
                    if(mPrefs.getInteger("METAL FRAME", 0) == 2){
                        mPrefs.putInteger("METAL FRAME", 1);
                        mPrefs.putInteger("MUZZLE VELOCITY", mPrefs.getInteger("MUZZLE VELOCITY", 2) - 2);
                    }
                    mPrefs.flush();
                }
            }
        });



        //ステータス
        gunState = new Sprite(new Texture("gun_state.png"), 150, 90);
        gunState.setPosition(CAMERA_WIDTH / 10 * 1, CAMERA_HEIGHT / 5 * 3);
        gunStateGageSpr = new Sprite(new Texture("gun_state_gage.png"), 30, 30);

        //ステージ
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        stage.addActor(backButton);
        stage.addActor(scope8xButton);
        stage.addActor(bipodButton);
        stage.addActor(magazineButton);
        stage.addActor(frameButton);


        //checkPurchase
        checkPurchaseBg = new Sprite(new Texture("buttonbackground.png"), 600, 400);
        checkPurchaseBg.setSize(CAMERA_WIDTH / 6 * 5, CAMERA_HEIGHT / 3 * 1);
        checkPurchaseBg.setPosition(CAMERA_WIDTH / 2 - checkPurchaseBg.getWidth() / 2, CAMERA_HEIGHT / 2 - checkPurchaseBg.getHeight() / 2);
        //purchase Stage
        TextureRegion endRegion = new TextureRegion(new Texture("monkeynobutton.png"), 420, 420);
        Button.ButtonStyle endButtonStyle = new Button.ButtonStyle();
        endButtonStyle.up = new TextureRegionDrawable(endRegion);
        endButtonStyle.down = new TextureRegionDrawable(endRegion);
        Button endButton = new Button(endButtonStyle);
        endButton.setSize(50, 50);
        endButton.setPosition(checkPurchaseBg.getX() + checkPurchaseBg.getWidth() - 60, checkPurchaseBg.getY() + checkPurchaseBg.getHeight() - 60);
        endButton.addListener(new ButtonInputListener(endButton, 0.96f){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                super.touchUp(event, x, y, pointer, button);
                Rectangle endRect = new Rectangle(0, 0, mButton.getWidth(), mButton.getHeight());
                if(endRect.contains(x, y)){
                    mState = STATE_NOMAL;
                }
            }
        });

        TextureRegion purchaseRegion = new TextureRegion(new Texture("monkeyokbutton.png"), 420, 420);
        Button.ButtonStyle purchaseStyle = new Button.ButtonStyle();
        purchaseStyle.up = new TextureRegionDrawable(purchaseRegion);
        purchaseStyle.down = new TextureRegionDrawable(purchaseRegion);
        Button purchaseButton = new Button(purchaseStyle);
        purchaseButton.setSize(50, 50);
        purchaseButton.setPosition(checkPurchaseBg.getX() + checkPurchaseBg.getWidth() / 2 - purchaseButton.getWidth() /2, checkPurchaseBg.getY() + 20);
        purchaseButton.addListener(new ButtonInputListener(purchaseButton, 0.96f){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                super.touchUp(event, x, y, pointer, button);
                Rectangle purchaseRect = new Rectangle(0, 0, mButton.getWidth(), mButton.getHeight());
                if(purchaseRect.contains(x, y)){
                    if(coinPrefs.getInteger("COIN", 0) >= customPrice){
                        coinPrefs.putInteger("COIN", coinPrefs.getInteger("COIN", 0) - customPrice);
                        coinPrefs.flush();
                        switch(checkPurchaseState){
                            case CHECK_PURCHASE_BIPOD:
                                mPrefs.putInteger("BIPOD", 2);
                                break;
                            case CHECK_PURCHASE_SCOPE:
                                mPrefs.putInteger("8xSCOPE", 2);
                                break;
                            case CHECK_PURCHASE_MGAZINE:
                                mPrefs.putInteger("MAGAZINE", 2);
                                break;
                            case CHECK_PURCHASE_METAL_FRAME:
                                mPrefs.putInteger("METAL FRAME", 2);
                                break;
                        }
                        mPrefs.flush();
                        mState = STATE_NOMAL;
                    }
                }
            }
        });

        purchaseStage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        purchaseStage.addActor(endButton);
        purchaseStage.addActor(purchaseButton);

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(mState == STATE_NOMAL){
            Gdx.input.setInputProcessor(stage);
            Matrix4 cameraMatrix = stage.getViewport().getCamera().combined;
            mGame.batch.setProjectionMatrix(cameraMatrix);
        }
        stage.act();
        if(mState == STATE_CHECK_PURCHASE){
            Gdx.input.setInputProcessor(purchaseStage);
            Matrix4 cameraMatrix = purchaseStage.getViewport().getCamera().combined;
            mGame.batch.setProjectionMatrix(cameraMatrix);
        }
        purchaseStage.act();


        mGame.batch.begin();
        mBg.draw(mGame.batch);
        mFont.getData().setScale(1);
        mFont.draw(mGame.batch, "COIN:" + coinPrefs.getInteger("COIN", 0), 60, 430);
        //shop
        if(mPrefs.getInteger("BIPOD", 0) == 2){
            bipod.draw(mGame.batch);
        }
        nomalGun.draw(mGame.batch);
        if(mPrefs.getInteger("METAL FRAME", 0) == 2){
            gunFrame1.draw(mGame.batch);
            gunFrame2.draw(mGame.batch);
        }
        if(mPrefs.getInteger("8xSCOPE", 0) == 2){
            scopeEight.draw(mGame.batch);
        }
        if(mPrefs.getInteger("MAGAZINE", 0) == 2){
            magazine.draw(mGame.batch);
        }
        gunStateDraw();
        mGame.batch.end();

        stage.draw();

        mGame.batch.begin();
        //state
        gunState.draw(mGame.batch);
        if(mState == STATE_CHECK_PURCHASE){
            checkPurchaseBg.draw(mGame.batch);
            mFont.getData().setScale(1.2f);
            mFont.draw(mGame.batch, "$" + customPrice, checkPurchaseBg.getX() + 50, checkPurchaseBg.getY() + 120);
        }
        mGame.batch.end();

        if(mState == STATE_CHECK_PURCHASE){
            purchaseStage.draw();
        }

    }

    public void gunStateDraw(){
        //muzzle velocity
        gunStateGageSpr.setSize(30 * mPrefs.getInteger("MUZZLE VELOCITY", 2), 30);
        gunStateGageSpr.setPosition(gunState.getX(), gunState.getY() + gunState.getHeight() / 3 * 2);
        gunStateGageSpr.draw(mGame.batch);
        //accuracy
        gunStateGageSpr.setSize(30 * mPrefs.getInteger("ACCURACY", 1), 30);
        gunStateGageSpr.setPosition(gunState.getX(), gunState.getY() + gunState.getHeight() / 3);
        gunStateGageSpr.draw(mGame.batch);
        //rate of fire
        gunStateGageSpr.setSize(30 * mPrefs.getInteger("RATE OF FIRE", 3), 30);
        gunStateGageSpr.setPosition(gunState.getX(), gunState.getY());
        gunStateGageSpr.draw(mGame.batch);
    }

    private void checkPurchase(int price){
        mState = STATE_CHECK_PURCHASE;
        customPrice = price;
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
        purchaseStage.getViewport().update(width, height);
        //       stageDrawable.getViewport().update(width, height);
    }
}
