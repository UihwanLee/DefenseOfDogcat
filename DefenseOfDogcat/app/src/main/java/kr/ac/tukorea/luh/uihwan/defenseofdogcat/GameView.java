package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View implements Choreographer.FrameCallback {

    private static final String TAG = GameView.class.getSimpleName();

    private Bitmap bgBitmap;
    private Bitmap controlBitmap;

    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private Dogcat player;

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

        // StateBar 제거
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setFullScreen(); // default behavior?

        // Stage 기본 세팅
        curStageIDX = 0;

        Resources res = getResources();
        bgBitmap = BitmapFactory.decodeResource(res, STAGE_IDS[curStageIDX]);
        controlBitmap = BitmapFactory.decodeResource(res, R.mipmap.scene03_ui_background);

        // 플레이어 초기화
        Bitmap playerSheet = BitmapFactory.decodeResource(res, R.mipmap.player_animation_sheet);
        this.player = new Dogcat(playerSheet);
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

    private static final float SCREEN_WIDTH = 16.0f;
    private static final float SCREEN_HEIGHT = 9.0f;
    private final Matrix transformMatrix = new Matrix();
    private final Matrix invertedMatrix = new Matrix();
    private final float[] pointsBuffer = new float[2];
    RectF bgRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    RectF controlRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h;
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT;

        if (view_ratio > game_ratio) {
            float scale = h / SCREEN_HEIGHT;
            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / SCREEN_WIDTH;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
        transformMatrix.invert(invertedMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.concat(transformMatrix);
        drawStage(canvas);
        drawUI(canvas);
        drawObject(canvas);
        canvas.restore();
    }

    private void drawStage(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
    }

    private void drawObject(Canvas canvas) {
        for (IGameObject gameObject : gameObjects) {
            gameObject.draw(canvas);
        }
    }

    private void drawUI(Canvas canvas) {
        // 슬롯 UI 배치
        canvas.drawBitmap(controlBitmap, null, controlRect, null);

        // 아군 슬롯 버튼 UI

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                pointsBuffer[0] = event.getX();
                pointsBuffer[1] = event.getY();
                invertedMatrix.mapPoints(pointsBuffer);
                player.setTargetPosition(pointsBuffer[0], pointsBuffer[1]);
                return true;
        }
        return  super.onTouchEvent(event);
    }

    private void update() {
        // update
        for (IGameObject gameObject : gameObjects) {
            gameObject.update(elapsedSeconds);
        }
    }
}