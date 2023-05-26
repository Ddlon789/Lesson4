package ru.mirea.malyushin.data_thread;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import ru.mirea.malyushin.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textV.setText("Метод runOnUiThread() позволяет выполнить код в главном (UI) потоке." +
                " Это полезно, когда нужно выполнить операции из другого потока, например, из фонового потока." +
                "Метод postDelayed() позволяет запланировать выполнение кода через определенный промежуток " +
                "времени в главном (UI) потоке." +
                "Метод post() выполняет код в главном (UI) потоке без задержки." +
                "Соответсвенно, поток ждет 2 секунды, далее в UI попадает непосредственно runn1, затем вызывается runn3" +
                ", но так как у него задержка 2 секунды, то в очередь встает runn2, у которого ее нет и затем уже " +
                "runn3");

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn1");
                Log.i(TAG, "runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn2");
                Log.i(TAG, "runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn3");
                Log.i(TAG, "runn3");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    binding.tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}