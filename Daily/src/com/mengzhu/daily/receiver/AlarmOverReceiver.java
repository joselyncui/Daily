package com.mengzhu.daily.receiver;

import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.util.GsonUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmOverReceiver extends BroadcastReceiver{
	
	public static final String ACTION_ALARM_OVER = "com.mengzhu.daily.receiver.AlarmOverReceiver";
	private AlarmOverInterface alarmOverInterface;
	
	public AlarmOverReceiver(AlarmOverInterface alarmOverInterface) {
		this.alarmOverInterface = alarmOverInterface;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String timedStr = intent.getExtras().getString(Timed.GSON_KEY);
		Timed timed = GsonUtil.strToObj(Timed.class, timedStr);
		alarmOverInterface.onAlarmOver(timed.getId());
		
		System.out.println("over ");
	}
	
	public interface AlarmOverInterface{
		public void onAlarmOver(int timedId);
	}

}
