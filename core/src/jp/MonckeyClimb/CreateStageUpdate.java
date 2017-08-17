package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static jp.MonckeyClimb.PlayScreen.CAMERA_HEIGHT;
import static jp.MonckeyClimb.PlayScreen.CAMERA_WIDTH;
import static jp.MonckeyClimb.PlayScreen.MONKEY_Y;
import static jp.MonckeyClimb.PlayScreen.mCamera;
import static jp.MonckeyClimb.PlayScreen.stopCount;

public class CreateStageUpdate {

    public static final float space = CAMERA_HEIGHT / 10;
    public static final int TREE_MATERIAL_1_WIDTH = 8;
    public static final int TREE_MATERIAL_1_HEIGHT = 16;
    public static final int TREE_MATERIAL_2_WIDTH = 5;
    public static final int TREE_MATERIAL_2_HEIGHT = 8;
    public static final int TREE_MATERIAL_3_WIDTH = 12;
    public static final int TREE_MATERIAL_3_HEIGHT = 15;
    public static final int TREE_BODY_WIDTH = 16;
    public static final int TREE_BODY_HEIGHT = 64;

    List<TreeMaterial> mTreeMaterial = new ArrayList<TreeMaterial>();
    List<Tree> mTrees = new ArrayList<Tree>();
    List<Coin> mCoin = new ArrayList<Coin>();
    List<Log> mLogsLeft = new ArrayList<Log>();
    List<Log> mLogsRight = new ArrayList<Log>();
    List<Bikkuri> mBikkurisLeft = new ArrayList<Bikkuri>();
    List<Bikkuri> mBikkurisRight = new ArrayList<Bikkuri>();

    int coinCount = 0;
    int treecount = 0;
    int treecount2 = 0;
    int treeMaterialCount = 0;
    int treeMaterialCount2 = 0;
    int logCount = 0;

    float coinRange = CAMERA_WIDTH / 10 * 7 - CAMERA_WIDTH / 10 * 3 + Tree.TREE_WIDTH / 2;

    Random random = new Random();

    Texture treeMaterial1LeftTexture = new Texture("tree_material_1_left.png");
    Texture treeMaterial1RightTexture = new Texture("tree_material_1_right.png");
    Texture treeMaterial2LeftTexture = new Texture("tree_material_2_left.png");
    Texture treeMaterial2RightTexture = new Texture("tree_material_2_right.png");
    Texture treeMaterial3LeftTexture = new Texture("tree_material_3_left.png");
    Texture treeMaterial3RightTexture = new Texture("tree_material_3_right.png");
    Texture treeTexture = new Texture("tree_material_body.png");
    Texture coinTexture = new Texture("coin2.png");
    Texture logTextureLeft = new Texture("log2.png");
    Texture logTextureRight = new Texture("log.png");
    Texture bikkuriRTexture = new Texture("bikkuriRight.png");
    Texture bikkuriLTexture = new Texture("bikkuriLeft.png");

    public void Create(){
        logCount++;

        //登るのをやめた場合1秒でコインの湧き停止
        stopCount++;

        //木
        if(mCamera.position.y + CAMERA_HEIGHT / 2 + space > treecount * Tree.TREE_HEIGHT){
            treecount = tree(CAMERA_WIDTH / 10 * 3, treecount);
        }
        if(mCamera.position.y + CAMERA_HEIGHT / 2 + space > treecount2 * Tree.TREE_HEIGHT){
            treecount2 = tree(CAMERA_WIDTH / 10 * 7, treecount2);
        }

        //木の部品
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treeMaterialCount * 100){
            treeMaterialCount = treeMaterialdraw(CAMERA_WIDTH / 10 * 3, treeMaterialCount);
        }
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treeMaterialCount2 * 100){
            treeMaterialCount2 = treeMaterialdraw(CAMERA_WIDTH / 10 * 7, treeMaterialCount2);
        }

        //コイン
        if (stopCount < 60){
            coinCount++;
            coinCreate();
        }

        logdraw();
    }

     public void Createfirst(){

        for (int i = 0; i < Math.round(CAMERA_HEIGHT / Tree.TREE_HEIGHT) + 1; i++){
            treecount = tree(CAMERA_WIDTH / 10 * 3, treecount);
        }

        for (int i = 0; i < Math.round(CAMERA_HEIGHT / Tree.TREE_HEIGHT) + 1; i++){
            treecount2 = tree(CAMERA_WIDTH / 10 * 7, treecount2);
        }

        for (int i = 0; i < Math.round(CAMERA_HEIGHT / 100) + 1; i++){
            treeMaterialCount = treeMaterialdraw(CAMERA_WIDTH / 10 * 3, treeMaterialCount);
        }
        for (int i = 0; i < Math.round(CAMERA_HEIGHT / 100) + 1; i++){
            treeMaterialCount2 = treeMaterialdraw(CAMERA_WIDTH / 10 * 7, treeMaterialCount2);
        }
    }

    private int treeMaterialdraw(float position, int count) {
        int ntm = random.nextInt(6);
        switch (ntm){
            case 0:
                TreeMaterial treeMaterial1Left = new TreeMaterial(treeMaterial1LeftTexture, 0, 0, TREE_MATERIAL_1_WIDTH, TREE_MATERIAL_1_HEIGHT);
                treeMaterial1Left.setSize(TREE_MATERIAL_1_WIDTH * 4, TREE_MATERIAL_1_HEIGHT * 4);
                treeMaterial1Left.setPosition(position - Tree.TREE_WIDTH / 2 - treeMaterial1Left.getWidth(), count * 100);
                mTreeMaterial.add(treeMaterial1Left);
                break;
            case 1:
                TreeMaterial treeMaterial1Right = new TreeMaterial(treeMaterial1RightTexture, 0, 0, TREE_MATERIAL_1_WIDTH, TREE_MATERIAL_1_HEIGHT);
                treeMaterial1Right.setSize(TREE_MATERIAL_1_WIDTH * 4, TREE_MATERIAL_1_HEIGHT * 4);
                treeMaterial1Right.setPosition(position + Tree.TREE_WIDTH / 2, count * 100);
                mTreeMaterial.add(treeMaterial1Right);
                break;
            case 2:
                TreeMaterial treeMaterial2Left = new TreeMaterial(treeMaterial2LeftTexture, 0, 0, TREE_MATERIAL_2_WIDTH, TREE_MATERIAL_2_HEIGHT);
                treeMaterial2Left.setSize(TREE_MATERIAL_2_WIDTH * 4, TREE_MATERIAL_2_HEIGHT * 4);
                treeMaterial2Left.setPosition(position - Tree.TREE_WIDTH / 2 - treeMaterial2Left.getWidth(), count * 100);
                mTreeMaterial.add(treeMaterial2Left);
                break;
            case 3:
                TreeMaterial treeMaterial2Right = new TreeMaterial(treeMaterial2RightTexture, 0, 0, TREE_MATERIAL_2_WIDTH, TREE_MATERIAL_2_HEIGHT);
                treeMaterial2Right.setSize(TREE_MATERIAL_2_WIDTH * 4, TREE_MATERIAL_2_HEIGHT * 4);
                treeMaterial2Right.setPosition(position + Tree.TREE_WIDTH / 2, count * 100);
                mTreeMaterial.add(treeMaterial2Right);
                break;
            case 4:
                TreeMaterial treeMaterial3Left = new TreeMaterial(treeMaterial3LeftTexture, 0, 0, TREE_MATERIAL_3_WIDTH, TREE_MATERIAL_3_HEIGHT);
                treeMaterial3Left.setSize(TREE_MATERIAL_3_WIDTH * 3.5f, TREE_MATERIAL_3_HEIGHT * 3.5f);
                treeMaterial3Left.setPosition(position - Tree.TREE_WIDTH / 2 - treeMaterial3Left.getWidth(), count * 100);
                mTreeMaterial.add(treeMaterial3Left);
                break;
            case 5:
                TreeMaterial treeMaterial3Right = new TreeMaterial(treeMaterial3RightTexture, 0, 0, TREE_MATERIAL_3_WIDTH, TREE_MATERIAL_3_HEIGHT);
                treeMaterial3Right.setSize(TREE_MATERIAL_3_WIDTH * 3.5f, TREE_MATERIAL_3_HEIGHT * 3.5f);
                treeMaterial3Right.setPosition(position + Tree.TREE_WIDTH / 2, count * 100);
                mTreeMaterial.add(treeMaterial3Right);
                break;

        }
        count++;
        return count;
    }

    private int tree(float position, int count){
        Tree tree = new Tree(treeTexture, 0, 0, TREE_BODY_WIDTH, TREE_BODY_HEIGHT);
        tree.setPosition(position - Tree.TREE_WIDTH / 2, count * Tree.TREE_HEIGHT);
        mTrees.add(tree);
        count++;
        return count;
    }

    private void coinCreate(){
        if (coinCount == 60){
            float nc = random.nextInt((int) coinRange);
            Coin coin = new Coin(coinTexture, 0, 0, 25, 25);
            coin.setPosition(CAMERA_WIDTH / 10 * 3 - Tree.TREE_WIDTH / 2 + nc, mCamera.position.y + CAMERA_HEIGHT / 2 + space);
            mCoin.add(coin);
            coinCount = 0;
        }
    }

    private void logdraw(){
        if(logCount == 60){
            int n = random.nextInt(2);
            int n2 = random.nextInt(383 + 200) + 1;
            float n3 = random.nextInt(200) + 100;
            if (n == 0){
                Log log = new Log(logTextureLeft, 0, 0, 30, 12);
                log.setPosition(0 - Log.LOG_WIDTH - CAMERA_WIDTH / 2, MONKEY_Y + Monkey.MONKEY_HEIGHT + n2);
                Bikkuri bikkuri = new Bikkuri(bikkuriLTexture, 0, 0, 62, 49);
                bikkuri.setPosition(0, log.getY() + Log.LOG_HEIGHT / 2 - Bikkuri.BIKKURI_HEIGHT / 2);
                log.accelaccel = n3;
                mLogsLeft.add(log);
                mBikkurisLeft.add(bikkuri);
            } else if (n == 1){
                Log log = new Log(logTextureRight , 0, 0, 30, 12);
                log.setPosition(CAMERA_WIDTH + Log.LOG_WIDTH + CAMERA_WIDTH / 2, MONKEY_Y + Monkey.MONKEY_HEIGHT + n2);
                Bikkuri bikkuri = new Bikkuri(bikkuriRTexture, 0, 0, 62, 49);
                bikkuri.setPosition(CAMERA_WIDTH - Bikkuri.BIKKURI_WIDTH, log.getY() + Log.LOG_HEIGHT / 2 - Bikkuri.BIKKURI_HEIGHT / 2);
                log.accelaccel = -n3;
                mLogsRight.add(log);
                mBikkurisRight.add(bikkuri);
            }
            logCount = 0;
        }
    }
}
