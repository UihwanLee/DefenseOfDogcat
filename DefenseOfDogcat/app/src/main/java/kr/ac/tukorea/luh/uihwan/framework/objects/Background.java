package kr.ac.tukorea.luh.uihwan.framework.objects;

import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class Background extends Sprite{
    public Background(int bitmapResId) {
        super(bitmapResId);
        float height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setPosition(Metrics.width / 2, Metrics.height / 2, Metrics.width, height);
    }
}
