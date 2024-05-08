package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class LobbyScene extends Scene {
    private static final String TAG = Scene.class.getSimpleName();
    private static int[] STAGE_IDS = new int[] {
            R.mipmap.scene03_background_type_1,
            R.mipmap.scene03_background_type_2,
            R.mipmap.scene03_background_type_3,
    };
    public enum Layer {
        bg, touch, COUNT
    }
    public LobbyScene() {
        initLayers(StartScene.Layer.COUNT);

        // Background 생성
        add(StartScene.Layer.bg, new Background(R.mipmap.scene02_stage_select));

        // Stage 생성
        add(StartScene.Layer.touch, new Button(R.mipmap.scene02_stage01, 8.0f, 5.0f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new InGameScene(STAGE_IDS[0]).push();
                add(StartScene.Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        }));

        add(StartScene.Layer.touch, new Button(R.mipmap.scene02_stage02, 5.3f, 2.7f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new InGameScene(STAGE_IDS[1]).push();
                add(StartScene.Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        }));

        add(StartScene.Layer.touch, new Button(R.mipmap.scene02_stage03, 12.1f, 2.1f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new InGameScene(STAGE_IDS[2]).push();
                add(StartScene.Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        }));
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
