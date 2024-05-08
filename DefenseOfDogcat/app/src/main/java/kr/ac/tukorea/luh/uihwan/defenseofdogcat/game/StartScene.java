package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class StartScene extends Scene {
    private static final String TAG = Scene.class.getSimpleName();
    private static int[] STAGE_IDS = new int[] {
            R.mipmap.scene03_background_type_1,
            R.mipmap.scene03_background_type_2,
            R.mipmap.scene03_background_type_3,
    };
    public enum Layer {
        bg, touch, COUNT
    }
    public StartScene() {
        initLayers(Layer.COUNT);

        // Background 생성
        add(StartScene.Layer.bg, new Background(R.mipmap.scene01_startpage_background));

        // Button 생성
        add(Layer.touch, new Button(R.mipmap.scene01_startpage_background, 5.0f, 8.0f, 2.0f, 2.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new InGameScene(STAGE_IDS[0]).push();
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
        return Layer.touch.ordinal();
    }
}
