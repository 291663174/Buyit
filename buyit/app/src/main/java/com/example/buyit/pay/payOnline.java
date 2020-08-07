package com.example.buyit.pay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.example.buyit.R;

public class payOnline extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_online);

        handler = new Handler();

        ImageView back_image_button = findViewById(R.id.back_image_button);
        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                    finish();
                }
            }
        });
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Payed.class));
                finish();
            }
        },2000);
    }

    @Override
    public void onBackPressed() {        //重写手机返回键的方法
        if (runnable != null) {
            handler.removeCallbacks(runnable);
            finish();
        }
    }

}
