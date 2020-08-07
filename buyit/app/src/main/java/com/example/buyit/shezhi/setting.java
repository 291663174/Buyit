package com.example.buyit.shezhi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.buyit.R;
import com.example.buyit.denglu.login;
import com.example.buyit.shezhi.tuozhanjiemian.aboutUs;
import com.example.buyit.shezhi.tuozhanjiemian.exit;
import com.example.buyit.shezhi.tuozhanjiemian.userAgreement;

public class setting extends AppCompatActivity {

    private static boolean mBackKeyPressed = false;//记录是否有首次按手机返回键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        //返回fragment_my界面，属于activity向fragment跳转
        ImageButton back = findViewById(R.id.back_image_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0x11);    //响应0x01请求的来源0x11
                finish();
            }
        });

        Button about_us = findViewById(R.id.about_us);
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), aboutUs.class);
                startActivity(intent);
            }
        });

        Button user_story = findViewById(R.id.user_story);
        user_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), userAgreement.class);
                startActivity(intent);
            }
        });

        Button check_update = findViewById(R.id.check_update);
        check_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"当前版本已是最新版本！",Toast.LENGTH_SHORT).show();
            }
        });

        Button exit_this = findViewById(R.id.exit_this);
        exit_this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

        Button exit_app = findViewById(R.id.exit_app);
        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new exit(setting.this);
            }
        });

    }

    @Override
    public void onBackPressed() {        //重写手机返回键的方法
        //重新获取数据库内的 昵称，响应 我的界面 的请求，顺利返回 我的界面
        setResult(0x11);    //响应 我的界面 0x01请求的来源，发出0x11响应码
        finish();
    }

}