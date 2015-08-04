package com.mengzhu.daily;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;

import com.mengzhu.daily.adapter.DragListAdapter;
import com.mengzhu.daily.adapter.TaskListAdapter;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Task;
import com.mengzhu.daily.view.DragListView;
import com.mengzhu.daily.view.DragListView.OnDropListener;

public class TaskListFragment extends BaseFragment{
	
	public static final String TAG = "TaskListFragment";
	
	public static final int UPDATE_CURSOR = 1;
	private DragListView dragListView;
	private TaskListAdapter<Task> taskListAdapter;
    private List<Task> tasks;
    private DailyDataSource dataSource;
    
   
    private Context context;
    private Cursor cursor;
    
    private Handler handler = new Handler(){
    	public void handleMessage(Message msg) {
    		if (msg.what == UPDATE_CURSOR) {
				taskListAdapter.changeCursor(dataSource.getTaskCursor());
			}
    	};
    };
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.task_fragment_layout, container, false);
		dragListView = (DragListView)view.findViewById(R.id.drag_list_view);
		dataSource = DailyDataSource.getInstance(getActivity());
		
		tasks = new ArrayList<Task>();
		context = getActivity();
		
		cursor = dataSource.getTaskCursor();
		taskListAdapter = new TaskListAdapter<Task>(context, cursor, false, R.layout.task_list_item_layout);
		dragListView.setAdapter(taskListAdapter);
		
		dragListView.setOnDropListener(new OnDropListener() {
			
			@Override
			public void onDrop(int from, int to) {
				Task task = (Task) dragListView.getAdapter().getItem(from);
				
				dataSource.deleteTask(task);
				dataSource.addTask(task);
				
				handler.sendEmptyMessage(UPDATE_CURSOR);//update cursor
			}
		});
        
		return view;
	}
	
	@Override
	public void onResume() {
		handler.sendEmptyMessage(UPDATE_CURSOR);
		super.onResume();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void action(Activity activity){
		AddTaskActivity.start(activity,null);
	}
}
