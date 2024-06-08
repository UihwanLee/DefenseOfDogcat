package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

public class HP extends Sprite {

    boolean isPlayer;
    final float MAX_X;
    final float MAX_HP;
    final float MAX_WIDTH;
    private float hp;
    private InGameScene scene;

    public HP(int mipmapId, float x, float y, float width, float height, float hp, boolean isPlayer, InGameScene scene)
    {
        super(mipmapId, x, y, width, height);

        MAX_WIDTH = width;

        MAX_HP = hp;
        this.hp = hp;
        this.isPlayer = isPlayer;
        MAX_X = x + width;

        dstRect.set(x, y, x + width, y + height);

        this.scene = scene;
    }

    public void decreaseHP(float decrease)
    {
        checkPlayerDead(decrease);
        if(hp - decrease < 0.0f) return;

        hp -= decrease;

        setHP();
    }

    private void checkPlayerDead(float decrease)
    {
        if(isPlayer == false) return;

        if(hp - decrease < 0.0f)
        {
            // player 사망 미션 실패
            this.scene.stageFail();
        }
    }

    private void setHP()
    {
        if (hp <= 0)
            width = 0.0f;
        else if (hp >= MAX_HP)
            width = MAX_WIDTH;
        else
        {
            width = MAX_WIDTH * (hp / MAX_HP);
        }

        if (isPlayer)
        {
            dstRect.set(x + (MAX_WIDTH - width), y, MAX_X, y + height);
        }
        else
        {
            dstRect.set(x, y, x + width, y + height);
        }
    }

    public float getHP() { return hp; }
}
