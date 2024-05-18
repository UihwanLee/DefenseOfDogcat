package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.scene.RecycleBin;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class CostGenerator implements IGameObject {
    private final float MAX_TIME = 1.0f;
    private float updateTime = MAX_TIME;
    private final int COST_PER_INCREASE = 20;

    private Paint cosPaint;

    private Cost cost;

    public CostGenerator(Cost cost) {
        cosPaint = new Paint();
        cosPaint.setColor(Color.BLUE);
        cosPaint.setTextSize(100f);
        this.cost = cost;
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
        cost.increaseCost(COST_PER_INCREASE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText("COST: " + cost, 100f, 200f, cosPaint);
    }
}
