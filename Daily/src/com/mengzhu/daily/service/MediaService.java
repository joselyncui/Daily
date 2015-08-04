package com.mengzhu.daily.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

import com.mengzhu.daily.R;

public class MediaService extends Service{
	
	private MediaPlayer mPlayer;
	

	@Override
	public void onCreate() {
		
		System.out.println("media create ");
		mPlayer = new MediaPlayer();
		mPlayer = MediaPlayer.create(MediaService.this,R.raw.alarm);
		super.onCreate();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//开始播放
		mPlayer.start();
		mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				//释放资源
				mPlayer.release();
				System.out.println("on error media " + what + "  " + extra);
				return false;
			}
		});
		
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				stopSelf();//完成之后关闭service
			}
		});
		return super.onStartCommand(intent, flags, startId);
	}

	
	@Override
	public void onDestroy() {
		System.out.println("service destory");
		mPlayer.stop();
		mPlayer.release();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
