package com.mengzhu.daily.service;

import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.util.DateUtil;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * 在每天结束之后被启动
 * 用户当天的额数据
 * 清理完成之后自动销毁
 * 
 * @author MGC01
 *
 */
public class CleanDataService extends IntentService{
	
	private DailyDataSource mDataSource;
	private Context mContext;
	private CleanDataService(){
		super("CleanDataService");
	}

	public CleanDataService(String name) {
		super(name);
	}

	@Override
	public void onCreate() {
		mContext = this;
		super.onCreate();
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		mDataSource = DailyDataSource.getInstance(mContext);
		mDataSource.clearTask(DateUtil.getEndTime());
		mDataSource.clearTimed(DateUtil.getEndTime());
	}

}
