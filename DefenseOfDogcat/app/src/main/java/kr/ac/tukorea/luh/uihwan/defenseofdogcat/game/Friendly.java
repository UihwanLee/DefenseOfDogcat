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
    private static final int[] resIds = {
            R.mipmap.unit_01_rat_animation_sheet, R.mipmap.unit_02_rabbit_animation_sheet, R.mipmap.unit_03_bear_animation_sheet,
            R.mipmap.unit_04_turtle_animation_sheet, R.mipmap.unit_05_rhinoceros_animation_sheet, R.mipmap.unit_06_penguin_animation_sheet,
            R.mipmap.unit_07_dragon_animation_sheet,
    };

    private static final int[] resHP = {
            20,	15,	40,	50,	70,	90,	150,
    };

    private static final int[] resATK = {
            5,	8,	10,	20,	50,	60,	100
    };

    public static final float[] resSpeed =  {
            1.0f, 0.5f, 1.0f,
    };

    private Friendly(int index, int frameCount) {
        super(resIds[index], ANIM_FPS, frameCount);
        init(index);
    }
    public static Friendly get(int index, int frameCount) {
        Friendly friendly = (Friendly) RecycleBin.get(Friendly.class);
        if (friendly != null) {
            friendly.init(index);
            return friendly;
        }
        return new Friendly(index, frameCount);
    }

    private void init(int index)
    {
        setState(State.walking);
        this.hp = this.maxHp = resHP[index];
        this.atk = resATK[index];
        setPosition(1.0f, 4.0f, 1.5f, 1.5f);
        setSpeed(resSpeed[index]);
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
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }
}
