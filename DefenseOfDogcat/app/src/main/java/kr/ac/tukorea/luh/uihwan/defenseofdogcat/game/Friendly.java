package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.RectF;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IRecyclable;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.RecycleBin;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Friendly extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float ANIM_FPS = 10.0f;
    private int hp, maxHp;
    private int atk;

    public enum FriendlyType {
        rat, rabbit, bear, kangaroo, turtle, rhinoceros, penguin, dragon;
        int getId() { return resIds[this.ordinal()]; }
        int getHP() { return resHP[this.ordinal()]; }
        int getATK() { return resATK[this.ordinal()]; }
        float getSpeed() { return resSpeed[this.ordinal()]; }
        float getCoolTime() { return resCoolTime[this.ordinal()]; }
        int getFrameCount(int state) { return resFrame[this.ordinal()][state]; }
        static final int[] resIds = {
                R.mipmap.unit_01_rat_animation_sheet, R.mipmap.unit_02_rabbit_animation_sheet, R.mipmap.unit_03_bear_animation_sheet,
                R.mipmap.unit_04_turtle_animation_sheet, R.mipmap.unit_05_rhinoceros_animation_sheet, R.mipmap.unit_06_penguin_animation_sheet,
                R.mipmap.unit_07_dragon_animation_sheet,
        };
        static final int[] resHP = { 20, 15, 40, 50, 70, 90, 150, };
        static final int[] resATK = { 5, 8,	10,	20,	50,	60,	100, };
        static final float[] resSpeed =  { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f };
        static final float[] resCoolTime = { 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, };
        static final int[][] resFrame = { {3, 2, 1}, {4, 5, 1}, {4, 4, 1}, {4, 6, 1}, {3, 4, 1}, {4, 4, 1}, {5, 4, 1}};
    }

    private float maxAttackCoolTime;
    private float attackCoolTime;
    private float dyingTime = 0.5f;

    private Friendly.FriendlyType type;

    private Friendly(FriendlyType type, int frameCount) {
        super(type.getId(), ANIM_FPS, frameCount);
        init(type);
    }
    public static Friendly get(FriendlyType type, int frameCount) {
        Friendly friendly = (Friendly) RecycleBin.get(Friendly.class);
        if (friendly != null) {
            friendly.init(type);
            return friendly;
        }
        return new Friendly(type, frameCount);
    }

    private void init(FriendlyType type)
    {
        this.type = type;
        setState(State.walking);
        this.hp = this.maxHp = type.getHP();
        this.atk = type.getATK();
        this.maxAttackCoolTime = type.getCoolTime();
        this.attackCoolTime = type.getCoolTime();
        setPosition(1.0f, 4.0f, 1.5f, 1.5f);
        setSpeed(type.getSpeed());
        setBitmap(type.getId());
    }

    @Override
    public void update(float elapsedSeconds) {
        if (dstRect.right > Metrics.width) {
            Scene.top().remove(InGameScene.Layer.friendly, this);
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
                    Scene.top().remove(InGameScene.Layer.friendly, this);
                }
                break;
        }
    }

    public boolean attack(float elapsedSeconds, Enemy enemy)
    {
        InGameScene scene = (InGameScene) Scene.top();
        if (scene == null) return false;
        attackCoolTime -= elapsedSeconds;
        if (attackCoolTime > 0) return false;

        attackCoolTime = this.maxAttackCoolTime;

        return enemy.decreaseLife(atk);
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
