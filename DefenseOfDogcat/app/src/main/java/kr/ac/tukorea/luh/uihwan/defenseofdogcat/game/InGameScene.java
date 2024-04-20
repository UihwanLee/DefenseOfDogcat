package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
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
        this.player = Dogcat.get(joyStick);
        add(player);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        checkCollision();
    }

    private void checkCollision() {
        int count = gameObjects.size();
        for (int i1 = count - 1; i1 >= 0; i1--) {
            count = gameObjects.size();
            if (i1 >= count) {
                i1 = count - 1; // o1 와 o2 이 모두 삭제된 경우 count 가 더 많이 줄었을 수도 있다.
            }
            IGameObject o1 = gameObjects.get(i1);
            if (!(o1 instanceof Friendly)) {
                continue;
            }
            Friendly enemy = (Friendly) o1;
//            boolean removed = false;
            count = gameObjects.size();
            for (int i2 = count - 1; i2 >= 0; i2--) {
                IGameObject o2 = gameObjects.get(i2);
                if (!(o2 instanceof Dogcat)) {
                    continue;
                }
                Dogcat dogcat = (Dogcat) o2;
                if (CollisionHelper.collides(enemy, dogcat)) {
                    Log.d(TAG, "Collision !!");
                    //remove(enemy);
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
