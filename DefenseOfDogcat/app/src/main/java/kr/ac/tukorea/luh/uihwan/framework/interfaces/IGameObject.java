package kr.ac.tukorea.luh.uihwan.framework.interfaces;

import android.graphics.Canvas;

public interface IGameObject {
    public void update(float elapsedSeconds);
    public void draw(Canvas canvas);
}