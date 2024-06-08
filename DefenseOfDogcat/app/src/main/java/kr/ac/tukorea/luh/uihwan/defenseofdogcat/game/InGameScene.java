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
import kr.ac.tukorea.luh.uihwan.framework.objects.Score;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.res.Sound;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class InGameScene extends Scene {
    private static final String TAG = InGameScene.class.getSimpleName();
    private Dogcat player;

    private final JoyStick joyStick;
    private Cost costbar;

    private FriendlyGenerator friendlyGenerator;

    public static int currentStage = 0;
    public static int clearStage = 0;

    RectF bgRect = new RectF(0, 0, Metrics.width, Metrics.height);
    RectF controlRect = new RectF(0, 0, Metrics.width, Metrics.height);

    public enum StageType {
        stage1, stage2, stage3;
        int getId() { return STAGE_IDS[this.ordinal()]; }
        int getHP() { return STAGE_BOSS_HP[this.ordinal()]; }
        int getAlly() { return STAGE_ALLY_COUNT[this.ordinal()]; }

        static int[] STAGE_IDS = new int[] {
                R.mipmap.scene03_background_type_1,
                R.mipmap.scene03_background_type_2,
                R.mipmap.scene03_background_type_3,
        };

        static int[] STAGE_BOSS_HP = new int[] {
                1000, 2000, 3000,
        };
        static int[] STAGE_ALLY_COUNT = new int[] {
                5, 6, 7,
        };
    }

    private static int[] UI_ALLY_IDS = new int[] {
            R.mipmap.ui_ally_01_rat,
            R.mipmap.ui_ally_02_rabbit,
            R.mipmap.ui_ally_03_bear,
            R.mipmap.ui_ally_04_turtle,
            R.mipmap.ui_ally_05_rhinoceros,
            R.mipmap.ui_ally_06_penguin,
            R.mipmap.ui_ally_07_dragon,
            R.mipmap.ui_ally_08_lock,
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

    public StageType getTypeAtIndex(int index) {
        InGameScene.StageType[] types = InGameScene.StageType.values();
        if (index >= 0 && index < types.length) {
            return types[index];
        } else {
            throw new IllegalArgumentException("Invalid index");
        }
    }

    StageType stage;
    Score score; // package private
    Score cost;

    public InGameScene(int stage) {
        // Layer 초기화
        initLayers(Layer.COUNT);

        InGameScene.currentStage = stage;

        this.stage = getTypeAtIndex(stage);

        // joyStick 초기화
        this.joyStick = new JoyStick();
        add(Layer.controller, joyStick);

        // player 초기화
        this.player = Dogcat.get(joyStick);
        add(Layer.player, player);

        // Background 생성
        add(Layer.bg, new Background(this.stage.getId()));

        // Cost UI 생성
        Score MAX_COST = new Score(R.mipmap.number_24x32, 6.0f, 4.8f, 0.4f);
        MAX_COST.setScore(200);
        add(Layer.UI, MAX_COST);
        cost = new Score(R.mipmap.number_24x32, 4.6f, 4.8f, 0.4f);
        cost.setScore(0);
        add(Layer.UI, cost);
        costbar = new Cost(R.mipmap.ui_cost, 0.9f, 5.1f, 0.0f, 1.0f, cost);
        add(Layer.UI, costbar);

        // Cost System 초기화
        add(Layer.controller, new CostGenerator(costbar));

        // Button 생성
        createAllyButton();
        createPauseButton();

        // HP UI 생성
        HP player_hp = new HP(R.mipmap.ui_hp_ally, 5.5f, -0.19f, 2.3f, 1.0f, 100f, true, this);
        HP boss_hp = new HP(R.mipmap.ui_hp_enemy, 8.2f, -0.19f, 2.3f, 1.0f, this.stage.getHP(), false, this);
        add(Layer.UI, player_hp);
        add(Layer.UI, boss_hp);

        // UI 생성
        add(Layer.UI, new Background(R.mipmap.scene03_ui_background));
        add(Layer.UI, new Background(R.mipmap.ui_state));

        // Score
        this.score = new Score(R.mipmap.gold_number, 14.0f, 0.1f, 0.4f);
        score.setScore(0);
        add(Layer.UI, score);

        // Boss 생성
        add(Layer.boss, new Boss(this.stage.getHP()));
        add(Layer.UI, new Background(R.mipmap.bose_background));

        // Unit Generator 생성
        add(Layer.controller, new EnemyGenerator(stage, player_hp));
        friendlyGenerator = new FriendlyGenerator(costbar, boss_hp, this.stage.getAlly());
        add(Layer.controller, new CollisionChecker(this));
    }

    public void addScore(int amount) {
        score.add(amount);
    }

    private void createAllyButton()
    {
        for(int i=0; i<9; i++)
        {
            int finalI = i;
            finalI = (i < this.stage.getAlly()) ? i : 7;
            int finalI1 = finalI;
            add(InGameScene.Layer.touch, new Button(UI_ALLY_IDS[finalI1], i * 1.6f + 1.1f, 6.7f, 1.6f, 1.6f, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    friendlyGenerator.spawnFriendly(finalI1);
                    return true;
                }
            }));
        }
    }

    private void createPauseButton()
    {
        add(InGameScene.Layer.touch, new Button(R.mipmap.ui_bt_pause, 15.5f, 0.4f, 1.6f, 1.6f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new PauseScene().push();
                return true;
            }
        }));
    }

    public void stageClear()
    {
        InGameScene.clearStage += 1;
        new EndScene(this.score.getScore()).push();
    }

    public void stageFail()
    {
        new FailScene().push();
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

    enum PauseLayer {
        bg, touch, COUNT
    }

    @Override
    public boolean onBackPressed() {
        new Scene() {
            @Override
            protected void onStart() {
                initLayers(PauseLayer.COUNT);
                Sprite bg = new Sprite(R.mipmap.trans_50p);
                float w = Metrics.width, h = Metrics.height;
                bg.setPosition(w/2, h/2, w, h);
                add(PauseLayer.bg, bg);
                add(PauseLayer.touch, new Button(UI_ALLY_IDS[0], 5.0f, 0.0f, 1.6f, 1.6f, new Button.Callback() {
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
        }.push();
        return true;
    }

    public boolean pause()
    {
        new Scene() {
            @Override
            protected void onStart() {
                initLayers(PauseLayer.COUNT);
                Sprite bg = new Sprite(R.mipmap.trans_50p);
                float w = Metrics.width, h = Metrics.height;
                bg.setPosition(w/2, h/2, w, h);
                add(PauseLayer.bg, bg);
                add(PauseLayer.touch, new Button(UI_ALLY_IDS[0], 5.0f, 0.0f, 1.6f, 1.6f, new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
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
            public boolean onTouch(MotionEvent event) {
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());

                return super.onTouch(event);
            }
        }.push();
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return InGameScene.Layer.touch.ordinal();
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());

        if(pts[1] > 5.0f || pts[1] < 0.2f)  return super.onTouch(event);
        else return joyStick.onTouch(event);
    }
}
