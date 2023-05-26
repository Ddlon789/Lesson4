package ru.mirea.malyushin.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import ru.mirea.malyushin.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: " + msg.getData().getString("result"));
            }
        };
        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();
        binding.editText.setText("Мой номер по списку № 17");
        binding.editYears.getText();
        binding.editProffesy.getText();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}
                }, Integer.parseInt(String.valueOf(binding.editYears.getText())));

                Log.d(MainActivity.class.getSimpleName(), "Возраст - " + binding.editYears.getText());
                Log.d(MainActivity.class.getSimpleName(), "Профессия - " + binding.editProffesy.getText());
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("KEY", "mirea");
                bundle.putString("YEAR", String.valueOf(binding.editYears.getText()));
                bundle.putString("PROFF", String.valueOf(binding.editProffesy.getText()));
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);
            }
        });
    }
}