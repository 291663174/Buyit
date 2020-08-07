package com.example.buyit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

// TODO: 定义MyFragment_my和user_info类中用到的 Listview （列表视图）的模板
public class Myadapter extends SimpleAdapter {

    public Myadapter(Context context, List<?extends Map<String,?>> data, int resource, String[] from,
                     int[] to) {
        super(context,data,resource,from,to);
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        return super.getView(i, convertView, viewGroup);
    }
}
