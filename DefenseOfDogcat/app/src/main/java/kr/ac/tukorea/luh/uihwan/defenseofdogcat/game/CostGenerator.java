package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.scene.RecycleBin;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class CostGenerator implements IGameObject {
    private final float MAX_TIME = 2.0f;
    private float updateTime = MAX_TIME;
    private int cost = 0;
    private final int COST_PER_INCREASE = 10;

    private Paint cosPaint;

    public CostGenerator() {
        cosPaint = new Paint();
        cosPaint.setColor(Color.BLUE);
        cosPaint.setTextSize(100f);
    }

    @Override
    public void update(float elapsedSeconds) {
        updateTime -= elapsedSeconds;
        if (updateTime < 0) {
            increaseCost();
            updateTime = MAX_TIME;
        }
    }

    private void increaseCost()
    {
        cost += COST_PER_INCREASE;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText("COST: " + cost, 100f, 200f, cosPaint);
    }
}
