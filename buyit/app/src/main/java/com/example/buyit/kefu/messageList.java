package com.example.buyit.kefu;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.buyit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class messageList extends Fragment {

    private ArrayList<ChatBean> chatBeans;
    public static final int MSG_OK = 1; // 获取数据
    private static final String WEB_SITE = "http://www.tuling123.com/openapi/api";
    // 唯一key，该key的值是从官网注册账号后获取的，注册地址来自于：http://www.tuling123.com/
    private static final String KEY = "40abb943778c472ea40e9c31e1a3c339";
    private MHandler mHandler;
    private EditText et_send_msg;
    private ChatAdapter adapter;
    private String sendMsg;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fy_messagelist,container,false);
        chatBeans = new ArrayList<>();
        mHandler = new MHandler();
        String[] welcome = getResources().getStringArray(R.array.welcome);
        ListView listView = view.findViewById(R.id.list);
        et_send_msg = view.findViewById(R.id.et_send_msg);
        Button btn_send = view.findViewById(R.id.btn_send);
        adapter = new ChatAdapter(chatBeans, mContext);
        listView.setAdapter(adapter);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
        int position = (int) (Math.random() * welcome.length - 1); // 获取一个随机数
        showData(welcome[position]); // 用随机数获取机器人的首次聊天信息
        return view;
    }

    private void sendData() {
        sendMsg = et_send_msg.getText().toString(); // 获取你输入的信息
        if (TextUtils.isEmpty(sendMsg)) { // 判断是否为空
            Toast.makeText(mContext, "你还未输任何信息,输出框不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        et_send_msg.setText("");
        // 替换空格和换行
        sendMsg = sendMsg.replaceAll(" ", "").replaceAll("\n", "").trim();
        ChatBean chatBean = new ChatBean();
        chatBean.setMessage(sendMsg);
        chatBean.setState(ChatBean.SEND); // SEND表示自己发送的信息
        chatBeans.add(chatBean); // 将发送的信息添加到chatBeanList集合中
        adapter.notifyDataSetChanged(); // 更新ListView列表
        getDataFromServer(); // 从服务器获取机器人发送的信息
    }

    private void getDataFromServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request =
                new Request.Builder().url(WEB_SITE + "?key=" + KEY + "&info=" + sendMsg).build();
        Call call = okHttpClient.newCall(request);
        // 开启异步线程访问网络
        call.enqueue(
                new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        Message msg = new Message();
                        msg.what = MSG_OK;
                        msg.obj = res;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {}
                });
    }

    /** 事件捕获 */
    @SuppressLint("HandlerLeak")
    class MHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == MSG_OK) {
                if (msg.obj != null) {
                    String vlResult = (String) msg.obj;
                    paresData(vlResult);
                }
            }
        }
    }
    private void paresData(String JsonData) { // Json解析
        try {
            JSONObject obj = new JSONObject(JsonData);
            String content = obj.getString("text"); // 获取的机器人信息
            int code = obj.getInt("code"); // 服务器状态码
            updateView(code, content); // 更新界面
        } catch (JSONException e) {
            e.printStackTrace();
            showData("网络未连接");
        }
    }
    private void showData(String message) {
        ChatBean chatBean = new ChatBean();
        chatBean.setMessage(message);
        chatBean.setState(ChatBean.RECEIVE); // RECEIVE表示接收到机器人发送的信息
        chatBeans.add(chatBean); // 将机器人发送的信息添加到chatBeanList集合中
        adapter.notifyDataSetChanged();
    }
    private void updateView(int code, String content) {
        // code有很多种状，先弄几组测试数据
        switch (code) {
            case 4004:
                showData("测试数据01");
                break;
            case 40005:
                showData("测试数据02");
                break;
            case 40006:
                showData("测试数据03");
                break;
            case 40007:
                showData("测试数据04");
                break;
            default:
                showData(content);
                break;
        }
    }

}
