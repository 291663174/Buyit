package com.example.buyit.dengluhou.sigezhujiemian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.buyit.R;
import com.example.buyit.kefu.messageList;
import com.example.buyit.shezhi.tuozhanjiemian.exit;

import java.util.Timer;
import java.util.TimerTask;

public class mainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    //implements是一个类实现接口用的关键字，用来实现监听接口中定义的抽象方法。

    private static boolean mBackKeyPressed = false;//记录是否有首次按手机返回键

    private myFragmentJiFen fg2;       //创建获取其他3个界面布局的对象
    private myFragmentIndex fg1;
    private messageList fg3;
    private myFragmentMy fg4;                  //创建获取我的界面布局的对象
    private FragmentManager fManager;           //要管理activity中的fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //判断是否有相机权限
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }

        fManager = getFragmentManager();        //配合FragmentManager获得activity中的fragment

        //获取底部导航栏按钮组
        RadioGroup rg_nav_bar = findViewById(R.id.rg_nav_bar);
        rg_nav_bar.setOnCheckedChangeListener(this);

        //默认获取第一个单选界面按钮，并设置为开启APP默认选中状态
        RadioButton rb_1 = findViewById(R.id.rb_1);
        rb_1.setChecked(true);

        exit.getInstance().addActivity(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        //实现底部导航栏的循环，checkedId就是获取每项的ID进行循环

        FragmentTransaction fTransaction = fManager.beginTransaction(); //开启事务

        hideAllFragment(fTransaction);      //调用隐藏界面函数

        switch (checkedId) {
            case R.id.rb_1:
                if (fg1 == null) {
                    fg1 = new myFragmentIndex();    //给界面传值
                    fTransaction.add(R.id.ly_content, fg1);     //将一个fragment实例添加到Activity的最上层
                } else {
                    fTransaction.show(fg1);                     //将fragment 1界面显示出来
                }
                break;
            case R.id.rb_2:
                if (fg2 == null) {
                    fg2 = new myFragmentJiFen();
                    fTransaction.add(R.id.ly_content, fg2);
                } else {
                    fTransaction.show(fg2);
                }
                break;
            case R.id.rb_3:
                if (fg3 == null) {
                    fg3 = new messageList();
                    fTransaction.add(R.id.ly_content, fg3);
                } else {
                    fTransaction.show(fg3);
                }
                break;
            case R.id.rb_4:
                if (fg4 == null) {
                    fg4 = new myFragmentMy();
                    fTransaction.add(R.id.ly_content, fg4);
                } else {
                    fTransaction.show(fg4);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);
    }

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