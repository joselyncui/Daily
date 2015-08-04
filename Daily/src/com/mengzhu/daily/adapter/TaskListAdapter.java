package com.mengzhu.daily.adapter;

import com.mengzhu.daily.R;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Task;
import com.mengzhu.daily.view.SwitchButton;
import com.mengzhu.daily.view.SwitchButton.OnStateChangeListener;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskListAdapter<T> extends CursorAdapter {

	private int resourceId;
	LayoutInflater inflater;
	
	private DailyDataSource dataSource;

	public TaskListAdapter(Context context, Cursor c, boolean autoRequery,
			int resourceId) {
		super(context, c, autoRequery);
		dataSource = DailyDataSource.getInstance(context);
		this.resourceId = resourceId;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public Object getItem(int position) {
		return DailyDataSource.cursorToTask((Cursor)super.getItem(position));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(resourceId, parent, false);
		return view;
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		TextView title = (TextView) convertView.findViewById(R.id.item_title);
		TextView levelTextView = (TextView) convertView
				.findViewById(R.id.item_imp);
		TextView hourTextView = (TextView) convertView
				.findViewById(R.id.item_time);
		SwitchButton switchButton = (SwitchButton) convertView
				.findViewById(R.id.item_toggle);

		final Task task = DailyDataSource.cursorToTask(cursor);
		title.setText(task.getComment());
		levelTextView.setText(task.getLevel() + "级 " + task.getIsOpen() +" id " + task.getId());
		hourTextView.setText(task.getHours() + "小时");
		switchButton.changeState(task.getIsOpen());
		switchButton.setEnable(true);
		
		switchButton.setOnStateChangeListener(new OnStateChangeListener() {

			@Override
			public void onStateChanged(boolean isOn) {
				System.out.println("state change");
				// 更新数据库
				if (isOn) {
					task.setIsOpen(Task.OPEN);
				} else {
					task.setIsOpen(Task.CLOSE);
				}
				dataSource.updateTask(task);
				System.out.println("update " + isOn +"  " + task.getId());
			}
		});

	}

}
