package kr.ac.tukorea.luh.uihwan.defenseofdogcat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.BuildConfig;
import kr.ac.tukorea.luh.uihwan.framework.activity.GameActivity;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.databinding.ActivityMainBinding;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (BuildConfig.DEBUG) {
            startActivity(new Intent(this, DogcatActivity.class));
        }
    }

    public void onBtnStartGame(View view) {
        Log.d(TAG, "Open GameView");
        Intent intent = new Intent(this, DogcatActivity.class);
        startActivity(intent);
    }
}