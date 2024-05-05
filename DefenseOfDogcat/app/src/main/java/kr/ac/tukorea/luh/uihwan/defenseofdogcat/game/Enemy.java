package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IRecyclable;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.RecycleBin;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Enemy extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float ANIM_FPS = 10.0f;
    private int hp, maxHp;
    private int atk;
    private float maxAttackCoolTime;
    private float attackCoolTime;

    private float dyingTime = 0.5f;

    public enum EnemyType {
        zombie, pirate, ninja, witch, frankenstein;

        int getId() { return resIds[this.ordinal()]; }
        int getHP() { return resHP[this.ordinal()];}
        int getATK() { return resATK[this.ordinal()]; }
        float getSpeed() { return resSpeed[this.ordinal()]; }
        float getCoolTime() { return resCoolTime[this.ordinal()]; }
        int getFrameCount(int state) { return resFrame[this.ordinal()][state]; }
        static final int[] resIds = {
                R.mipmap.enemy_01_zomibe_animation_sheet, R.mipmap.enemy_02_skeleton_pirate_animation_sheet, R.mipmap.enemy_03_skeleton_ninja_animation_sheet,
                R.mipmap.enemy_04_witch_animation_sheet, R.mipmap.enemy_05_frankenstein_animation_sheet,
        };
        static final int[] resHP = { 10, 8, 30, 70, 120, };
        static final int[] resATK = { 5, 8, 20, 30, 50, };
        static final float[] resSpeed =  { -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, };
        static final float[] resCoolTime = { 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, };
        static final int[][] resFrame = { {5, 3, 1}, {3, 3, 1}, {2, 3, 1}, {4, 2, 1}, {5, 4, 1}};
    }
    private EnemyType type;

    public static EnemyType getRandomEnemyType() {
        EnemyType[] values = EnemyType.values();
        Random random = new Random();
        return values[random.nextInt(values.length)];
    }

    private Enemy(EnemyType type, int frameCount) {
        super(type.getId(), ANIM_FPS, frameCount);
        init(type);
    }
    public static Enemy get(EnemyType type, int frameCount) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.init(type);
            return enemy;
        }
        return new Enemy(type, frameCount);
    }

    private void init(EnemyType type)
    {
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
        }

        processState(elapsedSeconds);
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
}
