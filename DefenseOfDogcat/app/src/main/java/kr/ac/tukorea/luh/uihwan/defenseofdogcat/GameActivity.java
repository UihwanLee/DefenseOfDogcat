package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.databinding.GameViewBinding;

public class GameActivity extends AppCompatActivity {

    private GameViewBinding binding;
    public static GameActivity activity;

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

    private static int[] STAGE_IDS = new int[] {
            R.mipmap.scene03_background_type_1,
            R.mipmap.scene03_background_type_2,
            R.mipmap.scene03_background_type_3,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = GameViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(new GameView(this));

        activity = this;

        new InGameScene(STAGE_IDS[0]).push();

        // ally unit slot image 적용
        init_unit_slot();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

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
}