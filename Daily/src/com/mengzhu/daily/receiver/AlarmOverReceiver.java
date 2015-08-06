package com.mengzhu.daily.receiver;

import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.util.GsonUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmOverReceiver extends BroadcastReceiver{
	
	public static final String ACTION_ALARM_OVER = "com.mengzhu.daily.receiver.AlarmOverReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		String timedStr = intent.getExtras().getString(Timed.GSON_KEY);
		Timed timed = GsonUtils.strToObj(Timed.class, timedStr);
		
		System.out.println("over ");
	}

}
