package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.luh.uihwan.defenseofdogcat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onBtnStartGame(View view) {
        Log.d(TAG, "Open GameView");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}