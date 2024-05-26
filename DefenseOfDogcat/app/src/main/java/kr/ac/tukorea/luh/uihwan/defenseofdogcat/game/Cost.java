package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.tukorea.luh.uihwan.framework.objects.Score;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;

public class Cost extends Sprite {

    final float MAX_COST = 200.0f;
    float cost;
    Score value_cost;

    private static final String TAG = Cost.class.getSimpleName();

    public Cost(int mipmapId, float x, float y, float width, float height, Score value_cost) {
        super(mipmapId, x, y, width, height);
        cost = 0;

        this.value_cost = value_cost;
        dstRect.set(x, y, x + width, y + height);
    }

    public void increaseCost(float increase)
    {
        if(cost + increase > MAX_COST) return;

        cost += increase;

        value_cost.add((int)increase);

        setCost();
    }

    public boolean canSpawn(int cost)
    {
        if(this.cost >= cost)
        {
            decreaseCost(cost);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void decreaseCost(float decrease)
    {
        if(cost - decrease < 0.0f) return;

        cost -= decrease;

        value_cost.sub((int)decrease);

        setCost();
    }

    public void setCost()
    {
        if (cost <= 0)
            width = 0.0f;
            // cost 값이 100보다 크거나 같으면 width는 5.0f
        else if (cost >= MAX_COST)
            width = 5.0f;
            // 그 외의 경우, cost 값에 비례하여 width 값을 계산
        else
        {
            width = 5.0f * (cost / MAX_COST);
        }

        dstRect.set(x, y, x + width, y + height);
        //Log.d(TAG, "cost=" + cost + " width:" + width);
    }
}
