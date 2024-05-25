package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;

public class EnemyGenerator implements IGameObject {

    private static int[][] levels = new int[][] {
            { 1, 2, 3, },
            { 2, 3, 4, },
            { 3, 4, 5, },
    };

    private HP playerHP;
    private float spawnTime = 0;
    private int[] level;

    public EnemyGenerator(int level, HP playerHP) {
        this.level = levels[level];
        this.playerHP = playerHP;
    }

    @Override
    public void update(float elapsedSeconds) {
        spawnTime -= elapsedSeconds;
        if (spawnTime < 0) {
            generateEnemy();
            spawnTime = 5.0f;
        }
    }

    private void generateEnemy()
    {
        // level에 따른 몬스터 배치
        Enemy.EnemyType type = Enemy.getEnemyTypeByLevel(this.level);
        Scene.top().add(InGameScene.Layer.enemy, Enemy.get(type, 5, playerHP));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
