package kr.ac.tukorea.luh.uihwan.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.res.BitmapPool;

public class JoyStick implements IGameObject {
    private static final String TAG = JoyStick.class.getSimpleName();
    private final Bitmap bgBitmap;
    private final Bitmap thumbBitmap;

    private float CENTER_X = 3.5f;
    private float CENTER_Y = 5.0f;
    private float BG_RADIUS = 2.0f;
    private float THUMB_RADIUS = 1.0f;
    private static final float THUMB_MOVE_RADIUS = 1.5f;

    private final RectF bgRect = new RectF();
    private final RectF thumbRect = new RectF();

    private boolean visible;
    private float startX, startY;
    public float power, angle_radian;
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
        if (!visible) return;
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(thumbBitmap, null, thumbRect, null);
    }

    public boolean onTouch(MotionEvent event) {
        float[] pts;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                visible = true;
                pts = Metrics.fromScreen(event.getX(), event.getY());
                startX = pts[0];
                startY = pts[1];
                CENTER_X = startX;
                CENTER_Y = startY;
                bgRect.set(CENTER_X - BG_RADIUS, CENTER_Y - BG_RADIUS, CENTER_X + BG_RADIUS, CENTER_Y + BG_RADIUS);
                thumbRect.set(CENTER_X - THUMB_RADIUS, CENTER_Y - THUMB_RADIUS, CENTER_X + THUMB_RADIUS, CENTER_Y + THUMB_RADIUS);
                return true;
            case MotionEvent.ACTION_MOVE:
                visible = true;
                pts = Metrics.fromScreen(event.getX(), event.getY());
                float dx = Math.max(-BG_RADIUS, Math.min(pts[0] - startX, BG_RADIUS));
                float dy = Math.max(-BG_RADIUS, Math.min(pts[1] - startY, BG_RADIUS));
                double radius = Math.sqrt(dx * dx + dy * dy);
                angle_radian = (float) Math.atan2(dy, dx);

                if (radius > THUMB_MOVE_RADIUS) {
                    dx = (float) (THUMB_MOVE_RADIUS * Math.cos(angle_radian));
                    dy = (float) (THUMB_MOVE_RADIUS * Math.sin(angle_radian));
                    radius = THUMB_MOVE_RADIUS;
                }
                power = (float) (radius / THUMB_MOVE_RADIUS);

                float cx = CENTER_X + dx, cy = CENTER_Y + dy;
                Log.d(TAG, "sx="+startX+" sy="+startY+" dx="+dx + " dy="+dy);
                thumbRect.set(cx - THUMB_RADIUS, cy - THUMB_RADIUS, cx + THUMB_RADIUS, cy + THUMB_RADIUS);
                return true;
            case MotionEvent.ACTION_UP:
                visible = false;
                power = 0;
                return true;
        }
        return false;
    }
}