package com.mengzhu.daily.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mengzhu.daily.R;
import com.mengzhu.daily.TaskListFragment;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Task;
import com.mengzhu.daily.view.SwitchButton;

public class TaskListAdapter<T> extends CursorAdapter {

	private int resourceId;
	LayoutInflater inflater;
	
	private DailyDataSource dataSource;
	private Handler handler;

	public TaskListAdapter(Context context, Cursor c, boolean autoRequery,
			int resourceId, Handler handler) {
		super(context, c, autoRequery);
		this.handler = handler;
		
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
		final SwitchButton switchButton = (SwitchButton) convertView
				.findViewById(R.id.item_toggle);

		final Task task = DailyDataSource.cursorToTask(cursor);
		title.setText(task.getComment());
		levelTextView.setText(task.getLevel() + "级 " );
		hourTextView.setText(task.getHours() + "小时");
		
		switchButton.changeState(task.getIsOpen());
		switchButton.setEnable(true);
		
		switchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (switchButton.isOn()) {
					switchButton.changeState(Task.CLOSE);
					task.setIsOpen(Task.CLOSE);
				} else {
					switchButton.changeState(Task.OPEN);
					task.setIsOpen(Task.OPEN);
				}
				
				dataSource.updateTask(task);
				handler.sendEmptyMessage(TaskListFragment.UPDATE_CURSOR);
			}
		});
		
	}

}
