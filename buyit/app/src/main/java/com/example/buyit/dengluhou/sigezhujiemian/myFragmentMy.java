package com.example.buyit.dengluhou.sigezhujiemian;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.buyit.Myadapter;
import com.example.buyit.R;
import com.example.buyit.UserLogin;
import com.example.buyit.shezhi.setting;
import com.example.buyit.wode.jiemian.Card;
import com.example.buyit.wode.jiemian.dialogKeFu;
import com.example.buyit.wode.jiemian.faq;
import com.example.buyit.wode.jiemian.footPrint;
import com.example.buyit.wode.jiemian.transactionRecords;
import com.example.buyit.yonghu.userInfo;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// TODO: 设计 “ 我的 ” 界面
public class myFragmentMy extends Fragment {

    private TextView nicheng;
    String[] items = new String[]{"我的卡包", "联系客服", "我的足迹", "交易记录", "常见问题", "分享好友"};
    int[] images = new int[]{R.mipmap.card, R.mipmap.kefu, R.mipmap.foot, R.mipmap.jilu, R.mipmap.wenti, R.mipmap.fenxiang};
    private Message message;
    private Message message1;
    private Message message2;
    private Message message3;
    private String nich;
    private String userphonenumber;
    private String useremail;
    private String usercardnumber;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        //获取我的界面模板fg_my
        view = inflater.inflate(R.layout.fg_my, container, false);

        //对右上角"设置"按钮进行监听,点击可跳转系统设置界面
        RadioButton rb_setting = view.findViewById(R.id.rb_s);
        rb_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), setting.class);
                startActivityForResult(intent, 0x01);    //标识请求的来源0x01,跳转系统设置页面
            }
        });

        /*
            获取头像和昵称以及右边的箭头，并实现监听，点击了就会跳转到user_info（用户信息）界面，属于fragment向activity跳转
        */
        ImageView touxiang = view.findViewById(R.id.touxiang);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), userInfo.class);
                intent.putExtra("nicheng", nich);
                intent.putExtra("useremail", useremail);
                intent.putExtra("userphonenumber", userphonenumber);
                intent.putExtra("userAccount", getActivity().getIntent().getStringExtra("userAccount"));
                startActivityForResult(intent, 0x02);    //标识请求的来源0x02,跳转用户信息页面
            }
        });
        nicheng = view.findViewById(R.id.nicheng);
        nicheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), userInfo.class);
                intent.putExtra("nicheng", nich);
                intent.putExtra("useremail", useremail);
                intent.putExtra("userphonenumber", userphonenumber);
                intent.putExtra("userAccount", getActivity().getIntent().getStringExtra("userAccount"));
                startActivityForResult(intent, 0x02);    //标识请求的来源0x02,跳转用户信息页面
            }
        });
        ImageView right_to_info = view.findViewById(R.id.right_to_info);
        right_to_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), userInfo.class);
                intent.putExtra("nicheng", nich);
                intent.putExtra("useremail", useremail);
                intent.putExtra("userphonenumber", userphonenumber);
                intent.putExtra("userAccount", getActivity().getIntent().getStringExtra("userAccount"));
                startActivityForResult(intent, 0x02);    //标识请求的来源0x02,跳转用户信息页面
            }
        });

        // TODO: 获取 我的界面 的列表菜单栏
        listmenu();

        //获取数据库内的对应用户相关信息
        InitData();

        return view;
    }

    private void listmenu() {

       /*
        0.抽取 我的界面的底部列表视图，并实现监听
        1.实现list中具体的数据
        2.设计布局文件
        3.设置adapter适配器
        对底部列表框点击实现的效果
        */
        ListView listview_bottom = view.findViewById(R.id.listview_my_bottom);
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("001图片", images[i]);
            map.put("002文字", items[i]);
            list.add(map);
        }
        Myadapter adapter = new Myadapter(getActivity().getApplicationContext(), list, R.layout.list_bottom_bg, new String[]{"001图片", "002文字"}
                , new int[]{R.id.select_imageview, R.id.select_title});
        listview_bottom.setAdapter(adapter);
        listview_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getActivity().getApplicationContext(), Card.class);
                        intent.putExtra("userAccount", getActivity().getIntent().getStringExtra("userAccount"));
                        intent.putExtra("nicheng", nich);
                        intent.putExtra("usercardnumber", usercardnumber);
                        startActivityForResult(intent, 0x06);    //标识请求的来源0x06,跳转绑定银行卡页面
                        break;
                    case 1:
                        showDialog();
                        break;
                    case 2:
                        Intent intent1 = new Intent(getActivity().getApplicationContext(), footPrint.class);
                        startActivityForResult(intent1, 0x07);    //标识请求的来源0x07,跳转 我的足迹 页面
                        break;
                    case 3:
                        Intent intent2 = new Intent(getActivity().getApplicationContext(), transactionRecords.class);
                        startActivityForResult(intent2, 0x08);    //标识请求的来源0x08,跳转 交易记录 页面
                        break;
                    case 4:
                        Intent intent3 = new Intent(getActivity().getApplicationContext(), faq.class);
                        startActivityForResult(intent3, 0x09);    //标识请求的来源0x09,跳转 faq常见问题 页面
                        break;
                    case 5:
                        //判断手机内是否有微信app
                        isWeixinAvilible(getActivity().getApplicationContext());
                        Intent intent4 = new Intent();
                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                        intent4.setAction(Intent.ACTION_MAIN);
                        intent4.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent4.setComponent(cmp);
                        startActivity(intent4);
                        break;
                }
            }
        });
    }

    private void showDialog() {
        FragmentManager fm = getActivity().getFragmentManager();
        dialogKeFu dialoga = new dialogKeFu();
        dialoga.show(fm, "fragmenta");
    }

    //根据用户账号去获取数据库中的用户信息
    private void InitData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //FromBody用于提交表单键值对,key-value,其作用类似于HTML中的<form>标记。比如username="1001",age="123456"等类似的键值对
        FormBody formBody = new FormBody.Builder().add("userAccount",
                getActivity().getIntent().getStringExtra("userAccount")).build();
        //向后台发送请求
        // TODO: 需修改为本电脑 ipV4 地址，可在cmd命令行内输入ipconfig进行查询
        //http://192.168.0.102为电脑开cmd ipconfig显示的IP V4 地址，且手机需和电脑处于一个WIFI，后面的路径是指向后台服务接口
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
                    message1.obj = user.getUserEmail();
                    message1.what = 0x2222;

                    message2 = Message.obtain();
                    message2.obj = user.getUserPhoneNumber();
                    message2.what = 0x3333;

                    message3 = Message.obtain();
                    message3.obj = user.getUserCardNumber();
                    message3.what = 0x4444;

                    handler.sendMessage(message);
                    handler.sendMessage(message1);
                    handler.sendMessage(message2);
                    handler.sendMessage(message3);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
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
                    nich = (String) msg.obj;
                    nicheng.setText(nich);
                    break;
                case 0x2222:
                    useremail = (String) msg.obj;
                    break;
                case 0x3333:
                    userphonenumber = (String) msg.obj;
                    break;
                case 0x4444:
                    usercardnumber = (String) msg.obj;
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x01 && resultCode == 0x11) {   //对 标识请求的来源0x01 和 响应0x01请求的来源0x11 进行判断，是否符合跳转要求
            //获取界面无暇切换的实现，从设置界面(activity)回到我的(用户)界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();

            //切换回界面，重新更新用户昵称
            InitData();
        }

        if (requestCode == 0x02 && resultCode == 0x12) {   //对 标识请求的来源0x02 和 响应0x02请求的来源0x12 进行判断，是否符合跳转要求

            //获取界面无暇切换的实现，从用户信息界面(activity)回到我的(用户)界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();

            //切换回界面，重新更新用户昵称
            InitData();

        }

        if (requestCode == 0x06 && resultCode == 0x16) {   //对 标识请求的来源0x03 和 响应0x03请求的来源0x13 进行判断，是否符合跳转要求
            //返回fragment_my界面，属于activity向fragment跳转
            //获取界面无暇切换的实现，从用户信息界面(activity)回到我的(用户)界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();

            //切换回界面，重新更新用户昵称
            InitData();
        }

        if (requestCode == 0x07 && resultCode == 0x17) {   //对 标识请求的来源0x03 和 响应0x03请求的来源0x13 进行判断，是否符合跳转要求
            //返回fragment_my界面，属于activity向fragment跳转
            //获取界面无暇切换的实现，从用户信息界面(activity)回到我的(用户)界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();

            //切换回界面，重新更新用户昵称
            InitData();
        }

        if (requestCode == 0x08 && resultCode == 0x18) {   //对 标识请求的来源0x03 和 响应0x03请求的来源0x13 进行判断，是否符合跳转要求
            //返回fragment_my界面，属于activity向fragment跳转
            //获取界面无暇切换的实现，从用户信息界面(activity)回到我的(用户)界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();

            //切换回界面，重新更新用户昵称
            InitData();
        }

        if (requestCode == 0x09 && resultCode == 0x19) {   //对 标识请求的来源0x03 和 响应0x03请求的来源0x13 进行判断，是否符合跳转要求
            //返回fragment_my界面，属于activity向fragment跳转
            //获取界面无暇切换的实现，从用户信息界面(activity)回到我的(用户)界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();

            //切换回界面，重新更新用户昵称
            InitData();
        }
    }

    public static void isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return;
                }
            }
        }
    }

}