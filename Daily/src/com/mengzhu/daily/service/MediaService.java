package com.mengzhu.daily.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.os.Vibrator;

import com.mengzhu.daily.R;

public class MediaService extends Service{
	
	private MediaPlayer mPlayer;
	private AudioManager audioManager;//音量管理器
	private Vibrator vibrator;
	

	@Override
	public void onCreate() {
		
		System.out.println("media create ");
		mPlayer = new MediaPlayer();
		mPlayer = MediaPlayer.create(MediaService.this,R.raw.alarm);
		
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_RING, 60,  
                0);
		
		/* 
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到 
         * */  
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启   
		vibrator.vibrate(pattern,2);  //重复两次上面的pattern 如果只想震动一次，index设为-1   
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
		vibrator.cancel();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
