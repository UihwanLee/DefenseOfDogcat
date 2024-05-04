package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;

import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class FriendlyGenerator implements IGameObject {

    @Override
    public void update(float elapsedSeconds) {

    }

    private void generateFriendly()
    {
        Scene.top().add(InGameScene.Layer.friendly, Friendly.get(0, 3));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
