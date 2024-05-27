package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

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
    public LobbyScene() {
        initLayers(Layer.COUNT);

        // Background 생성
        add(Layer.bg2, new Background(R.mipmap.scene02_stage_select));

        // Stage 생성
        add(Layer.touch, new Button(R.mipmap.scene02_stage01, 8.0f, 5.0f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new InGameScene(0).push();
                add(StartScene.Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.scene02_stage02, 5.3f, 2.7f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new InGameScene(1).push();
                add(Layer.bg, new Background(R.mipmap.scene03_background_type_1));
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.scene02_stage03, 12.1f, 2.1f, 3.0f, 3.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new InGameScene(2).push();
                add(Layer.bg, new Background(R.mipmap.scene03_background_type_1));
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
