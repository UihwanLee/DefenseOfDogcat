package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;

import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class FriendlyGenerator implements IGameObject {

    private Cost cost;
    private HP bossHP;
    private final int MAX_ALLY;

    public FriendlyGenerator(Cost cost, HP bossHP, int max_ally)
    {
        this.cost = cost;
        this.bossHP = bossHP;
        this.MAX_ALLY = max_ally;
    }

    @Override
    public void update(float elapsedSeconds) {

    }

    private void generateFriendly()
    {
        Scene.top().add(InGameScene.Layer.friendly, Friendly.get(Friendly.FriendlyType.rat, 3, bossHP));
    }

    public void spawnFriendly(int id)
    {
        if(id > MAX_ALLY) return;

        Friendly.FriendlyType type = Friendly.getTypeAtIndex(id);

        if(cost.canSpawn(type.getCost()))
        {
            Scene.top().add(InGameScene.Layer.friendly, Friendly.get(type, 3, bossHP));
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
