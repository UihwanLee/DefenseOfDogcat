package kr.ac.tukorea.luh.uihwan.defenseofdogcat.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.luh.uihwan.framework.interfaces.IGameObject;
import kr.ac.tukorea.luh.uihwan.framework.objects.JoyStick;
import kr.ac.tukorea.luh.uihwan.framework.objects.Sprite;
import kr.ac.tukorea.luh.uihwan.framework.res.BitmapPool;

public class Dogcat extends Sprite implements IBoxCollidable {

    private final Bitmap invertSheet;
    int TOTAL_NUMBER_OF_FRAMES = 2;
    Rect[] playerIDLEFrames;
    Rect[] playerIDLEOriginFrame = new Rect[TOTAL_NUMBER_OF_FRAMES];
    Rect[] playerIDLEInvertFrame = new Rect[TOTAL_NUMBER_OF_FRAMES];

    private static final float RADIUS_X = 2.5f;
    private static final float RADIUS_Y = 1.5f;
    private static final float SPEED = 8.0f;
    protected RectF collisionRect = new RectF();
    private RectF dstRect = new RectF();
    private float x, y, angle;
    private float tx, ty, dx, dy;

    private Bitmap playerSheet;
    private Bitmap originSheet;

    private final JoyStick joyStick;

    //좌우 반전 이미지 효과 및 Bitmap 만들기
    private Matrix sideInversion = new Matrix();

    public Dogcat(JoyStick joyStick) {
        super(R.mipmap.player_animation_sheet);
        x = 8.0f;
        y = 2.0f;
        dstRect.set(x-RADIUS_X, y, x+RADIUS_X, y+2*RADIUS_Y);
        //dstRect.set(0.0f, 0.0f, 16.0f, 9.0f);

        playerIDLEOriginFrame[0] = new Rect(0, 200, 100, 300);
        playerIDLEOriginFrame[1] = new Rect(200, 400, 400, 600);

        playerIDLEInvertFrame[0] = new Rect(500, 200, 600, 300);
        playerIDLEInvertFrame[1] = new Rect(200, 400, 400, 600);

        // 이미지 생성
        originSheet = BitmapPool.get(R.mipmap.player_animation_sheet);

        // 좌우 반전 이미지 생성
        sideInversion.setScale(-1, 1);
        invertSheet = Bitmap.createBitmap(originSheet, 0, 0, originSheet.getWidth(), originSheet.getHeight(), sideInversion, false);

        this.playerSheet = originSheet;
        playerIDLEFrames = playerIDLEOriginFrame;

        this.joyStick = joyStick;
    }

    @Override
    public void update(float elapsedSeconds) {
        if (joyStick.power > 0) {
            float distance = SPEED * joyStick.power * elapsedSeconds;
            x += (float) (distance * Math.cos(joyStick.angle_radian));
            dstRect.set(x-RADIUS_X, y, x+RADIUS_X, y+2*RADIUS_Y);
            angle = (float) Math.toDegrees(joyStick.angle_radian) + 90;

            // 목표 위치에 따른 이미지 반전
            this.playerSheet = (Math.cos(joyStick.angle_radian)>=0) ? originSheet : invertSheet;
            this.playerIDLEFrames = (Math.cos(joyStick.angle_radian)>=0) ? playerIDLEOriginFrame : playerIDLEInvertFrame;

            // 별도의 collisionRect
            collisionRect.set(dstRect);
            collisionRect.inset(0.11f, 0.11f);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(playerSheet, playerIDLEFrames[0], dstRect, null);
        canvas.restore();
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
