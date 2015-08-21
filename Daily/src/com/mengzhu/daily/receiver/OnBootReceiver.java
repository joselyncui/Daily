package com.mengzhu.daily.receiver;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.mengzhu.daily.AddTimedActivity;
import com.mengzhu.daily.MainActivity;
import com.mengzhu.daily.R;
import com.mengzhu.daily.service.OnBootService;

/**
 * 设备开机、 取消飞行模式 监听receiver
 * 
 * 收到广播后， 重新写入 定时任务
 * 
 * @author MGC01
 *
 */
public class OnBootReceiver extends BroadcastReceiver {
	private Context context;

	@SuppressLint("InlinedApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;

		if (Intent.ACTION_BOOT_COMPLETED.endsWith(intent.getAction())) {
			boot();
		} else if (Intent.ACTION_AIRPLANE_MODE_CHANGED.endsWith(intent
				.getAction())) {

			int isAirplaneMode = Settings.System.getInt(
					context.getContentResolver(),
					Settings.Global.AIRPLANE_MODE_ON, 0);
			boolean find = (isAirplaneMode == 1) ? true : false;
			if (!find) {

				System.out.println("find");
				boot();
			}
		}

	}

	private void boot() {
		// 开启后台服务
		Intent newIntent = new Intent(context, OnBootService.class);
		context.startService(newIntent);
	}

	@SuppressWarnings("unused")
	private void createNotification(String comment) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.timed)
				.setContentTitle("主人~ 起来嗨").setContentText(comment);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, MainActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(AddTimedActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(-1, mBuilder.build());
	}

}
