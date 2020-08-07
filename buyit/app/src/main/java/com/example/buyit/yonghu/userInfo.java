package com.example.buyit.yonghu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.buyit.Myadapter;
import com.example.buyit.R;
import com.example.buyit.UserLogin;
import com.example.buyit.shezhi.tuozhanjiemian.exit;
import com.example.buyit.yonghu.xinxigengxin.updateEmail;
import com.example.buyit.yonghu.xinxigengxin.updateName;
import com.example.buyit.yonghu.xinxigengxin.updateTelphone;
import org.jetbrains.annotations.NotNull;
import java.io.FileNotFoundException;
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

// TODO: 设计 用户信息 界面
public class userInfo extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;//声明一个请求码，用于识别返回的结果

    String[] titles = new String[]{"头像", "昵称", "手机号", "电子邮箱"};
    private String useraccount;
    private String nicheng;
    private String number;
    private String email;
    private Dialog dialog;

    public static String MIME_TYPE_IMAGE_JPEG = "image/*";
    public static final int ACTIVITY_GET_IMAGE = 0;

    private Message message;
    private Message message1;
    private Message message2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        useraccount = getIntent().getStringExtra("userAccount");
        nicheng = getIntent().getStringExtra("nicheng");
        number = getIntent().getStringExtra("userphonenumber");
        email = getIntent().getStringExtra("useremail");

        //用户信息界面的列表设计
        listmenu();

        //返回fragment_my界面，属于activity向fragment跳转
        ImageButton back = findViewById(R.id.back_image_button_user);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0x12);  //响应 我的界面 0x02请求的来源，发出0x12响应码
                finish();
            }
        });
    }

    // TODO: 用户信息界面的列表设计
    private void listmenu() {
        String[] right_txt = new String[]{"", nicheng, number, email};
        int[] images = new int[]{R.mipmap.man, 0, 0, 0};
           /*
        0.抽取 user_info(用户信息)界面中间的列表视图，并实现监听
        1.实现list中具体的数据
        2.设计布局文件
        3.设置adapter适配器
        对底部列表框点击实现的效果
        */
        ListView listview_info_bottom = findViewById(R.id.listview_info_bottom);
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("001框名", titles[i]);
            map.put("002图片", images[i]);
            map.put("003文字", right_txt[i]);
            list.add(map);
        }
        Myadapter adapter = new Myadapter(getApplicationContext(), list, R.layout.listview_info_bg, new String[]{"001框名", "002图片", "003文字"}
                , new int[]{R.id.info_title, R.id.edit_left_image, R.id.right_text});
        listview_info_bottom.setAdapter(adapter);
        listview_info_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i) {
                    case 0:
                        @SuppressLint("InflateParams")
                        View view_photo = getLayoutInflater().inflate(R.layout.update_photo, null);
                        dialog = new Dialog(userInfo.this, R.style.theme);
                        dialog.setContentView(view_photo, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        Window window = dialog.getWindow();
                        //设置显示渐渐出现效果
                        assert window != null;
                        window.setWindowAnimations(R.style.update_photo_animstyle);
                        WindowManager.LayoutParams wl = window.getAttributes();
                        //保证在底部，默认横坐标wl.x = 0;
                        wl.y = getWindowManager().getDefaultDisplay().getHeight();
                        // 以下这两句是为了保证按钮可以水平满屏
                        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        dialog.onWindowAttributesChanged(wl);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), updateName.class);
                        intent1.putExtra("nicheng", nicheng);
                        intent1.putExtra("userAccount", useraccount);
                        startActivityForResult(intent1, 0x03);    //标识请求的来源0x03,跳转更新昵称页面
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), updateTelphone.class);
                        intent2.putExtra("userphonenumber", number);
                        intent2.putExtra("userAccount", useraccount);
                        startActivityForResult(intent2, 0x04);    //标识请求的来源0x04,跳转更新电话页面
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), updateEmail.class);
                        intent3.putExtra("useremail", email);
                        intent3.putExtra("userAccount", useraccount);
                        startActivityForResult(intent3, 0x05);    //标识请求的来源0x05,跳转更新邮箱页面
                        break;
                }
            }
        });

        exit.getInstance().addActivity(this);
    }

    @SuppressLint("Assert")
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0x03 && resultCode == 0x13) {   //对 标识请求的来源0x03 和 响应0x03请求的来源0x13 进行判断，是否符合跳转要求
            InitData();
            listmenu();
        }

        if (requestCode == 0x04 && resultCode == 0x14) {   //对 标识请求的来源0x04 和 响应0x04请求的来源0x14 进行判断，是否符合跳转要求
            InitData();
            listmenu();
        }

        if (requestCode == 0x05 && resultCode == 0x15) {   //对 标识请求的来源0x05 和 响应0x05请求的来源0x15 进行判断，是否符合跳转要求
            InitData();
            listmenu();
        }

        if (requestCode == TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = null;
                    assert false;
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    ImageView touxiang = findViewById(R.id.edit_left_image);
                    touxiang.setImageBitmap(bitmap);
                    //将图片解析成Bitmap对象，并把它显现出来
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {        //重写手机返回键的方法
        setResult(0x12);  //响应 我的界面 0x02请求的来源，发出0x12响应码
        finish();
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
                    message.obj = user.getUserName();
                    message.what = 0x1111;

                    message1 = Message.obtain();
                    message1.obj = user.getUserPhoneNumber();
                    message1.what = 0x2222;

                    message2 = Message.obtain();
                    message2.obj = user.getUserEmail();
                    message2.what = 0x3333;

                    handler.sendMessage(message);
                    handler.sendMessage(message1);
                    handler.sendMessage(message2);
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
            switch (msg.what) {
                case 0x1111:
                    nicheng = (String) msg.obj;
                    listmenu();
                    break;
                case 0x2222:
                    number = (String) msg.obj;
                    listmenu();
                    break;
                case 0x3333:
                    email = (String) msg.obj;
                    listmenu();
                    break;
            }
        }
    };

    public void take_photos(View view) {
        //使用隐示的Intent，系统会找到与它对应的活动，即调用摄像头，并把它存储
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, (android.os.Parcelable) null);
        startActivityForResult(intent, TAKE_PHOTO);
        //调用会返回结果的开启方式，返回成功的话，则把它显示出来
        dialog.dismiss();
    }

    public void select_from_photos(View view) {
        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
        getImage.addCategory(Intent.CATEGORY_OPENABLE);
        getImage.setType(MIME_TYPE_IMAGE_JPEG);
        startActivityForResult(getImage, ACTIVITY_GET_IMAGE);
    }

    public void cancel(View view) {
        dialog.dismiss();
    }
}