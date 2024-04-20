package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IRecyclable;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Friendly extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float SPEED = 1.0f;
    public static final float ANIM_FPS = 10.0f;
    private static final int[] resIds = {
            R.mipmap.unit_01_rat_animation_sheet, R.mipmap.unit_02_rabbit_animation_sheet, R.mipmap.unit_03_bear_animation_sheet,
    };

    protected static ArrayList<Friendly> objPool = new ArrayList<>();

    private Friendly(int index, int frameCount) {
        super(resIds[index], ANIM_FPS, frameCount);
        setPosition(1.0f, 4.0f, 1.5f, 1.5f);
        dx = SPEED;
    }
    public static Friendly get(int index, int frameCount) {
        if (!objPool.isEmpty()) {
            Friendly friendly = objPool.remove(0);
            friendly.setPosition(1.0f, 4.0f, 1.5f, 1.5f);
            return friendly;
        }
        return new Friendly(index, frameCount);
    }

    public void addToPool() {
        objPool.add(this);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.right > Metrics.width) {
            Scene.top().remove(this);
            objPool.add(this);
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
