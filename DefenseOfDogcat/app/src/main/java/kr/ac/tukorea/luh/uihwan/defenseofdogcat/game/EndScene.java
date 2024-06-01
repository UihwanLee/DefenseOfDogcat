package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.util.Log;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.objects.Score;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class EndScene extends Scene {
    private static final String TAG = EndScene.class.getSimpleName();
    private Score score;

    enum Layer {
        bg, ui, touch, COUNT
    }

    public EndScene(int score)
    {
        initLayers(Layer.COUNT);

        add(Layer.bg, new Background(R.mipmap.trans_50p));

        add(Layer.ui, new Background(R.mipmap.ui_endgame));

        // Score
        this.score = new Score(R.mipmap.gold_number, 14.0f, 3.0f, 1f);
        this.score.setScore(0);
        add(Layer.ui, this.score);
        this.score.add(score);

        add(Layer.touch, new Button(R.mipmap.ui_endgame_bt_next, 12.0f, 8.0f, 5f, 1.6f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pop();
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
