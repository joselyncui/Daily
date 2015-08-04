package com.mengzhu.daily;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mengzhu.daily.adapter.TimedListAdapter;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Timed;

public class TimedListFragment extends BaseFragment{
	public static final String TAG = "TimedListFragment";
	private ListView timedListView;
	private List<Timed> timeds = new ArrayList<Timed>();
	private TimedListAdapter adapter;
	private DailyDataSource dataSource;
	private Handler handler = new Handler();
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		
		View view = inflater.inflate(R.layout.timer_task_fragment_layout, container,false);
		timedListView = (ListView) view.findViewById(R.id.timed_lisview);
		
		timedListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						AddTimedActivity.start((Activity)context, (Timed)timedListView.getAdapter().getItem(position));
					}
				}, 400);
			}
		});
		dataSource = DailyDataSource.getInstance(getActivity());
		
		return view;
	}
	
	@Override
	public void onResume() {
		timeds.clear();
		timeds.addAll(dataSource.getTimeds());
		
		if (adapter == null) {
			adapter = new TimedListAdapter(getActivity(),timeds);
			timedListView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void action(final Activity activity) {
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				AddTimedActivity.start(activity, null);
			}
		}, 1000);
		
	}

}
