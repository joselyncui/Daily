package com.mengzhu.daily.util;

import android.app.Activity;

public class ScreenUtil {
	
	public static int getStatuBarHeight(Activity activity){
		 int result = 0;
	      int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
	      if (resourceId > 0) {
	          result = activity.getResources().getDimensionPixelSize(resourceId);
	      } 
	      return result;
	}
}
