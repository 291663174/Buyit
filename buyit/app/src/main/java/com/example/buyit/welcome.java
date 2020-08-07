package com.example.buyit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buyit.denglu.login;
import java.util.Timer;
import java.util.TimerTask;

public class welcome extends AppCompatActivity {
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    private int tiaoguo_time = 4; 		//跳过倒计时提示6秒

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    tiaoguo_time--;
                    TextView tiaoguo = findViewById(R.id.tiaoguo);
                    tiaoguo.setText(tiaoguo_time + "s 跳 过 ");
                    if (tiaoguo_time < 1) {
                        timer.cancel();
                        tiaoguo.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        timer.schedule(task, 0, 1000);	//等待时间一秒，停顿时间一秒
        //5s后自动跳过欢迎界面
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(welcome.this, login.class);
                startActivity(intent);
            }
        }, 3000);
    }

    public void tiaoguo(View view) {
        if (view.getId() == R.id.tiaoguo) {//从欢迎界面跳转到首界面
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
        }
    }
}
