package com.example.buyit.wode.jiemian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.buyit.Myadapter;
import com.example.buyit.R;
import com.example.buyit.UserLogin;
import com.example.buyit.shezhi.tuozhanjiemian.exit;
import com.example.buyit.yonghu.xinxigengxin.updateCard;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// TODO: 设计 我的卡包 （银行卡） 界面
public class Card extends AppCompatActivity {
    private Message message;

    private RelativeLayout card_info_more;
    private TextView card_txt;
    private CardView cardview;
    private TextView unbind_txt;
    private ListView listview_below_card;
    private ArrayList<Map<String, Object>> list;
    String[] items = new String[]{"去转账", "扣款顺序", "查看账单", "卡管理"};
    int[] images = new int[]{R.mipmap.card, R.mipmap.kefu, R.mipmap.foot, R.mipmap.jilu};
    private Myadapter adapter;
    private String cardnumber;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        //返回
        ImageView back = findViewById(R.id.back_image_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0x16);
                finish();
            }
        });

        //点击添加 跳转添加银行卡页面
        Button insert_card = findViewById(R.id.insert_card);
        insert_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), updateCard.class);
                intent.putExtra("nicheng", getIntent().getStringExtra("nicheng"));
                startActivityForResult(intent, 0x20);    //标识请求的来源0x20,跳转 绑定新卡 页面
            }
        });

        //没有绑定银行卡号，本身就不显示银行卡,否则显示银行卡界面
        if (!"".equals(getIntent().getStringExtra("usercardnumber")) && getIntent().getStringExtra("usercardnumber") != null) {
            card_info_more = findViewById(R.id.card_info_more);
            card_info_more.setVisibility(View.VISIBLE);

            unbind_txt = findViewById(R.id.unbind_txt);
            unbind_txt.setVisibility(View.GONE);

            card_txt = findViewById(R.id.card_txt);
            card_txt.setText(getIntent().getStringExtra("usercardnumber").substring(0, 4) + " **** **** **** " + getIntent().getStringExtra("usercardnumber").substring(16, 19));

            cardview = findViewById(R.id.cardview);
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "您的19位数建行卡", Toast.LENGTH_SHORT).show();
                }
            });

               /*
                 0.抽取 我的界面的底部列表视图，并实现监听
                 1.实现list中具体的数据
                 2.设计布局文件
                 3.设置adapter适配器
                */
            listview_below_card = findViewById(R.id.listview_below_card);
            list = new ArrayList<>();
            for (int i = 0; i < items.length; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("001图片", images[i]);
                map.put("002文字", items[i]);
                list.add(map);
            }
            adapter = new Myadapter(getApplicationContext(), list, R.layout.list_bottom_bg, new String[]{"001图片", "002文字"}
                    , new int[]{R.id.select_imageview, R.id.select_title});
            listview_below_card.setAdapter(adapter);

        }


        exit.getInstance().addActivity(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x20 && resultCode == 0x30) {   //对 标识请求的来源0x20 和 响应0x20请求的来源0x30 进行判断，是否符合跳转要求
            InitData();
        }
    }

    //根据用户账号去获取数据库中的用户信息
    private void InitData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //FromBody用于提交表单键值对,key-value,其作用类似于HTML中的<form>标记。比如username="1001",age="123456"等类似的键值对
        FormBody formBody = new FormBody.Builder().add("userAccount",
                getIntent().getStringExtra("userAccount")).build();
        //向后台发送请求
        // TODO: 需修改为本电脑 ipV4 地址，可在cmd命令行内输入ipconfig进行查询
        String url = "http://192.168.0.102:8080/user/findAll";
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
                    UserLogin user = JSONObject.parseObject(json.getString("user"), UserLogin.class);
                    message = Message.obtain();
                    message.obj = user.getUserCardNumber();
                    message.what = 0x1111;
                    handler.sendMessage(message);
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x1111) {
                cardnumber = (String) msg.obj;
                try {
                    cardlist();
                } catch (Exception ignore) {
                    Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    // TODO: 无绑定银行卡时，绑定即更新卡号时重新获取银行卡绑定信息
    @SuppressLint("SetTextI18n")
    public void cardlist(){
        card_info_more = findViewById(R.id.card_info_more);
        card_info_more.setVisibility(View.VISIBLE);
        unbind_txt = findViewById(R.id.unbind_txt);
        unbind_txt.setVisibility(View.GONE);
        card_txt = findViewById(R.id.card_txt);
        card_txt.setText(cardnumber.substring(0, 4) + " **** **** **** " + cardnumber.substring(16, 19));
        cardview = findViewById(R.id.cardview);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "您的19位数建行卡", Toast.LENGTH_SHORT).show();
            }
        });
               /*
                 0.抽取 我的界面的底部列表视图，并实现监听
                 1.实现list中具体的数据
                 2.设计布局文件
                 3.设置adapter适配器
                */
        listview_below_card = findViewById(R.id.listview_below_card);
        list = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("001图片", images[i]);
            map.put("002文字", items[i]);
            list.add(map);
        }
        adapter = new Myadapter(getApplicationContext(), list, R.layout.list_bottom_bg, new String[]{"001图片", "002文字"}
                , new int[]{R.id.select_imageview, R.id.select_title});
        listview_below_card.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {        //重写手机返回键的方法
        // 重新获取数据库内的 昵称，响应 我的界面 的请求，顺利返回 我的界面
        setResult(0x16);  //响应 绑定银行卡界面 0x20请求的来源，发出0x30响应码
        finish();
    }
}