package kr.ac.tukorea.luh.uihwan.framework.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.luh.uihwan.framework.res.BitmapPool;

public class AnimSprite extends Sprite {
    protected Rect srcRect = new Rect();
    public enum State {
        idle, walking, attacking, dying, COUNT
    }
    protected State state = State.idle;
    private float fps;
    private int frameWidth, frameHeight;
    private int frameCount, frameState;
    private final long createdOn;

    private int sheetWidth, sheetHeight;

    public AnimSprite(int mipmapId, float fps, int frameCount) {
        super(mipmapId);
        setAnimationResource(0, fps, frameCount);
        createdOn = System.currentTimeMillis();
    }
    public void setAnimationResource(int mipmapId, float fps) {
        setAnimationResource(mipmapId, fps, 0);
    }
    public void setAnimationResource(int mipmapId, float fps, int frameCount) {
        if (mipmapId != 0) {
            bitmap = BitmapPool.get(mipmapId);
        }
        this.fps = fps;
        this.frameCount = frameCount;
        frameWidth = frameHeight = 100;
        frameState = 0;

        sheetWidth = bitmap.getWidth();
        sheetHeight = bitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        // AnimSprite 는 단순반복하는 이미지이므로 time 을 update 에서 꼼꼼히 누적하지 않아도 된다.
        // draw 에서 생성시각과의 차이로 frameIndex 를 계산한다.
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, sheetHeight - (frameHeight * (frameState + 1)), (frameIndex + 1) * frameWidth, sheetHeight - (frameHeight * frameState));
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void setState(State state) {
        this.state = state;
    }
}
