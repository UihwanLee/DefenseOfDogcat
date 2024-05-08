package kr.ac.tukorea.luh.uihwan.framework.activity;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.activity.OnBackPressedCallback;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.luh.uihwan.framework.view.GameView;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.StartScene;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.InGameScene;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.databinding.GameViewBinding;

public class GameActivity extends AppCompatActivity {

    private GameViewBinding binding;
    public static GameActivity activity;
    private GameView gameView;

    private static int[] UI_ALLY_IDS = new int[] {
            R.mipmap.ui_ally_01_rat,
            R.mipmap.ui_ally_02_rabbit,
            R.mipmap.ui_ally_03_bear,
            R.mipmap.ui_ally_04_turtle,
            R.mipmap.ui_ally_05_rhinoceros,
            R.mipmap.ui_ally_06_penguin,
            R.mipmap.ui_ally_07_dragon,
    };

    private static int[] ALLY_SLOT_VIEW_IDS = new int[] {
            R.id.unit_ally_slot_01, R.id.unit_ally_slot_02, R.id.unit_ally_slot_03,
            R.id.unit_ally_slot_04, R.id.unit_ally_slot_05, R.id.unit_ally_slot_06,
            R.id.unit_ally_slot_07,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        gameView = new GameView(this);
        //gameView.setFullScreen();
        setContentView(gameView);
        //new InGameScene(STAGE_IDS[0]).push();

        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            gameView.onBackPressed();
        }
    };

    private void init_unit_slot() {
        for(int i=0; i < ALLY_SLOT_VIEW_IDS.length; i++) {
            ImageView iv = findViewById(ALLY_SLOT_VIEW_IDS[i]);
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(UI_ALLY_IDS[i]);
            iv.setTag(UI_ALLY_IDS[i]);
        }
    }

    public void onBtnAllySlot(View view) {
        Log.d("Tag", "Slot clicked. ID=" + view.getId());
    }

    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        gameView.destroyGame();
        super.onDestroy();
    }
}