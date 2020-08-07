package com.example.buyit.kefu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.buyit.R;

import java.util.List;

class ChatAdapter extends BaseAdapter {
	private List<ChatBean> chatBeanList; //聊天数据
	private LayoutInflater layoutInflater;
	ChatAdapter(List<ChatBean> chatBeanList, Context context) {
		this.chatBeanList = chatBeanList;
		layoutInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return chatBeanList.size();
	}
	@Override
	public Object getItem(int position) {
		return chatBeanList.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@SuppressLint("InflateParams")
    @Override
	public View getView(int position, View contentView, ViewGroup viewGroup) {
		Holder holder = new Holder();
		//判断当前的信息是发送的信息还是接收到的信息，不同信息加载不同的view
		if (chatBeanList.get(position).getState() == ChatBean.RECEIVE) {
			//加载左边布局，也就是机器人对应的布局信息
			contentView = layoutInflater.inflate(R.layout.chatting_robot_item,
					null);
		} else {
			//加载右边布局，也就是用户对应的布局信息
			contentView = layoutInflater.inflate(R.layout.chatting_user_item,
					null);
		}
		holder.tv_chat_content = contentView.findViewById(R.id.
				tv_chat_content);
		holder.tv_chat_content.setText(chatBeanList.get(position).getMessage());
		return contentView;
	}
	static class Holder {
		TextView tv_chat_content; // 聊天内容
	}
}

