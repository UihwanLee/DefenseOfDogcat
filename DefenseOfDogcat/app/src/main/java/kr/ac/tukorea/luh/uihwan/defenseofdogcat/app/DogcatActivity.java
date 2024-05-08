package kr.ac.tukorea.luh.uihwan.defenseofdogcat.app;

import android.os.Bundle;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.InGameScene;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.game.StartScene;
import kr.ac.tukorea.luh.uihwan.framework.activity.GameActivity;

public class DogcatActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StartScene().push();
    }
}
