package com.example.buyit.dengluhou.sigezhujiemian;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.buyit.R;

// TODO: 积分界面
@SuppressLint("ValidFragment")
public class myFragmentJiFen extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_jifen,container,false);
    }

}
