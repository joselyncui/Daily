package com.mengzhu.daily.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.util.AlarmUtils;

public class OnBootService extends Service{
	 DailyDataSource dataSource;
	 List<Timed> timeds = new ArrayList<>();

	@Override
	public void onCreate() {
		
		System.out.println("service create");
		dataSource = DailyDataSource.getInstance(this);
		timeds = dataSource.getTimeds();
		
		for (int i = 0; i < timeds.size(); i++) {
			if (timeds.get(i).getTime() > System.currentTimeMillis()) {
				AlarmUtils.setAlarms(this, timeds.get(i));
			}
		}
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
}
