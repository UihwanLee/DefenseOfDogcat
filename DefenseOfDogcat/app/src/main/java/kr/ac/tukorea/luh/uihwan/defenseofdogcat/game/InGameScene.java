package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.objects.Background;
import kr.ac.tukorea.luh.uihwan.framework.objects.Button;
import kr.ac.tukorea.luh.uihwan.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.luh.uihwan.framework.objects.JoyStick;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class InGameScene extends Scene {
    private static final String TAG = InGameScene.class.getSimpleName();
    private Dogcat player;

    private final JoyStick joyStick;
    private Cost cost;

    private FriendlyGenerator friendlyGenerator;

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

    private static int[] STAGE_IDS = new int[] {
            R.mipmap.scene03_background_type_1,
            R.mipmap.scene03_background_type_2,
            R.mipmap.scene03_background_type_3,
    };

    private static int[] STAGE_BOSS_HP = new int[] {
            1000, 2000, 3000,
    };

    public enum Layer {
        bg, boss, player, enemy, friendly, controller, UI, touch, COUNT
    }

    public InGameScene(int stage) {
        // Layer 초기화
        initLayers(Layer.COUNT);

        // joyStick 초기화
        this.joyStick = new JoyStick();
        add(Layer.controller, joyStick);

        // player 초기화
        this.player = Dogcat.get(joyStick);
        add(Layer.player, player);

        // Background 생성
        add(Layer.bg, new Background(STAGE_IDS[stage]));

        // Cost UI 생성
        cost = new Cost(R.mipmap.ui_cost, 0.9f, 5.1f, 0.0f, 1.0f);
        add(Layer.UI, cost);

        // Cost System 초기화
        add(Layer.controller, new CostGenerator(cost));

        // Button 생성
        createAllyButton();

        // HP UI 생성
        HP player_hp = new HP(R.mipmap.ui_hp_ally, 5.5f, -0.19f, 2.3f, 1.0f, 100f, true);
        HP boss_hp = new HP(R.mipmap.ui_hp_enemy, 8.2f, -0.19f, 2.3f, 1.0f, STAGE_BOSS_HP[stage], false);
        add(Layer.UI, player_hp);
        add(Layer.UI, boss_hp);

        // UI 생성
        add(Layer.UI, new Background(R.mipmap.scene03_ui_background));
        add(Layer.UI, new Background(R.mipmap.ui_state));

        // Boss 생성
        add(Layer.boss, new Boss(STAGE_BOSS_HP[stage]));

        // Unit Generator 생성
        add(Layer.controller, new EnemyGenerator(stage, player_hp));
        friendlyGenerator = new FriendlyGenerator(cost, boss_hp);
        add(Layer.controller, new CollisionChecker());
    }

    private void createAllyButton()
    {
        for(int i=0; i<UI_ALLY_IDS.length; i++)
        {
            int finalI = i;
            add(InGameScene.Layer.touch, new Button(UI_ALLY_IDS[i], i * 1.6f + 1.1f, 6.7f, 1.6f, 1.6f, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    friendlyGenerator.spawnFriendly(finalI);
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
    protected int getTouchLayerIndex() {
        return InGameScene.Layer.touch.ordinal();
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());

        if(pts[1] > 5.0f)  return super.onTouch(event);
        else return joyStick.onTouch(event);
    }
}
