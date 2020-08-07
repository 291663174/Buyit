package com.example.buyit.kefu;

public class ChatBean {
	static final int SEND = 1; // 发送消息
	static final int RECEIVE = 2; // 接受消息

	private int state; // 消息的状态（是接受还是发送）
	private String message; // 消息的内容


	int getState() {
		return state;
	}

	void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
