package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.graphics.Canvas;

public interface IGameObject {
    public void update(float elapsedSeconds);
    public void draw(Canvas canvas);
}