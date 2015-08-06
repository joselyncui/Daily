package com.mengzhu.daily.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mengzhu.daily.R;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.util.AlarmUtil;
import com.mengzhu.daily.util.DateUtil;
import com.mengzhu.daily.view.SwitchButton;
import com.mengzhu.daily.view.SwitchButton.OnStateChangeListener;

public class TimedListAdapter extends SimpleAdapter<Timed>{
	
	private DailyDataSource database;
	private Context context;

	public TimedListAdapter(Context context,List<Timed> times) {
		super(context, times);
		this.context = context;
		this.database = DailyDataSource.getInstance(context);
	}

	@Override
	public int getItemResourceId() {
		return R.layout.timed_list_item_layout;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		final Timed timed = getItem(position);
		TextView comment = holder.findView(R.id.item_com);
		TextView time = holder.findView(R.id.item_time);
		SwitchButton switchBtn = holder.findView(R.id.item_toggle);
		
		comment.setText(timed.getComment());
		time.setText(DateUtil.dateFormat(timed.getTime()));
		
		if (timed.getTime() <=  System.currentTimeMillis()) {
			switchBtn.changeState(Timed.CLOSE);
			switchBtn.setEnable(false);
			timed.close();
			database.updTimed(timed);
			
		} else {
			switchBtn.setEnable(true);
			switchBtn.changeState(timed.getIsOpen());
			
			switchBtn.setOnStateChangeListener(new OnStateChangeListener() {
				
				@Override
				public void onStateChanged(boolean isOn) {
					if (isOn) {
						timed.open();
						AlarmUtil.setAlarms(context, timed);
					} else {
						AlarmUtil.cancelAlarms(context, timed);
						timed.close();
					}
					database.updTimed(timed);
				}
			});
		}
		
		
	}



	

}
