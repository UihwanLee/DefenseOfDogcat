package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class FailScene extends Scene {
    private static final String TAG = EndScene.class.getSimpleName();

    enum Layer {
        bg, ui, touch, COUNT
    }

    public FailScene()
    {
        initLayers(EndScene.Layer.COUNT);

        add(EndScene.Layer.bg, new Background(R.mipmap.trans_50p));

        add(EndScene.Layer.ui, new Background(R.mipmap.scene03_mission_fail));
        add(EndScene.Layer.touch, new Button(R.mipmap.ui_endgame_bt_next, 13.5f, 8.0f, 5f, 1.6f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                LobbyScene.setStageBitmap();
                pop();
                pop();
                return true;
            }
        }));
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected void onStart() {
        Sound.playMusic(R.raw.fail);
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
        return FailScene.Layer.touch.ordinal();
    }
}
