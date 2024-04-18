package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class JoyStick implements IGameObject {
    private final Bitmap bgBitmap;
    private final Bitmap thumbBitmap;

    private static final float CENTER_X = 3.5f;
    private static final float CENTER_Y = 5.0f;
    private static final float BG_RADIUS = 1.5f;
    private static final float THUMB_RADIUS = 0.5f;

    private final RectF bgRect = new RectF();
    private final RectF thumbRect = new RectF();
    public JoyStick() {
        bgBitmap = BitmapPool.get(R.mipmap.joystick_bg);
        thumbBitmap = BitmapPool.get(R.mipmap.joystick_thumb);

        bgRect.set(CENTER_X - BG_RADIUS, CENTER_Y - BG_RADIUS, CENTER_X + BG_RADIUS, CENTER_Y + BG_RADIUS);
        thumbRect.set(CENTER_X - THUMB_RADIUS, CENTER_Y - THUMB_RADIUS, CENTER_X + THUMB_RADIUS, CENTER_Y + THUMB_RADIUS);
    }

    @Override
    public void update(float elapsedSeconds) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(thumbBitmap, null, thumbRect, null);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
        }
        return false;
    }
}