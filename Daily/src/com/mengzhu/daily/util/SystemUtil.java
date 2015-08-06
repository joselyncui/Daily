package com.mengzhu.daily.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class SystemUtil {

	public static boolean isForeground(Context context, String className) {

		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}

		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = aManager.getRunningTasks(1);
		if (!list.isEmpty()) {
			ComponentName cpn = list.get(0).topActivity;
			if (TextUtils.equals(className, cpn.getClassName())) {
				return true;
			}
		}

		return false;

	}
	

	/**
	 * 判断应用程序是否在后台运行
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					Log.i("后台", appProcess.processName);
					return true;
				} else {
					Log.i("前台", appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

}
