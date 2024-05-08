package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.objects.JoyStick;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class InGameScene extends Scene {
    private static final String TAG = InGameScene.class.getSimpleName();
    private Dogcat player;

    private final JoyStick joyStick;

    RectF bgRect = new RectF(0, 0, Metrics.width, Metrics.height);
    RectF controlRect = new RectF(0, 0, Metrics.width, Metrics.height);

    private static int[] UI_ALLY_IDS = new int[] {
            R.mipmap.ui_ally_01_rat,
            R.mipmap.ui_ally_02_rabbit,
            R.mipmap.ui_ally_03_bear,
            R.mipmap.ui_ally_04_turtle,
            R.mipmap.ui_ally_05_rhinoceros,
            R.mipmap.ui_ally_06_penguin,
            R.mipmap.ui_ally_07_dragon,
    };

    public enum Layer {
        bg, player, enemy, friendly, controller, UI, touch, COUNT
    }

    public InGameScene(int stage) {
        // Layer 초기화
        initLayers(Layer.COUNT);

        // Background 생성
        add(Layer.bg, new Background(stage));

        // Unit Generator 생성
        add(Layer.controller, new EnemyGenerator());
        //add(Layer.controller, new CollisionChecker());

        // joyStick 초기화
        this.joyStick = new JoyStick();
        add(Layer.controller, joyStick);

        // Cost System 초기화
        add(Layer.controller, new CostGenerator());

        // player 초기화
        this.player = Dogcat.get(joyStick);
        add(Layer.player, player);

        // UI 생성
        add(Layer.UI, new Background(R.mipmap.scene03_ui_background));

        // Button 생성
        createAllyButton();
    }

    private void createAllyButton()
    {
        for(int i=0; i<UI_ALLY_IDS.length; i++)
        {
            add(InGameScene.Layer.touch, new Button(UI_ALLY_IDS[i], i * 1.6f + 1.1f, 6.7f, 1.6f, 1.6f, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {

                    return true;
                }
            }));
        }
    }

    @Override
    protected void onStart() {
        Sound.playMusic(R.raw.main);
    }

    @Override
    protected void onEnd() {
        Sound.stopMusic();
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
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
