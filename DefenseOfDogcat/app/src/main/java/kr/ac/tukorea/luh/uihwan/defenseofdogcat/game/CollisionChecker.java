package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update(float elapsedSeconds) {
        Scene scene = Scene.top();
        if (scene == null) return;

        ArrayList<IGameObject> friendlyes = scene.objectsAt(InGameScene.Layer.friendly);
        for (int e = friendlyes.size() - 1; e >= 0; e--) {
            Friendly friendly = (Friendly)friendlyes.get(e);
            ArrayList<IGameObject> bullets = scene.objectsAt(InGameScene.Layer.player);
            for (int b = bullets.size() - 1; b >= 0; b--) {
                Dogcat player = (Dogcat)bullets.get(b);
                if (CollisionHelper.collides(friendly, player)) {
                    Log.d(TAG, "Collision !!");
                    //scene.remove(InGameScene.Layer.player.ordinal(), player);
                    //scene.remove(InGameScene.Layer.friendly.ordinal(), friendly);
//                    removed = true;
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}