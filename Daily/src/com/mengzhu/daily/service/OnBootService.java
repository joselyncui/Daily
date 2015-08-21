package com.mengzhu.daily.service;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.util.AlarmUtil;
import com.mengzhu.daily.util.DateUtil;

/**
 * 后台service 在重启或者关闭飞行模式之后 重新设置定时任务 设置完成之后自动关闭
 * 
 * @author MGC01
 * 
 */
public class OnBootService extends IntentService {

	private Context mContext;

	public OnBootService() {
		super("onBootService");
	}

	public OnBootService(String name) {
		super(name);
	}

	DailyDataSource dataSource;
	List<Timed> timeds = new ArrayList<>();

	@Override
	public void onCreate() {

		System.out.println("on boot service create");
		mContext = this;
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

	@Override
	protected void onHandleIntent(Intent intent) {
		dataSource = DailyDataSource.getInstance(mContext);
		timeds = dataSource.getTimeds();

		for (int i = 0; i < timeds.size(); i++) {
			if (timeds.get(i).getTime() > System.currentTimeMillis()) {
				AlarmUtil.setAlarms(mContext, timeds.get(i));
			}
		}

		//删除今天之前的所有任务
		dataSource.clearTask(DateUtil.getStartTime());
		dataSource.clearTimed(DateUtil.getStartTime());
		
		//设置定时删除任务, 每天一执行
		AlarmUtil.setRepeatAlarms(mContext, DateUtil.getEndTime(), DateUtil.getDayMilSecond());

	}

	@Override
	public void onDestroy() {
		System.out.println("on boot destory");
		super.onDestroy();
	}

}
