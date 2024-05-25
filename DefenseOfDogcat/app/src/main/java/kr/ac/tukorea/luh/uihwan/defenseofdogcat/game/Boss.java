package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.RectF;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.res.BitmapPool;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Boss extends Sprite implements IBoxCollidable {

    private int hp;
    protected RectF collisionRect = new RectF();

    public Boss(int hp)
    {
        super(R.mipmap.bose);
        float height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setPosition(Metrics.width / 2, Metrics.height / 2, Metrics.width, height);

        this.hp = hp;

        // 별도의 collisionRect
        collisionRect.set(dstRect);
        collisionRect.set(x + 3.8f, y - radius, x + radius, y + radius - 4.0f);
    }

    public boolean decreaseLife(int atk) {
        hp -= atk;
        return hp <= 0;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
