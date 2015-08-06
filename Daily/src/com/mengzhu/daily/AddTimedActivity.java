package com.mengzhu.daily;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.util.AlarmUtil;
import com.mengzhu.daily.util.DateUtil;
import com.mengzhu.daily.view.PickerView;
import com.mengzhu.daily.view.PickerView.onSelectListener;

public class AddTimedActivity extends BaseActivity implements OnClickListener{

	private PickerView hourPv;
	private PickerView minuPv;
	private ImageView backImg;
	private ImageView saveImg;
	private TextView comTextView;
	private Button delBtn;
	private int hour;
	private int minute;
	private Timed timed;
	
	private DailyDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add_timed);
		hourPv = (PickerView) findViewById(R.id.hour_pv);
		minuPv = (PickerView) findViewById(R.id.minu_pv);
		backImg = (ImageView) findViewById(R.id.toolbar_back_img);
		saveImg = (ImageView) findViewById(R.id.toolbar_action_img);
		comTextView = (TextView) findViewById(R.id.timed_comment);
		delBtn = (Button) findViewById(R.id.btn_delete);
		
		backImg.setOnClickListener(this);
		saveImg.setOnClickListener(this);
		delBtn.setOnClickListener(this);
		
		dataSource = DailyDataSource.getInstance(this);
		
		initDatePicker();
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if (bundle != null ) {
			String timeStr = bundle.getString(Timed.GSON_KEY);
			Gson gson = new Gson();
			timed = gson.fromJson(timeStr, Timed.class);
			hour = DateUtil.getHour(timed.getTime());
			minute = DateUtil.getMinute(timed.getTime());
			comTextView.setText(timed.getComment());
			delBtn.setVisibility(View.VISIBLE);
		}
		
		hourPv.setSelected(hour);
		minuPv.setSelected(minute);
		
	}

	/**
	 * 初始化时间选择器
	 */
	private void initDatePicker(){
		List<String> hours = new ArrayList<String>();
		List<String> minus = new ArrayList<String>();
		
		for (int i = 0; i < 24; i++) {
			hours.add(i < 10 ? "0" + i : "" + i);
		}
		for (int i = 0; i < 60; i++) {
			minus.add(i < 10 ? "0" + i : "" + i);
		}
		
		hourPv.setData(hours);
		hourPv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				
				hour = Integer.parseInt(text);
				Toast.makeText(AddTimedActivity.this, "选择了 " + hour + " 时",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		minuPv.setData(minus);
		minuPv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				minute = Integer.parseInt(text);
				Toast.makeText(AddTimedActivity.this, "选择了 " + minute + " 分",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		hour = DateUtil.getHour();
		minute = DateUtil.getMinute();
	}
	/**
	 * 启动activity
	 * 
	 * @param activity
	 * @param timed
	 */
	public static void start(Activity activity, Timed timed) {
		Intent intent = new Intent(activity, AddTimedActivity.class);
		if (timed != null) {
			Gson gson = new Gson();
			String timedGson = gson.toJson(timed);
			intent.putExtra(Timed.GSON_KEY, timedGson);
		}
		
		
		activity.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toolbar_back_img:
			finish();
			break;
		case R.id.toolbar_action_img:
			addTimed();
			break;
		case R.id.btn_delete:
			delete();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 添加或者修改定时任务
	 */
	private void addTimed(){
		if (timed == null) {
			timed = new Timed();
		}
		timed.setComment(comTextView.getText().toString());
		timed.setIsOpen(Timed.OPEN);
		timed.setTime(DateUtil.getTime(hour, minute));
		if (timed.getTime()- System.currentTimeMillis() < 40) {
			Toast.makeText(this, "小于当前时间", Toast.LENGTH_LONG).show();
			return;
		}
		if (timed.getId()!=0) {
			dataSource.updTimed(timed);
		} else {
			long id = dataSource.addTimed(timed);
			timed.setId((int)id);
		}
		
		AlarmUtil.setAlarms(this, timed);//覆盖或者添加 定时
		finish();
	}
	
	/**
	 * 从数据库中删除定时任务
	 */
	private void delete(){
		dataSource.delTimed(timed);
		finish();
	}
	
}
