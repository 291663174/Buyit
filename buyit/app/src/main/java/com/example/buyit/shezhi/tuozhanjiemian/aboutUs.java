package com.example.buyit.shezhi.tuozhanjiemian;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.buyit.R;

public class aboutUs extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ImageView back_image_button = findViewById(R.id.back_image_button);
        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView about_us_txt = findViewById(R.id.about_us_txt);
        about_us_txt.setText("        本App是由福建农林大学金山学院2017级计算机 \n科学与技术专业独立自主设计开发，" +
                "使用到了Java的SSM框架搭建了后台服务,使得本App访问mysql数据库更为便捷" +"注册账号登录后即可正常使用本App进行数据交互。");

        exit.getInstance().addActivity(this);
    }
}
