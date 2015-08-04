package com.mengzhu.daily.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CWebView extends LinearLayout{
	private Context context;

	public CWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	private void init(){
		TextView text1 = new TextView(context);
		text1.setText("title");
		TextView text2 = new TextView(context);
		text2.setText("content");
		
		addView(text1);
		addView(text2);
	}
}
