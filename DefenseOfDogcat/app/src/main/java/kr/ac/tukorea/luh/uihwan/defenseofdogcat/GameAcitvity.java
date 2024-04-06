package kr.ac.tukorea.luh.uihwan.defenseofdogcat;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.tukorea.luh.uihwan.defenseofdogcat.databinding.GameViewBinding;

public class GameAcitvity extends AppCompatActivity {

    private GameViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = GameViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(new GameView(this));
    }
}
