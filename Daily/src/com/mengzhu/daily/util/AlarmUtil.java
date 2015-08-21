package com.mengzhu.daily.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.receiver.AlarmReceiver;

public class AlarmUtil {

	public static void setAlarms(Context context, Timed timed) {
		AlarmManager am = getAlarmManager(context);
		
		String timedStr = GsonUtil.objToStr(timed);
		
		Intent intent = new Intent();
		intent.setAction(AlarmReceiver.ALARM_RECEIVER_ACTION);
		
		intent.putExtra(Timed.GSON_KEY, timedStr);
		PendingIntent pi = PendingIntent.getBroadcast(context, timed.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP,
				timed.getTime(), pi);
	}
	
	public static void cancelAlarms(Context context, Timed timed) {
		
		Intent intent = new Intent();
		intent.setAction(AlarmReceiver.ALARM_RECEIVER_ACTION);
		PendingIntent pIntent = PendingIntent.getBroadcast(context, timed.getId(), intent, 0);
		AlarmManager am = getAlarmManager(context);
		am.cancel(pIntent);
	}
	
	/**
	 * 设置 repeat定时
	 * 
	 * @param context
	 * @param beginTime，  定时第一次的执行时间
	 * @param interval， 重复执行的间隔时间
	 */
	public static void setRepeatAlarms(Context context, long beginTime, long interval) {
		Intent intent =new Intent();
	    intent.setAction(AlarmReceiver.ALARM_RECEIVER_REPEAT_ACTION);
	    
	    PendingIntent sender=PendingIntent
	        .getBroadcast(context, 0, intent, 0);
	    
	    AlarmManager am=getAlarmManager(context);
	    am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP
	            , beginTime, interval, sender);
	}

	public static AlarmManager getAlarmManager(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		return am;
	}
	

}
