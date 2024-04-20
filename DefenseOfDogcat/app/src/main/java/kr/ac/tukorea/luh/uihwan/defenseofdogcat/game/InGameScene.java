package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

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

    public enum Layer {
        enemy, friendly, player, controller, COUNT
    }

    public InGameScene(int stage) {

        bgBitmap = BitmapPool.get(stage);
        controlBitmap = BitmapPool.get(R.mipmap.scene03_ui_background);

        // Layer 초기화
        initLayers(Layer.COUNT);

        // Unit Generator 생성
        add(Layer.controller, new UnitGenerator());

        // joyStick 초기화
        this.joyStick = new JoyStick();
        add(Layer.controller, joyStick);

        // player 초기화
        this.player = Dogcat.get(joyStick);
        add(Layer.player, player);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        checkCollision();
    }

    private void checkCollision() {
        ArrayList<IGameObject> friedlyes = layers.get(Layer.friendly.ordinal());
        for (int e = friedlyes.size() - 1; e >= 0; e--) {
            Friendly friendly = (Friendly)friedlyes.get(e);
            ArrayList<IGameObject> bullets = layers.get(Layer.player.ordinal());
            for (int b = bullets.size() - 1; b >= 0; b--) {
                Dogcat dogcat = (Dogcat)bullets.get(b);
                if (CollisionHelper.collides(friendly, dogcat)) {
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
