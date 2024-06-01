package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.util.Log;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class PauseScene extends Scene {
    private static final String TAG = PauseScene.class.getSimpleName();

    enum Layer {
        bg, ui, touch, COUNT
    }

    public PauseScene()
    {
        initLayers(EndScene.Layer.COUNT);

        add(EndScene.Layer.bg, new Background(R.mipmap.trans_50p));

        add(EndScene.Layer.ui, new Background(R.mipmap.ui_option));

        add(EndScene.Layer.touch, new Button(R.mipmap.ui_bt_resume, 8.0f, 4.0f, 6f, 2.5f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pop();
                return true;
            }
        }));

        add(EndScene.Layer.touch, new Button(R.mipmap.ui_bt_exit, 8.0f, 6.5f, 6f, 2.5f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
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
        return EndScene.Layer.touch.ordinal();
    }
}
