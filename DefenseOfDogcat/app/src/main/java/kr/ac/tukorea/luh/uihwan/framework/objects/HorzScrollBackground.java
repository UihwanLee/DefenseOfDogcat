package kr.ac.tukorea.luh.uihwan.framework.objects;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.Dogcat;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.InGameScene;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class HorzScrollBackground extends Sprite {

    private static final String TAG = HorzScrollBackground.class.getSimpleName();
    private Dogcat player;
    float maxScrollX = 25f;
    private float scrollX;

    public HorzScrollBackground(int bitmapResId, Dogcat player) {
        super(bitmapResId);
        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        setPosition(Metrics.width / 2, Metrics.height / 2, width, Metrics.height);
        this.player = player;
    }
    @Override
    public void update(float elapsedSeconds) {
        // player의 위치에 따른 clamp 처리와 맵 이동 처리

        // 플레이어의 x 위치를 기반으로 배경을 스크롤
        float playerX = player.getX();
        float halfWidth = Metrics.width / 2; // 8.0f

        // 배경의 스크롤 값을 클램프하여 월드의 경계를 벗어나지 않도록 함
        scrollX = clamp(playerX - halfWidth, 0, maxScrollX);
        setPosition(-scrollX, Metrics.height / 2, width, Metrics.height);
        Log.d(TAG, "x: " + scrollX);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        float curr = x % width;
        if (curr > 0) curr -= width;
        while (curr < Metrics.width) {
            dstRect.set(curr, 0, curr + width, Metrics.height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }
}