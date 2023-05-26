package ru.mirea.malyushin.player;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import ru.mirea.malyushin.player.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView imageView = binding.imageView;
        ImageButton prevS = binding.prevS;
        ImageButton nextS = binding.nextS;
        ImageButton nextPl = binding.nextPl;
        ImageButton prevPl = binding.prevPl;
        ImageButton pause = binding.pause;
    }
}