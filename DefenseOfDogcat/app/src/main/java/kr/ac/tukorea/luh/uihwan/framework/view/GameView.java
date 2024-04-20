package kr.ac.tukorea.luh.uihwan.framework.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.BuildConfig;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.Dogcat;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.scene.Scene;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View implements Choreographer.FrameCallback {

    private static final String TAG = GameView.class.getSimpleName();

    //////////////////////////////////////////////////
    // Debug Helper
    private Paint borderPaint;
    private Paint fpsPaint;
    private void initDebugObjects() {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.RED);

        fpsPaint = new Paint();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100f);
    }

    //////////////////////////////////////////////////
    // Global Variable (static member) for Resources
    public static Resources res;
    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private Dogcat player;


    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

        // Resource 초기화
        res = getResources();

        // StateBar 제거
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setFullScreen(); // default behavior?

        if (BuildConfig.DEBUG) {
            initDebugObjects();
        }

        scheduleUpdate();
    }

    // Handler is android.os.Handler.
    // do not import java.util.logging.Handler
    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    private boolean running = true;
    private long previousNanos = 0;
    private float elapsedSeconds;
    @Override
    public void doFrame(long nanos) {
        long elapsedNanos = nanos - previousNanos;
        elapsedSeconds = elapsedNanos / 1_000_000_000f;
        if (previousNanos != 0) {
            update();
        }
        invalidate();
        if (running) {
            scheduleUpdate();
        }
        previousNanos = nanos;
    };

    public void setFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        setSystemUiVisibility(flags);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.onSize(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Scene scene = Scene.top();
        if (scene == null) {
            return;
        }
        canvas.save();
        Metrics.concat(canvas);
        if (BuildConfig.DEBUG) {
            canvas.drawRect(Metrics.borderRect, borderPaint);
        }
        scene.draw(canvas);
        canvas.restore();

        if(BuildConfig.DEBUG) {
            int fps = (int) (1.0f / elapsedSeconds);
            int count = scene.count();
            canvas.drawText("FPS: " + fps + " objs: " + count, 100f, 200f, fpsPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Scene scene = Scene.top();
        if (scene != null) {
            boolean handled = scene.onTouch(event);
            if (handled) return true;
        }
        return  super.onTouchEvent(event);
    }

    private void update() {
        // update
        Scene scene = Scene.top();
        if (scene != null) {
            scene.update(elapsedSeconds);
        }
    }

    public void onBackPressed() {
        Scene scene = Scene.top();
        if (scene == null) {
            Scene.finishActivity();
            return;
        }
        boolean handled = scene.onBackPressed();
        if (handled) return;

        Scene.pop();
    }

    public void pauseGame() {
        running = false;
        Scene.pauseTop();
    }

    public void resumeGame() {
        if (running) return;
        running = true;
        previousNanos = 0;
        scheduleUpdate();
        Scene.resumeTop();
    }

    public void destroyGame() {
        Scene.popAll();
    }
}