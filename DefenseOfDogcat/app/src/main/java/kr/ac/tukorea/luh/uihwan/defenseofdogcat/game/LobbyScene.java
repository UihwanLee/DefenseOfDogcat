package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.util.Log;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class LobbyScene extends Scene {
    private static final String TAG = Scene.class.getSimpleName();
    public enum Layer {
        bg, bg2, touch, COUNT
    }

    private static Button stage1, stage2, stage3;

    private final int[][] resStage = { {R.mipmap.scene02_stage02_lock, R.mipmap.scene02_stage02}, {R.mipmap.scene02_stage03_lock, R.mipmap.scene02_stage03_lock} };

    public LobbyScene() {
        initLayers(Layer.COUNT);

        // Background 생성
        add(Layer.bg2, new Background(R.mipmap.scene02_stage_select));

        // Stage 생성
        stage1 = new Button(R.mipmap.scene02_stage01, 8.0f, 5.0f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if(false == checkStage(0)) return true;

                new InGameScene(0).push();
                add(StartScene.Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        });

        stage2 = new Button(R.mipmap.scene02_stage02_lock, 5.3f, 2.7f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if(false == checkStage(1)) return true;

                new InGameScene(1).push();
                add(Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        });

        stage3 = new Button(R.mipmap.scene02_stage03_lock, 12.1f, 2.1f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if(false == checkStage(2)) return true;

                new InGameScene(2).push();
                add(Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        });

        add(Layer.touch, stage1);
        add(Layer.touch, stage2);
        add(Layer.touch, stage3);
    }

    public static void setStageBitmap()
    {
        if (InGameScene.clearStage >= 1) {
            stage2.setBitmap(R.mipmap.scene02_stage02);
        }
        if (InGameScene.clearStage >= 2) {
            stage3.setBitmap(R.mipmap.scene02_stage03);
        }
    }

    private boolean checkStage(int stage)
    {
        return (InGameScene.clearStage >= stage);
    }

    @Override
    protected void onStart() {
        Sound.playMusic(R.raw.menu);
    }

    @Override
    protected void onPause() {
        Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        Sound.resumeMusic();
    }

    @Override
    protected void onEnd() {
        Sound.stopMusic();
    }

    @Override
    protected int getTouchLayerIndex() {
        return LobbyScene.Layer.touch.ordinal();
    }
}
