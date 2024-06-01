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

    private final int[][] resScore = { {1500, 2000}, {2000, 2500}, {2500, 3000} };

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
                LobbyScene.setStageBitmap();
                pop();
                pop();
                Log.d(TAG, "Touch Button");
                return true;
            }
        }));

        calcScore(score);
    }

    private void calcScore(int score)
    {
        if(score >= resScore[InGameScene.currentStage][0])
        {
            Sprite star = new Sprite(R.mipmap.ui_star);
            add(Layer.ui, star);
            star.setPosition(8.0f, 5.5f, 3.3f, 3.3f);
        }
        if(score >= resScore[InGameScene.currentStage][1])
        {
            Sprite star = new Sprite(R.mipmap.ui_star);
            add(Layer.ui, star);
            star.setPosition(10.5f, 5.5f, 3.3f, 3.3f);
        }
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected void onStart() {
        Sound.playMusic(R.raw.victory);
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
