package com.example.buyit.yonghu.xinxigengxin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.buyit.R;
import com.example.buyit.shezhi.tuozhanjiemian.exit;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// TODO: 更新用户银行卡信息
public class updateCard extends AppCompatActivity {

    private EditText card;
    private String cardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_card);
        TextView tv_save = findViewById(R.id.new_card);
        card = findViewById(R.id.edit_card_number);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!card.getText().toString().equals("")) {
                    cardNumber = card.getText().toString();
                    if (card.getText().toString().length() == 19) {
                        InitData();
                    }else {
                        Toast.makeText(getApplicationContext(), "绑定银行卡失败，银行卡号不是19位！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "绑定银行卡失败，银行卡号不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView back_image_button = findViewById(R.id.back_image_button);
        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0x30);
                finish();
            }
        });
        EditText edit_username = findViewById(R.id.edit_username);
        edit_username.setText(getIntent().getStringExtra("nicheng"));

        exit.getInstance().addActivity(this);
    }

    private void InitData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //FromBody用于提交表单键值对,key-value,其作用类似于HTML中的<form>标记。比如username="1001",age="123456"等类似的键值对
        FormBody formBody = new FormBody.Builder().add("userName", getIntent().getStringExtra("nicheng"))
                .add("card", cardNumber).build();
        //向后台发送请求
        // TODO: 需修改为本电脑 ipV4 地址，可在cmd命令行内输入ipconfig进行查询
        String url = "http://192.168.0.102:8080/user/updatecard";
        Request request = new Request.Builder().url(url).post(formBody).build();
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
                String res = json.getString("result");
                if ("success".equals(res)) {
                    Message msg = Message.obtain();
                    msg.what = 0x1111;
                    handler.sendMessage(msg);
                } else {
                    //子线程不能弹出toast
                    Message msg = Message.obtain();
                    msg.what = 0x1112;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x1111:
                    Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                    break;
                case 0x1112:
                    Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {        //重写手机返回键的方法
        // 重新获取数据库内的 昵称，响应 我的界面 的请求，顺利返回 我的界面
        setResult(0x30);  //响应 绑定银行卡界面 0x20请求的来源，发出0x30响应码
        finish();
    }
}
