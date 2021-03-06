package com.example.buyit.yonghu.xinxigengxin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

// TODO:  设计更新 邮箱 界面
public class updateEmail extends AppCompatActivity {
    // TODO: 需修改为本电脑 ipV4 地址，可在cmd命令行内输入ipconfig进行查询
    private String url = "http://192.168.0.102:8080/user/updateEmail";

    private ImageButton back;
    private EditText edit_email;
    private TextView update_tel_text;
    private String updateemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        edit_email = findViewById(R.id.edit_email);
        edit_email.setText(getIntent().getStringExtra("useremail"));

        back = findViewById(R.id.back_image_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0x15);    //响应 用户信息界面 0x05请求的来源，发出0x15响应码
                finish();
            }
        });

        update_tel_text = findViewById(R.id.update_text);
        update_tel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_email.getText().toString().equals("")){
                    updateemail = edit_email.getText().toString();
                    InitData();
                }else {
                    Toast.makeText(getApplicationContext(),"请输入邮箱",Toast.LENGTH_SHORT).show();
                }
            }
        });

        exit.getInstance().addActivity(this);
    }

    private void InitData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //FromBody用于提交表单键值对,key-value,其作用类似于HTML中的<form>标记。比如username="1001",age="123456"等类似的键值对
        FormBody formBody = new FormBody.Builder()
                .add("userAccount",getIntent().getStringExtra("userAccount"))
                .add("updateEmail",updateemail)
                .build();
        //向后台发送请求
        Request request = new Request.Builder().url(url).post(formBody).build();
        //Call对象表示一个已经准备好可以执行的请求，用这个对象可以查询请求的执行状态，或者取消当前请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("update_email", "onFailure: " + e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject json = JSON.parseObject(response.body().string());
                String res = json.getString("result");
                if ("success".equals(res)) {
                    Message msg = Message.obtain();
                    msg.what=0x1111;
                    handler.sendMessage(msg);
                } else {
                    //子线程不能弹出toast
                    Message msg = Message.obtain();
                    msg.what=0x1112;
                    handler.sendMessage(msg);
                }
            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x1111:
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                    break;
                case 0x1112:
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {        //重写手机返回键的方法
        setResult(0x15);  //响应 用户信息 界面 0x05请求的来源，发出0x15响应码
        finish();
    }
}
