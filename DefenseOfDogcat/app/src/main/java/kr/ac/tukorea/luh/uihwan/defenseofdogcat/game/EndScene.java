package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.util.Log;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class EndScene extends Scene {
    private static final String TAG = EndScene.class.getSimpleName();

    enum EndLayer {
        bg, touch, COUNT
    }

    public EndScene()
    {

    }

    @Override
    protected void onStart() {
        initLayers(EndLayer.COUNT);
        Sprite bg = new Sprite(R.mipmap.trans_50p);
        float w = Metrics.width, h = Metrics.height;
        bg.setPosition(w/2, h/2, w, h);
        add(EndLayer.bg, bg);
        add(EndLayer.touch, new Button(R.mipmap.button_play, 8.0f, 5.0f, 1.6f, 1.6f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pop();
                Log.d(TAG, "Touch Button");
                return true;
            }
        }));
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
