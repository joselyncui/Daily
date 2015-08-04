package com.mengzhu.daily.receiver;

import com.mengzhu.daily.AddTimedActivity;
import com.mengzhu.daily.MainActivity;
import com.mengzhu.daily.R;
import com.mengzhu.daily.service.MediaService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver{
	public static final String ALARM_RECEIVER_ACTION = "com.mengzhu.daily.receiver.AlarmReceiver";
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		String comment = intent.getExtras().getString("comment");
		createNotification(comment);
		Intent serviceIntent = new Intent(context, MediaService.class);
		context.startService(serviceIntent);
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
