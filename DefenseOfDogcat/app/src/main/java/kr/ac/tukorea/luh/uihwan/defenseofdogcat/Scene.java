package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.graphics.Canvas;

import java.util.ArrayList;
import android.view.MotionEvent;

public class Scene {

    private static ArrayList<Scene> stack = new ArrayList<>();

    public static Scene top() {
        int top = stack.size() - 1;
        if (top < 0) return null;
        return stack.get(top);
    }
    public static void push(Scene scene) {
        Scene prev = top();
        if (prev != null) {
            prev.onPause();
        }
        stack.add(scene);
        scene.onStart();
    }

    public void push() {
        push(this);
    }

    public static void pop() {
        Scene scene = top();
        if (scene == null) return;
        scene.onEnd();
        stack.remove(scene);

        scene = top();
        if (scene == null) return;
        scene.onResume();
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

    //////////////////////////////////////////////////
    // Overridables


    protected void onStart() {
    }
    protected void onEnd() {
    }

    protected void onPause() {
    }
    protected void onResume() {
    }
}
