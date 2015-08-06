package com.mengzhu.daily;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mengzhu.daily.adapter.TimedListAdapter;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Timed;
import com.mengzhu.daily.receiver.AlarmOverReceiver;
import com.mengzhu.daily.receiver.AlarmOverReceiver.AlarmOverInterface;

public class TimedListFragment extends BaseFragment implements AlarmOverInterface{
	public static final String TAG = "TimedListFragment";
	private ListView timedListView;
	private List<Timed> timeds = new ArrayList<Timed>();
	private TimedListAdapter adapter;
	private DailyDataSource dataSource;
	private Handler handler = new Handler();
	private Context context;

	private AlarmOverReceiver alarmOverReceiver = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		alarmOverReceiver = new AlarmOverReceiver(this);
		
		View view = inflater.inflate(R.layout.timer_task_fragment_layout,
				container, false);
		timedListView = (ListView) view.findViewById(R.id.timed_lisview);

		timedListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						AddTimedActivity.start(
								(Activity) context,
								(Timed) timedListView.getAdapter().getItem(
										position));
					}
				}, 400);
			}
		});
		dataSource = DailyDataSource.getInstance(getActivity());
		System.out.println("fragment oncreate view");

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("fragment onActivityCreated");

		// 注册广播
		IntentFilter intentFilter = new IntentFilter(
				AlarmOverReceiver.ACTION_ALARM_OVER);
		context.registerReceiver(alarmOverReceiver, intentFilter);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		timeds.clear();
		timeds.addAll(dataSource.getTimeds());

		if (adapter == null) {
			adapter = new TimedListAdapter(getActivity(), timeds);
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

	@Override
	public void onDestroy() {
		context.unregisterReceiver(alarmOverReceiver);
		super.onDestroy();
	}

	@Override
	public void onAlarmOver(int timedId) {
		for (int i = 0; i < timeds.size(); i++) {
			if (timeds.get(i).getId() == timedId) {
				timeds.get(i).setIsOpen(Timed.CLOSE);
				adapter.notifyDataSetChanged();
				return;
			}
		}
	}
	
}
