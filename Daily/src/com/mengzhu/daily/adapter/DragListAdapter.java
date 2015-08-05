package com.mengzhu.daily.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.mengzhu.daily.R;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Task;
import com.mengzhu.daily.view.SwitchButton;
import com.mengzhu.daily.view.SwitchButton.OnStateChangeListener;

/**
 * Created by MGC01 on 7/7/2015.
 */
public class DragListAdapter extends SimpleAdapter<Task> {
	Handler handler ;
	DailyDataSource dataSource;
	
	private static boolean isSwitchBtnEnable= true;

    public DragListAdapter(Context context, List<Task> tasks, Handler handler) {
        super(context,tasks);
        this.handler = handler;
        dataSource = DailyDataSource.getInstance(context);
    }

	@Override
	public int getItemResourceId() {
		return R.layout.task_list_item_layout;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		final Task task = getItem(position);
		
		System.out.println("bind data**********" + task.getIsOpen());
		
		TextView title = holder.findView(R.id.item_title);
		TextView levelTextView = holder.findView(R.id.item_imp);
		TextView hourTextView = holder.findView(R.id.item_time);
		final SwitchButton switchButton = holder.findView(R.id.item_toggle);
		
		switchButton.changeState(task.getIsOpen());
		
		title.setText(task.getComment());
		levelTextView.setText(task.getLevel() +"级");
		hourTextView.setText((int)task.getHours()+"小时");
		
		switchButton.setOnStateChangeListener(new OnStateChangeListener() {
			
			@Override
			public void onStateChanged(boolean isOn) {
				
				System.out.println("onStateChanged " + isOn);
				if (!isSwitchBtnEnable) {
					return;
				}
				//更新数据库
				if (isOn) {
					task.setIsOpen(Task.OPEN);
				} else {
					task.setIsOpen(Task.CLOSE);
				}
				dataSource.updateTask(task);
			}
		});
		
	}

}
