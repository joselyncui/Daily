package com.mengzhu.daily.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.receiver.AlarmReceiver;

public class AlarmUtils {

	public static void setAlarms(Context context, Timed timed) {
		System.out.println("alarm create " );
		AlarmManager am = getAlarmManager(context);
		Intent intent = new Intent();
		intent.setAction(AlarmReceiver.ALARM_RECEIVER_ACTION);
		
		intent.putExtra("comment", timed.getComment());
		intent.putExtra("time", timed.getTime());
		PendingIntent pi = PendingIntent.getBroadcast(context, timed.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP,
				timed.getTime(), pi);
	}
	
	public static void cancelAlarms(Context context, Timed timed) {
		System.out.println("alarm cancel " + timed.getId());
		Intent intent = new Intent();
		intent.setAction(AlarmReceiver.ALARM_RECEIVER_ACTION);
		PendingIntent pIntent = PendingIntent.getBroadcast(context, timed.getId(), intent, 0);
		AlarmManager am = getAlarmManager(context);
		am.cancel(pIntent);
	}

	public static AlarmManager getAlarmManager(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		return am;
	}

}
