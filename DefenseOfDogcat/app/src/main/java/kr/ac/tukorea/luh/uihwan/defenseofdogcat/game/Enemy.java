package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IRecyclable;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.RecycleBin;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.util.Gauge;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Enemy extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float ANIM_FPS = 10.0f;
    private float hp, maxHp;
    private int atk;
    private float maxAttackCoolTime;
    private float attackCoolTime;

    private float dyingTime = 0.5f;

    private HP playerHP;

    public enum EnemyType {
        zombie, pirate, ninja, witch, frankenstein;

        int getId() { return resIds[this.ordinal()]; }
        int getHP() { return resHP[this.ordinal()];}
        int getATK() { return resATK[this.ordinal()]; }
        float getSpeed() { return resSpeed[this.ordinal()]; }
        float getCoolTime() { return resCoolTime[this.ordinal()]; }
        int getFrameCount(int state) { return resFrame[this.ordinal()][state]; }
        int getScore() { return resScore[this.ordinal()]; }
        static final int[] resIds = {
                R.mipmap.enemy_01_zomibe_animation_sheet, R.mipmap.enemy_02_skeleton_pirate_animation_sheet, R.mipmap.enemy_03_skeleton_ninja_animation_sheet,
                R.mipmap.enemy_04_witch_animation_sheet, R.mipmap.enemy_05_frankenstein_animation_sheet,
        };
        static final int[] resHP = { 10, 8, 30, 70, 120, };
        static final int[] resATK = { 5, 8, 20, 30, 50, };
        static final float[] resSpeed =  { -1.0f, -0.8f, -1.3f, -1.2f, -0.5f, };
        static final float[] resCoolTime = { 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, };
        static final int[][] resFrame = { {5, 3, 1}, {3, 3, 1}, {2, 3, 1}, {4, 2, 1}, {5, 4, 1}};
        static final int[] resScore = { 100, 200, 300, 400, 500, };
    }
    private EnemyType type;
    private static Gauge gauge;

    public static EnemyType getRandomEnemyType() {
        EnemyType[] values = EnemyType.values();
        Random random = new Random();
        return values[random.nextInt(values.length)];
    }

    public static EnemyType getEnemyTypeByLevel(int[] level) {
        EnemyType[] values = EnemyType.values();
        Random random = new Random();
        // level 배열에서 무작위로 하나의 값을 선택합니다.
        int randomLevel = level[random.nextInt(level.length)];
        return values[randomLevel];
    }

    private Enemy(EnemyType type, int frameCount, HP playerHP) {
        super(type.getId(), ANIM_FPS, frameCount);
        init(type, playerHP);
    }
    public static Enemy get(EnemyType type, int frameCount, HP playerHP) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.init(type, playerHP);
            return enemy;
        }
        return new Enemy(type, frameCount, playerHP);
    }

    private void init(EnemyType type, HP playerHP)
    {
        this.playerHP = playerHP;
        this.type = type;
        setState(State.walking);
        this.hp = this.maxHp = type.getHP();
        this.atk = type.getATK();
        this.maxAttackCoolTime = type.getCoolTime();
        this.attackCoolTime = type.getCoolTime();
        setPosition(16.0f, 4.0f, 1.5f, 1.5f);
        setSpeed(type.getSpeed());
        setBitmap(type.getId());
    }

    @Override
    public void update(float elapsedSeconds) {
        if (dstRect.left < 0) {
            Scene.top().remove(InGameScene.Layer.enemy, this);

            // player hp decrease
            this.playerHP.decreaseHP(atk);
        }

        processState(elapsedSeconds);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        float size = width * 2 / 3;
        if (gauge == null) {
            gauge = new Gauge(0.2f, R.color.healthFg, R.color.healthBg);
        }
        gauge.draw(canvas, x - size / 2, y - size / 2 - 0.3f, size, hp / maxHp);
    }

    private void processState(float elapsedSeconds)
    {
        switch (state) {
            case walking:
                float timedDx = dx * elapsedSeconds;
                float timedDy = dy * elapsedSeconds;
                x += timedDx;
                y += timedDy;
                dstRect.offset(timedDx, timedDy);
                frameState = 0;
                break;
            case attacking:
                frameState = 1;
                break;
            case dying:
                frameState = 2;
                dyingTime -= elapsedSeconds;
                if(dyingTime > 0.0f)
                {
                    Scene.top().remove(InGameScene.Layer.enemy, this);
                }
                break;
        }
    }

    public boolean attack(float elapsedSeconds, Friendly friendly)
    {
        InGameScene scene = (InGameScene) Scene.top();
        if (scene == null) return false;
        attackCoolTime -= elapsedSeconds;
        if (attackCoolTime > 0) return false;

        attackCoolTime = this.maxAttackCoolTime;

        return friendly.decreaseLife(atk);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }

    public int getATK()
    {
        return atk;
    }

    public boolean decreaseLife(int atk) {
        hp -= atk;
        return hp <= 0;
    }

    public void setState(State state)
    {
        this.state = state;
        this.frameCount = this.type.getFrameCount(state.ordinal() - 1);
    }

    public int getScore()
    {
        return type.getScore();
    }
}
