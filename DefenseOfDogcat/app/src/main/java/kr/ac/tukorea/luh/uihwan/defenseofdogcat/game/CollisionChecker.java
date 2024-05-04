package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update(float elapsedSeconds) {
        Scene scene = Scene.top();
        if (scene == null) return;

        ArrayList<IGameObject> friendlies = scene.objectsAt(InGameScene.Layer.friendly);
        for (int e = friendlies.size() - 1; e >= 0; e--) {
            Friendly friendly = (Friendly)friendlies.get(e);
            ArrayList<IGameObject> enemies = scene.objectsAt(InGameScene.Layer.enemy);
            for (int b = enemies.size() - 1; b >= 0; b--) {
                Enemy enemy = (Enemy)enemies.get(b);
                if (CollisionHelper.collides(friendly, enemy)) {
                    Log.d(TAG, "Collision !!");
                    friendly.setState(AnimSprite.State.attacking);
                    enemy.setState(AnimSprite.State.attacking);

                    boolean enemyDead = friendly.attack(elapsedSeconds, enemy);
                    boolean friendlyDead = enemy.attack(elapsedSeconds, friendly);

                    if(enemyDead)
                    {
                        enemy.setState(AnimSprite.State.dying);
                    }
                    if(friendlyDead)
                    {
                        friendly.setState(AnimSprite.State.dying);
                    }
                    //scene.remove(InGameScene.Layer.player.ordinal(), player);
                    //scene.remove(InGameScene.Layer.friendly.ordinal(), friendly);
//                    removed = true;
                    break;
                }
                else
                {
                    friendly.setState(AnimSprite.State.walking);
                    enemy.setState(AnimSprite.State.walking);
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}