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
    private static final int[] resIds = {
            R.mipmap.unit_01_rat_animation_sheet, R.mipmap.unit_02_rabbit_animation_sheet, R.mipmap.unit_03_bear_animation_sheet,
    };

    public static final float[] resSpeed =  {
            1.0f, 0.5f, 1.0f,
    };

    private Friendly(int index, int frameCount) {
        super(resIds[index], ANIM_FPS, frameCount);
        setPosition(1.0f, 4.0f, 1.5f, 1.5f);
        setSpeed(resSpeed[index]);
    }
    public static Friendly get(int index, int frameCount) {
        Friendly friendly = (Friendly) RecycleBin.get(Friendly.class);
        if (friendly != null) {
            friendly.setBitmap(resIds[index]);
            friendly.setSpeed(resSpeed[index]);
            friendly.setPosition(1.0f, 4.0f, 1.5f, 1.5f);
            return friendly;
        }
        return new Friendly(index, frameCount);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.right > Metrics.width) {
            Scene.top().remove(InGameScene.Layer.friendly, this);
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
