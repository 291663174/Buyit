package com.example.buyit.denglu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.buyit.R;
import com.example.buyit.dengluhou.sigezhujiemian.mainActivity;
import com.example.buyit.shezhi.tuozhanjiemian.exit;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private static boolean mBackKeyPressed = false;//记录是否有首次按手机返回键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        Button make_sure_button = findViewById(R.id.make_sure_button);
        make_sure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.length() >= 8 && password.length() >= 8) {
                    loginRequest();
                } else {
                    Toast.makeText(getApplicationContext(), "请检查用户名或密码是否符合输入规范！\n且账号密码需在8位以上！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button signin = findViewById(R.id.sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.length() >= 8 && password.length() >= 8) {
                    signinRequest();
                } else if (username.length() == 0 || password.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不可为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "账号密码过于简单，请分别输入8位数以上的账号密码！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exit.getInstance().addActivity(this);
    }

    //TODO:登录功能的实现
    private void loginRequest() {
        final String user = username.getText().toString();//获取用户名和密码
        final String pass = password.getText().toString();

        //OkHttpClient的线程池和连接池在空闲的时候会自动释放，所以一般情况下不需要手动关闭，
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //FromBody用于提交表单键值对,key-value,其作用类似于HTML中的<form>标记。比如username="1001",age="123456"等类似的键值对
        FormBody formBody = new FormBody.Builder().add("userAccount", user).add("userPwd", pass).build();
        //向后台发送请求
        // TODO: 需修改为本电脑 ipV4 地址，可在cmd命令行内输入ipconfig进行查询
        //http://192.168.0.102为电脑开cmd ipconfig显示的IP V4 地址，且手机需和电脑处于一个WIFI，后面的路径是指向后台服务接口
        String loginUrl = "http://192.168.0.102:8080/user/login";
        Request request = new Request.Builder().url(loginUrl).post(formBody).build();
        //Call对象表示一个已经准备好可以执行的请求，用这个对象可以查询请求的执行状态，或者取消当前请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("login", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                com.alibaba.fastjson.JSONObject json = JSON.parseObject(response.body().string());
                String res = json.getString("res");
                Log.d("result", "onResponse: " + json);
                if ("success".equals(res)) {
                    //登录成功，则进行页面跳转
                    Intent intent = new Intent(login.this, mainActivity.class);
                    intent.putExtra("userAccount", user);
                    intent.putExtra("userPwd", pass);
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);
                    startActivity(intent);
                    finish();
                } else {//登录失败
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        });
    }

    //TODO:注册功能的实现
    private void signinRequest() {
        String user = username.getText().toString();//获取用户名和密码
        String pass = password.getText().toString();

        //OkHttpClient的线程池和连接池在空闲的时候会自动释放，所以一般情况下不需要手动关闭，
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //FromBody用于提交表单键值对,key-value,其作用类似于HTML中的<form>标记。比如username="1001",age="123456"等类似的键值对
        FormBody formBody = new FormBody.Builder().add("userAccount", user).add("userPwd", pass)
                .add("userGender", "男").add("userName", user).add("userEmail", "")
                .add("userPhoneNumber", "").add("userCardNumber", "").build();
        //向后台发送请求
        String signinUrl = "http://192.168.0.102:8080/user/register";
        Request request = new Request.Builder().url(signinUrl).post(formBody).build();
        //Call对象表示一个已经准备好可以执行的请求，用这个对象可以查询请求的执行状态，或者取消当前请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("login", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject json = JSON.parseObject(response.body().string());
                String res = json.getString("res");
                Log.d("result", "onResponse: " + json);
                if ("success".equals(res)) {
                    //注册成功
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                } else {//注册失败
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "用户名或者密码错误，登录失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "已存在该账号,注册失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {        //重写手机返回键的方法
        if (!mBackKeyPressed) {
            Toast.makeText(this, "再按一次返回键则退出APP", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            },2000);
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }
}