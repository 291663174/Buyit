package com.example.buyit.dengluhou.sigezhujiemian;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.buyit.R;
import com.example.buyit.pay.dianshiPay;
import com.example.buyit.pay.shafaPay;
import com.example.buyit.pay.shounahePay;
import com.example.buyit.pay.zhiwujiaPay;

public class myFragmentIndex extends Fragment {

    private Handler handler;
    private int position = 0;
    private int i_position = 0;
    private EditText search_bar_hint;
    private String[] ads;
    private ImageView ads_image;
    private int[] ads_images;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_index, container, false);
        ads = new String[]{"速购室内加湿器", "索尼降噪耳机", "未闻花名", "速购网络打印店", "小米无声风扇"};
        ads_images = new int[]{R.drawable.ad2, R.drawable.ad3, R.drawable.ad1};
        search_bar_hint = view.findViewById(R.id.search_bar_hint);
        ads_image = view.findViewById(R.id.ads_image);
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                search_bar_hint.setHint(ads[position]);
                ads_image.setImageResource(ads_images[i_position]);
                i_position = (position < 2) ? position + 1 : 0;
                position = (position < 4) ? position + 1 : 0;
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);

        ImageView dianshi = view.findViewById(R.id.dianshi);
        dianshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), dianshiPay.class);
                startActivityForResult(intent, 0x21);    //标识请求的来源0x21,跳转 电视柜详情 页面
            }
        });

        ImageView shounahe = view.findViewById(R.id.shounahe);
        shounahe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), shounahePay.class);
                startActivityForResult(intent, 0x23);    //标识请求的来源0x22,跳转系统设置页面
            }
        });

        ImageView shafa = view.findViewById(R.id.shafa);
        shafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), shafaPay.class);
                startActivityForResult(intent, 0x25);    //标识请求的来源0x25,跳转系统设置页面
            }
        });

        ImageView zhiwujia = view.findViewById(R.id.zhiwujia);
        zhiwujia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), zhiwujiaPay.class);
                startActivityForResult(intent, 0x27);    //标识请求的来源0x27,跳转系统设置页面
            }
        });

        getActivity().setResult(0x51);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x21 && resultCode == 0x31) {   //对 标识请求的来源0x21 和 响应0x21请求的来源0x31 进行判断，是否符合跳转要求
            //获取界面无暇切换的实现，从 商品信息 界面(activity)回到 首页 界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();
        }
        if (requestCode == 0x23 && resultCode == 0x33) {   //对 标识请求的来源0x23 和 响应0x23请求的来源0x33 进行判断，是否符合跳转要求
            //获取界面无暇切换的实现，从 商品信息 界面(activity)回到 首页 界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();
        }
        if (requestCode == 0x25 && resultCode == 0x35) {   //对 标识请求的来源0x25 和 响应0x21请求的来源0x35 进行判断，是否符合跳转要求
            //获取界面无暇切换的实现，从 商品信息 界面(activity)回到 首页 界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();
        }
        if (requestCode == 0x27 && resultCode == 0x37) {   //对 标识请求的来源0x27 和 响应0x27请求的来源0x37 进行判断，是否符合跳转要求
            //获取界面无暇切换的实现，从 商品信息 界面(activity)回到 首页 界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();
        }
        if (resultCode == 0x41) {   //对 响应请求的来源0x41 进行判断，是否符合跳转要求
            //获取界面无暇切换的实现，从 商品信息 界面(activity)回到 首页 界面(fragment)
            mainActivity mainActivity = (mainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();
        }
    }
}
