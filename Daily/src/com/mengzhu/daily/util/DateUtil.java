package com.mengzhu.daily.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	public static long getTime(int hour, int minute){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), hour, minute,0);
		long millis = calendar.getTimeInMillis();
		
		return millis;
	}
	
	public static int getHour(){
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	public static int getMinute(){
		Calendar calendar = Calendar.getInstance();
		int minute = calendar.get(Calendar.MINUTE);
		return minute;
	}
	
	public static String dateFormat(long time) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		String hourStr = hour >= 10 ? ""+hour : "0" + hour;
		String minuteStr = minute >= 10? ""+minute :"0"+minute;
		return hourStr +":" + minuteStr;
	}
	
	public static int getHour(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	public static int getMinute(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int minute = calendar.get(Calendar.MINUTE);
		return minute;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static void format(long millims) {
		Date nowTime=new Date(millims); 
		SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd HH mm ss"); 
		System.out.println(time.format(nowTime)); 
	}
	
	public static long getStartTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
		long millis = calendar.getTimeInMillis();
		
		return millis;
	}
	
	public static long getEndTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
		long millis = calendar.getTimeInMillis();
		
		return millis;
	}
	
	public static long getDayMilSecond(){
		return 24*60*60 * 1000;
	}

}
