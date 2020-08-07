package com.example.buyit.shezhi.tuozhanjiemian;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class exit extends Application {

    private List<Activity> list = new ArrayList<Activity>();
    private static exit ex;
    private exit() {
    }
    public static exit getInstance() {
        if (null == ex) {
            ex = new exit();
        }
        return ex;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public exit(Context context) {
        for (Activity activity : list) {
            activity.finish();
        }
        System.exit(0);
    }

}
