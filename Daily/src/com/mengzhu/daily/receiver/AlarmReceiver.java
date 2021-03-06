package com.mengzhu.daily.receiver;

import com.mengzhu.daily.AddTimedActivity;
import com.mengzhu.daily.MainActivity;
import com.mengzhu.daily.R;
import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.service.CleanDataService;
import com.mengzhu.daily.service.MediaService;
import com.mengzhu.daily.util.GsonUtil;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

public class AlarmReceiver extends BroadcastReceiver{
	public static final String ALARM_RECEIVER_ACTION = "com.mengzhu.daily.receiver.AlarmReceiver";
	public static final String ALARM_RECEIVER_REPEAT_ACTION = "com.mengzhu.daily.receiver.AlarmReceiver.Repeat";
	
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		
		String action = intent.getAction();
		if (TextUtils.equals(ALARM_RECEIVER_ACTION, action)) {
			String timedStr = intent.getExtras().getString(Timed.GSON_KEY);
			Timed timed = GsonUtil.strToObj(Timed.class,timedStr);
			
			createNotification(timed.getComment());
			Intent serviceIntent = new Intent(context, MediaService.class);
			context.startService(serviceIntent);
			
			//发送闹钟结束广播
			Intent alarmOverReceiver = new Intent(AlarmOverReceiver.ACTION_ALARM_OVER);
			alarmOverReceiver.putExtra(Timed.GSON_KEY, timedStr);
			context.sendBroadcast(alarmOverReceiver);
		} else if (TextUtils.equals(ALARM_RECEIVER_REPEAT_ACTION, action)) {
			Intent cleanIntent = new Intent(context, CleanDataService.class);
			context.startService(cleanIntent);
		}
		
	}
	
	private void createNotification(String comment){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.timed)
		        .setContentTitle("主人~ 起来嗨")
		        .setContentText(comment);
		
		Intent resultIntent = new Intent(context, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(AddTimedActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(-1, mBuilder.build());
	}

}
