package kr.ac.tukorea.luh.uihwan.defenseofdogcat.app;

import android.os.Bundle;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.InGameScene;
import kr.ac.tukorea.luh.uihwan.framework.activity.GameActivity;

public class DogcatActivity extends GameActivity {

    private static int[] STAGE_IDS = new int[] {
            R.mipmap.scene03_background_type_1,
            R.mipmap.scene03_background_type_2,
            R.mipmap.scene03_background_type_3,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Scene.drawsDebugInfo = BuildConfig.DEBUG;
        super.onCreate(savedInstanceState);
        //new InGameScene(STAGE_IDS[0]).push();
    }
}
