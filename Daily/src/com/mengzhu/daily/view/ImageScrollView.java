package com.mengzhu.daily.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ImageScrollView extends HorizontalScrollView{
	private Context context;
	private int imgWidth;
	private int imgHeight;

	public ImageScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}

}
