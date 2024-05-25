package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;

public class HP extends Sprite {

    boolean isPlayer;
    final float MAX_HP;
    final float MAX_WIDTH;
    private float hp;

    public HP(int mipmapId, float x, float y, float width, float height, float hp, boolean isPlayer)
    {
        super(mipmapId, x, y, width, height);

        MAX_WIDTH = width;

        MAX_HP = hp;
        this.hp = hp;
        this.isPlayer = isPlayer;

        dstRect.set(x, y, x + width, y + height);
    }

    public void decreaseHP(float decrease)
    {
        if(hp - decrease < 0.0f) return;

        hp -= decrease;

        setHP();
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
            dstRect.set(x + width, y, x + MAX_HP, y + height);
        }
        else
        {
            dstRect.set(x, y, x + width, y + height);
        }
    }
}
