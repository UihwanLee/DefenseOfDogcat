package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View implements Choreographer.FrameCallback {

    private static final String TAG = GameView.class.getSimpleName();


    //////////////////////////////////////////////////
    // Global Variable (static member) for Resources
    public static Resources res;
    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private Dogcat player;

    private final Paint fpsPaint = new Paint();

    private int curStageIDX;
    private static int[] STAGE_IDS = new int[] {
            R.mipmap.scene03_background_type_1,
            R.mipmap.scene03_background_type_2,
            R.mipmap.scene03_background_type_3,
    };

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

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100f);

        // Stage 기본 세팅
        curStageIDX = 0;

        // 플레이어 초기화
        this.player = new Dogcat();
        gameObjects.add(player);

        scheduleUpdate();
    }

    // Handler is android.os.Handler.
    // do not import java.util.logging.Handler
    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

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
        if (isShown()) {
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

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.onSize(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Metrics.concat(canvas);
        drawObject(canvas);
        canvas.restore();
        drawFPS(canvas);
    }

    private void drawObject(Canvas canvas) {
        Scene scene = Scene.top();
        scene.draw(canvas);
    }

    private void drawFPS(Canvas canvas)
    {
        int fps = (int) (1.0f / elapsedSeconds);
        canvas.drawText("FPS: " + fps, 100f, 200f, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = Scene.top().onTouch(event);
        if (handled) return true;
        return  super.onTouchEvent(event);
    }

    private void update() {
        // update
        Scene scene = Scene.top();
        scene.update(elapsedSeconds);
    }
}