package com.mengzhu.daily.view;

import com.mengzhu.daily.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TextSeekBar extends LinearLayout implements OnSeekBarChangeListener{
	private View layout;
	private SeekBar seekBar;
	private TextView textView;

	public TextSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		layout = View.inflate(context, R.layout.text_seekbar_layout, this);
		seekBar = (SeekBar) layout.findViewById(R.id.seekBar);
		textView = (TextView) layout.findViewById(R.id.textview);
		
		seekBar.setOnSeekBarChangeListener(this);
		seekBar.setProgress(1);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		textView.setText((progress+1)+"");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	public float getProgress(){
		return seekBar.getProgress() + 1;
	}
	
	public void setMax(int max){
		seekBar.setMax(max);
	}
	
	public void setProgress(int progress) {
		seekBar.setProgress(progress-1);
		textView.setText(progress+"");
	}

}
