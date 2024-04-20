package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Friendly extends Sprite {
    private static final float SPEED = 1.0f;

    public Friendly(int index) {
        super(index);
        setPosition(1.0f, 2.0f, 1.5f, 1.5f);
        dx = SPEED;
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.right > Metrics.width) {
            Scene.top().remove(this);
        }
    }
}
