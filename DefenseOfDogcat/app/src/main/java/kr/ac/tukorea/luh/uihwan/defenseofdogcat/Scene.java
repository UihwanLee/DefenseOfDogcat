package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.graphics.Canvas;

import java.util.ArrayList;
import android.view.MotionEvent;

public class Scene {

    protected static Scene currentScene;

    public static Scene top() {
        return currentScene;
    }
    public static void push(Scene scene) {
        currentScene = scene;
    }

    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    public void update(float elapsedSeconds) {
        for (IGameObject gameObject : gameObjects) {
            gameObject.update(elapsedSeconds);
        }
    }

    public void draw(Canvas canvas) {
        for (IGameObject gameObject : gameObjects) {
            gameObject.draw(canvas);
        }
    }

    public boolean onTouch(MotionEvent event) {
        return false;
    }
}
