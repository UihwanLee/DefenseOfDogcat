package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class InGameScene extends Scene {
    private static final String TAG = InGameScene.class.getSimpleName();
    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private Dogcat player;
    private Bitmap bgBitmap;
    private Bitmap controlBitmap;

    RectF bgRect = new RectF(0, 0, Metrics.SCREEN_WIDTH, Metrics.SCREEN_HEIGHT);
    RectF controlRect = new RectF(0, 0, Metrics.SCREEN_WIDTH, Metrics.SCREEN_HEIGHT);

    public InGameScene(int stage) {

        bgBitmap = BitmapPool.get(stage);
        controlBitmap = BitmapPool.get(R.mipmap.scene03_ui_background);

        // 플레이어 초기화
        this.player = new Dogcat();
        gameObjects.add(player);
    }

    @Override
    public void update(float elapsedSeconds) {
        for (IGameObject gameObject : gameObjects) {
            gameObject.update(elapsedSeconds);
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(controlBitmap, null, controlRect, null);
        for (IGameObject gameObject : gameObjects) {
            gameObject.draw(canvas);
        }
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                player.setTargetPosition(pts[0], pts[1]);
                Log.d(TAG, "X Y = " + pts[0] + pts[1]);
                return true;
        }
        return false;
    }
}
