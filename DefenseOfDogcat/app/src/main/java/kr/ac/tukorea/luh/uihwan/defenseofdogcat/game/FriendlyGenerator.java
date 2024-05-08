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
        Scene.top().add(InGameScene.Layer.friendly, Friendly.get(Friendly.FriendlyType.rat, 3));
    }

    public void spawnFriendly(int id)
    {
        Friendly.FriendlyType type = Friendly.getTypeAtIndex(id);
        Scene.top().add(InGameScene.Layer.friendly, Friendly.get(type, 3));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
