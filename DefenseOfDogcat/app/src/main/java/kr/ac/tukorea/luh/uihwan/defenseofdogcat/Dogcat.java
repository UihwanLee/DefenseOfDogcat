package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
public class Dogcat {

    int TOTAL_NUMBER_OF_FRAMES = 2;
    Rect[] playerIDLEFrames = new Rect[TOTAL_NUMBER_OF_FRAMES];

    private float playerPosX;
    private float playerPosY;
    private float playerStartX;
    private float playerStartY;
    private float playerEndX;
    private float playerEndY;

    private static Bitmap playerSheet;

    public Dogcat() {
        playerPosX = 3.0f;
        playerPosY = 3.5f;

        float playerOffsetX = 2.5f;
        float playerOffsetY = 1.5f;

        playerStartX = playerPosX - playerOffsetX;
        playerStartY = playerPosY - playerOffsetY;
        playerEndX = playerPosX + playerOffsetX;
        playerEndY = playerPosY + playerOffsetY;

        playerIDLEFrames[0] = new Rect(0, 280, 100, 600);
        playerIDLEFrames[1] = new Rect(200, 400, 400, 600);
    }

    public static void setBitmap(Bitmap bitmap) { // Alt+Insert -> Setter
        Dogcat.playerSheet = bitmap;
    }

    public void update(float elapsedSeconds) {

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerSheet, playerIDLEFrames[0], new RectF(playerStartX, playerStartY, playerEndX, playerEndY), null);
    }
}
