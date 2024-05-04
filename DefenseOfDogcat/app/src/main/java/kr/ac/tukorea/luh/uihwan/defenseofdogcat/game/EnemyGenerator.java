package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;

import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;

public class EnemyGenerator implements IGameObject {
    private float spawnTime = 0;
    @Override
    public void update(float elapsedSeconds) {
        spawnTime -= elapsedSeconds;
        if (spawnTime < 0) {
            generateEnemy();
            generateFriendly();
            spawnTime = 5.0f;
        }
    }

    private void generateEnemy()
    {
        Scene.top().add(InGameScene.Layer.enemy, Enemy.get(Enemy.EnemyType.zombie, 5));
    }

    private void generateFriendly()
    {
        Scene.top().add(InGameScene.Layer.friendly, Friendly.get(Friendly.FriendlyType.rat, 3));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
