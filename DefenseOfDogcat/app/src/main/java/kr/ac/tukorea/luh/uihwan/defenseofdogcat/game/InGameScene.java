package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.objects.JoyStick;
import kr.ac.tukorea.luh.uihwan.framework.res.BitmapPool;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.util.CollisionHelper;
import kr.ac.tukorea.luh.uihwan.framework.view.Metrics;

public class InGameScene extends Scene {
    private static final String TAG = InGameScene.class.getSimpleName();
    private Dogcat player;
    private Bitmap bgBitmap;
    private Bitmap controlBitmap;

    private final JoyStick joyStick;

    RectF bgRect = new RectF(0, 0, Metrics.width, Metrics.height);
    RectF controlRect = new RectF(0, 0, Metrics.width, Metrics.height);

    public InGameScene(int stage) {

        bgBitmap = BitmapPool.get(stage);
        controlBitmap = BitmapPool.get(R.mipmap.scene03_ui_background);

        // Unit Generator 생성
        add(new UnitGenerator());

        // joyStick 초기화
        this.joyStick = new JoyStick();
        add(joyStick);

        // player 초기화
        this.player = new Dogcat(joyStick);
        add(player);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        checkCollision();
    }

    private void checkCollision() {
        for (IGameObject o1 : gameObjects) {
            if (!(o1 instanceof Friendly)) {
                continue;
            }
            Friendly enemy = (Friendly) o1;
//            boolean removed = false;
            for (IGameObject o2 : gameObjects) {
                if (!(o2 instanceof Dogcat)) {
                    continue;
                }
                Dogcat dogcat = (Dogcat) o2;
                if (CollisionHelper.collides(enemy, dogcat)) {
                    Log.d(TAG, "Collision !!");
                    remove(enemy);
//                    removed = true;
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
        canvas.drawBitmap(controlBitmap, null, controlRect, null);
        super.draw(canvas);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
