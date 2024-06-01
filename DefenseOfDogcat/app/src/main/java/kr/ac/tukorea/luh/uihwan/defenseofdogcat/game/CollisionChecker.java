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
    private final InGameScene scene;

    public CollisionChecker(InGameScene scene) {
        this.scene = scene;
    }

    @Override
    public void update(float elapsedSeconds) {
        ArrayList<IGameObject> friendlies = scene.objectsAt(InGameScene.Layer.friendly);
        for (int e = friendlies.size() - 1; e >= 0; e--) {
            Friendly friendly = (Friendly)friendlies.get(e);
            ArrayList<IGameObject> enemies = scene.objectsAt(InGameScene.Layer.enemy);
            ArrayList<IGameObject> bosses = scene.objectsAt(InGameScene.Layer.boss);
            for (int h = bosses.size() - 1; h >= 0; h--) {
                Boss boss = (Boss)bosses.get(h);
                if (CollisionHelper.collides(friendly, boss)) {
                    friendly.setState(AnimSprite.State.attacking);

                    boolean bossDead = friendly.attack(elapsedSeconds, boss);

                    if(bossDead)
                    {
                        // 게임 끝
                        Log.d(TAG, "END");
                        Scene.top().remove(InGameScene.Layer.boss, boss);
                        scene.clearStage();
                    }
                }
            }
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
                        // 점수 증가
                        scene.addScore(enemy.getScore());
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