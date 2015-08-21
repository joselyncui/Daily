package com.mengzhu.daily.view;

import com.mengzhu.daily.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class IconImage extends ImageView{
	Drawable bgBitmap;
	private Context context;

	public IconImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_title);
		canvas.drawBitmap(bitmap, 0, 0, null);
	}

}
