package ru.mirea.malyushin.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.mirea.malyushin.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Thread mainThread = Thread.currentThread();
        binding.textView.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-06-21, НОМЕР ПО СПИСКУ: 19, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Персона");
        binding.textView.append("\n Новое имя потока: " + mainThread.getName());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        int numberThread = counter++;
                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы № %s номер по списку № %d ", numberThread, "БСБО-06-21", 17));
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                            while (System.currentTimeMillis() < endTime) {
                                synchronized (this) {
                                    try {
                                        wait(endTime - System.currentTimeMillis());
                                        Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                Log.d("ThreadProject", "Выполнен поток No " + numberThread);
                            }
                        }
                }).start();

                Integer lessons = String.valueOf(binding.editText1.getText()).matches("-?\\d+") ?
                        Integer.parseInt(String.valueOf(binding.editText1.getText())) : null;
                Integer days = String.valueOf(binding.editText2.getText()).matches("-?\\d+") ?
                        Integer.parseInt(String.valueOf(binding.editText2.getText())) : null;
                if (lessons == null || days == null || days == 0) {
                    binding.textView.setText("неверный формат");
                } else {
                    binding.textView.setText(String.valueOf(lessons/days));
                }
            }
        });
    }
}