package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.RectF;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Friendly extends AnimSprite implements IBoxCollidable {
    private static final float SPEED = 1.0f;
    public static final float ANIM_FPS = 10.0f;
    private static final int[] resIds = {
            R.mipmap.unit_01_rat_animation_sheet, R.mipmap.unit_02_rabbit_animation_sheet, R.mipmap.unit_03_bear_animation_sheet,
    };

    public Friendly(int index, int frameCount) {
        super(resIds[index], ANIM_FPS, frameCount);
        setPosition(1.0f, 4.0f, 1.5f, 1.5f);
        dx = SPEED;
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.right > Metrics.width) {
            Scene.top().remove(this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
}
