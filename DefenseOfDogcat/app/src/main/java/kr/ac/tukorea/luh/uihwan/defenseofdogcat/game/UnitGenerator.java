package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;

public class UnitGenerator implements IGameObject {
    private float enemyTime = 0;
    @Override
    public void update(float elapsedSeconds) {
        enemyTime -= elapsedSeconds;
        if (enemyTime < 0) {
            generateFriendly();
            enemyTime = 5.0f;
        }
    }

    private void generateFriendly()
    {
        Scene.top().add(InGameScene.Layer.friendly, Friendly.get(0, 3));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
