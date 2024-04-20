package kr.ac.tukorea.luh.uihwan.framework.scene;

import android.graphics.Canvas;

import java.util.ArrayList;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.luh.uihwan.framework.activity.GameActivity;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;

public class Scene {

    private static final String TAG = Scene.class.getSimpleName();
    private static ArrayList<Scene> stack = new ArrayList<>();

    public static Scene top() {
        int top = stack.size() - 1;
        if (top < 0) {
            Log.e(TAG, "Scene Stack is empty in Scene.top()");
            return null;
        }
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
        if (scene == null) {
            Log.i(TAG, "Last scene is being popped");
            finishActivity();
            return;
        }
        scene.onEnd();
        stack.remove(scene);

        scene = top();
        if (scene == null) return;
        scene.onResume();
    }

    public static void finishActivity() {
        //GameView gameView = null;
        //gaveView.getActivity().finish();
        GameActivity.activity.finish();
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

    public boolean onBackPressed() {
        return false;
    }
}
