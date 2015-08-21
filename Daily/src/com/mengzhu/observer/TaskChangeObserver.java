package com.mengzhu.observer;

import android.database.ContentObserver;
import android.os.Handler;

public class TaskChangeObserver extends ContentObserver{

	public TaskChangeObserver(Handler handler) {
		super(handler);
	}

	@Override
	public boolean deliverSelfNotifications() {
		return super.deliverSelfNotifications();
	}
}
