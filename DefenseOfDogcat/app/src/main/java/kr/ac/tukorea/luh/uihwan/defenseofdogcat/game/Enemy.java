package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.RectF;

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
    private static final int[] resIds = {
            R.mipmap.enemy_01_zomibe_animation_sheet, R.mipmap.enemy_02_skeleton_pirate_animation_sheet, R.mipmap.enemy_03_skeleton_ninja_animation_sheet,
            R.mipmap.enemy_04_witch_animation_sheet, R.mipmap.enemy_05_frankenstein_animation_sheet,
    };

    private static final int[] resHP = {
            10, 8, 30, 70, 120,
    };

    private static final int[] resATK = {
            5, 8, 20, 30, 50,
    };

    public static final float[] resSpeed =  {
            -1.0f, -0.5f, -1.0f,
    };

    private Enemy(int index, int frameCount) {
        super(resIds[index], ANIM_FPS, frameCount);
        init(index);
    }
    public static Enemy get(int index, int frameCount) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.init(index);
            return enemy;
        }
        return new Enemy(index, frameCount);
    }

    private void init(int index)
    {
        this.hp = this.maxHp = resHP[index];
        this.atk = resATK[index];
        setPosition(16.0f, 4.0f, 1.5f, 1.5f);
        setSpeed(resSpeed[index]);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.left < 0) {
            Scene.top().remove(InGameScene.Layer.enemy, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }

    public boolean decreaseLife(int atk) {
        hp -= atk;
        return hp <= 0;
    }
}
