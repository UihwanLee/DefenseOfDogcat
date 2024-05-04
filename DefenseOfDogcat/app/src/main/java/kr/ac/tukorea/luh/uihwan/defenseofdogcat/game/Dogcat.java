package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.objects.AnimSprite;
import kr.ac.tukorea.luh.uihwan.framework.objects.JoyStick;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.res.BitmapPool;

public class Dogcat extends AnimSprite implements IBoxCollidable {
    protected Rect srcRect = new Rect();

    private static final float RADIUS_X = 2.5f;
    private static final float RADIUS_Y = 1.5f;
    private static final float SPEED = 8.0f;
    protected RectF collisionRect = new RectF();
    private RectF dstRect = new RectF();
    private float x, y, angle;
    private float tx, ty, dx, dy;


    private Bitmap playerSheet;
    private Bitmap originSheet;
    private final Bitmap invertSheet;

    private int frameIndex, frameCount, frameWidth, frameHeight, frameState, sheetWidth, sheetHeight;
    private float fps;
    private final long createdOn;

    private final JoyStick joyStick;

    //좌우 반전 이미지 효과 및 Bitmap 만들기
    private Matrix sideInversion = new Matrix();

    private Dogcat(JoyStick joyStick) {
        super(R.mipmap.player_animation_sheet, 10.0f, 3);
        x = 8.0f;
        y = 2.0f;
        dstRect.set(x-RADIUS_X, y, x+RADIUS_X, y+2*RADIUS_Y);
        //dstRect.set(0.0f, 0.0f, 16.0f, 9.0f);

        // 이미지 생성
        originSheet = BitmapPool.get(R.mipmap.player_animation_sheet);

        // 변수 지정
        frameWidth = frameHeight = 100;
        frameState = 0;
        frameCount = 3;

        sheetWidth = bitmap.getWidth();
        sheetHeight = bitmap.getHeight();

        createdOn = System.currentTimeMillis();
        fps = 10.0f;

        // 좌우 반전 이미지 생성
        sideInversion.setScale(-1, 1);
        invertSheet = Bitmap.createBitmap(originSheet, 0, 0, originSheet.getWidth(), originSheet.getHeight(), sideInversion, false);

        this.playerSheet = originSheet;

        // State 설정
        setState(State.idle);

        this.joyStick = joyStick;
    }
    public static Dogcat get(JoyStick joyStick) {
        return new Dogcat(joyStick);
    }

    @Override
    public void update(float elapsedSeconds) {
        if (joyStick.power > 0) {
            setState(State.walking);
        }
        else {
            // idle animation 표시
            setState(State.idle);
        }

        processState(elapsedSeconds);
    }

    private void processState(float elapsedSeconds)
    {
        switch (state) {
            case idle:
                // idle animation 표시
                frameCount = 3;
                frameState = 0;
                break;
            case walking:
                float distance = SPEED * joyStick.power * elapsedSeconds;
                x += (float) (distance * Math.cos(joyStick.angle_radian));
                dstRect.set(x-RADIUS_X, y, x+RADIUS_X, y+2*RADIUS_Y);
                angle = (float) Math.toDegrees(joyStick.angle_radian) + 90;

                // 목표 위치에 따른 이미지 반전
                this.playerSheet = (Math.cos(joyStick.angle_radian)>=0) ? originSheet : invertSheet;

                // 별도의 collisionRect
                collisionRect.set(dstRect);
                collisionRect.inset(0.5f, 0.3f);

                // walk animation 표시
                frameCount = 6;
                frameState = 1;
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // AnimSprite 는 단순반복하는 이미지이므로 time 을 update 에서 꼼꼼히 누적하지 않아도 된다.
        // draw 에서 생성시각과의 차이로 frameIndex 를 계산한다.
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        if((Math.cos(joyStick.angle_radian)>=0))
        {
            srcRect.set(frameIndex * frameWidth, sheetHeight - (frameHeight * (frameState + 1)), (frameIndex + 1) * frameWidth, sheetHeight - (frameHeight * frameState));
        }
        else
        {
            srcRect.set(sheetWidth - ((frameIndex+1) * frameWidth), sheetHeight - (frameHeight * (frameState + 1)), sheetWidth - (frameIndex * frameWidth), sheetHeight - (frameHeight * frameState));
        }
        canvas.drawBitmap(playerSheet, srcRect, dstRect, null);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
