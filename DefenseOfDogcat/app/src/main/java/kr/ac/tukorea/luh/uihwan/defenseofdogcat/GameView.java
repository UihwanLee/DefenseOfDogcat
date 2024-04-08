package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.content.Context;
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
import android.view.View;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View implements Choreographer.FrameCallback {

    private static final String TAG = GameView.class.getSimpleName();

    private Bitmap bgBitmap;
    private Bitmap controlBitmap;

    private static final float SCREEN_WIDTH = 16.0f;
    private static final float SCREEN_HEIGHT = 9.0f;
    private final Matrix transformMatrix = new Matrix();
    RectF bgRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    RectF controlRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    private final Dogcat dogcat = new Dogcat();

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

        // Stage 기본 세팅
        curStageIDX = 0;

        Resources res = getResources();
        bgBitmap = BitmapFactory.decodeResource(res, STAGE_IDS[curStageIDX]);
        controlBitmap = BitmapFactory.decodeResource(res, R.mipmap.scene03_ui_background);

        // 플레이어 초기화
        Bitmap playerSheet = BitmapFactory.decodeResource(res, R.mipmap.player_animation_sheet);
        Dogcat.setBitmap(playerSheet);

        scheduleUpdate();
    }

    // Handler is android.os.Handler.
    // do not import java.util.logging.Handler
    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }


    @Override
    public void doFrame(long nanos) {
        update();
        invalidate();
        if (isShown()) {
            scheduleUpdate();
        }
    };

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.concat(transformMatrix);
        drawStage(canvas);
        drawUI(canvas);
        drawPlayer(canvas);
        canvas.restore();
    }

    private void drawStage(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
    }

    private void drawPlayer(Canvas canvas) {
        dogcat.draw(canvas);
    }

    private void drawUI(Canvas canvas) {
        // 슬롯 UI 배치
        canvas.drawBitmap(controlBitmap, null, controlRect, null);

        // 아군 슬롯 버튼 UI

    }

    private void update() {
        // update
        dogcat.update();
    }
}