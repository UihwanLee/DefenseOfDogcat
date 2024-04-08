package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View {

    private static final String TAG = GameView.class.getSimpleName();

    private Bitmap bgBitmap;
    private Bitmap controlBitmap;

    private Bitmap playerSheet;

    private static final float SCREEN_WIDTH = 16.0f;
    private static final float SCREEN_HEIGHT = 9.0f;
    private final PointF transformOffset = new PointF();
    private float transformScale;
    RectF bgRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    RectF controlRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    int TOTAL_NUMBER_OF_FRAMES = 2;
    Rect[] playerFrames = new Rect[TOTAL_NUMBER_OF_FRAMES];

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

        // 플레이어 애니메이션 세팅
        playerSheet = BitmapFactory.decodeResource(res, R.mipmap.player_animation_sheet);
        playerFrames[0] = new Rect(0, 0, 200, 200);
        playerFrames[1] = new Rect(200, 0, 400, 200);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h;
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT;

        if (view_ratio > game_ratio) {
            transformOffset.set((w - h * game_ratio) / 2, 0);
            transformScale = h / SCREEN_HEIGHT;
        } else {
            transformOffset.set(0, (h - w / game_ratio) / 2);
            transformScale = w / SCREEN_WIDTH;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(transformOffset.x, transformOffset.y);
        canvas.scale(transformScale, transformScale);
        drawStage(canvas);
        drawUI(canvas);
        drawPlayer(canvas);
        canvas.restore();
    }

    private void drawStage(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
    }

    private void drawPlayer(Canvas canvas) {
        float left = 5.0f;
        float top = 2.0f;
        float right = 4.0f;
        float bottom = 6.0f;
        canvas.drawBitmap(playerSheet, playerFrames[0], new RectF(left, top, right, bottom), null);
    }

    private void drawUI(Canvas canvas) {
        // 슬롯 UI 배치
        canvas.drawBitmap(controlBitmap, null, controlRect, null);

        // 아군 슬롯 버튼 UI

    }
}