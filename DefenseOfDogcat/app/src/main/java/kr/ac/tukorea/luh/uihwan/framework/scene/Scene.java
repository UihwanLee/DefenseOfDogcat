package kr.ac.tukorea.luh.uihwan.framework.scene;

import android.graphics.Canvas;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.BuildConfig;
import kr.ac.tukorea.luh.uihwan.framework.activity.GameActivity;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IRecyclable;

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
        if (scene == null) {
            Log.i(TAG, "Last scene is being popped");
            finishActivity();
            return;
        }
        scene.onResume();
    }

    public static void popAll() {
        int count = stack.size();
        for (int i = count - 1; i >= 0; i--) {
            Scene scene = stack.get(i);
            scene.onEnd();
        }
        stack.clear();
        finishActivity();
    }

    public static void finishActivity() {
        //GameView gameView = null;
        //gaveView.getActivity().finish();
        GameActivity.activity.finish();
    }

    public static void pauseTop() {
        Log.i(TAG, "Pausing Game");
        Scene scene = top();
        if (scene != null) {
            scene.onPause();
        }
    }

    public static void resumeTop() {
        Log.i(TAG, "Resuming Game");
        Scene scene = top();
        if (scene != null) {
            scene.onResume();
        }
    }

    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    public int count() {
        return gameObjects.size();
    }

    public void update(float elapsedSeconds) {
        int count = gameObjects.size();
        for (int i = count - 1; i >= 0; i--) {
            IGameObject gameObject = gameObjects.get(i);
            gameObject.update(elapsedSeconds);
        }
    }

    protected static Paint bboxPaint;
    public void draw(Canvas canvas) {
        for (IGameObject gameObject : gameObjects) {
            gameObject.draw(canvas);
        }
        if (BuildConfig.DEBUG) {
            if (bboxPaint == null) {
                bboxPaint = new Paint();
                bboxPaint.setStyle(Paint.Style.STROKE);
                bboxPaint.setColor(Color.RED);
            }
            for (IGameObject gobj : gameObjects) {
                if (gobj instanceof IBoxCollidable) {
                    RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
                    canvas.drawRect(rect, bboxPaint);
                }
            }
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

    //////////////////////////////////////////////////
    // Game Object Management
    public void add(IGameObject gameObject) {
        gameObjects.add(gameObject);
        //Log.d(TAG, gameObjects.size() + " objects in [+]" + getClass().getSimpleName());
    }

    public void remove(IGameObject gameObject) {
        gameObjects.remove(gameObject);
        //Log.d(TAG, gameObjects.size() + " objects in [-]" + getClass().getSimpleName());
        if (gameObject instanceof IRecyclable) {
            RecycleBin.collect((IRecyclable) gameObject);
        }
    }
}
