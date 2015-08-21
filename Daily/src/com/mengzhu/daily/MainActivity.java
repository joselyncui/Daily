package com.mengzhu.daily;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengzhu.daily.view.TabView;
import com.mengzhu.daily.view.TabView.TabClickListener;

public class MainActivity extends BaseActivity implements TabClickListener,
		OnClickListener {

	private FragmentManager fm;
	private TabView tabView;
	

	// tabview
	private String[] actions = { "常规", "定时", "统计" };
	private int[] checkedIcons = { R.drawable.task, R.drawable.timed,
			R.drawable.statistic };
	private int[] uncheckedIcons = { R.drawable.task_unchecked,
			R.drawable.timed_uncheched, R.drawable.statistic_unchecked };
	private int[] textColors = { R.color.light_gray, R.color.colorPrimary };
	private BaseFragment[] fragments;
	private TextView toolbarTitle;
	private ImageView actionImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabView = (TabView) findViewById(R.id.tabview);
		toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
		actionImageView = (ImageView) findViewById(R.id.toolbar_action);
		actionImageView.setOnClickListener(this);

		init(savedInstanceState);// 初始化fragment

		if (isLater) {
			MarginLayoutParams params = (MarginLayoutParams) findViewById(
					R.id.toolbar).getLayoutParams();
			params.setMargins(0, statusBarHeight, 0, 0);
		}

		
	}

	/**
	 * init fragments
	 * 
	 * @param instanceState
	 */
	private void init(Bundle instanceState) {
		fm = getFragmentManager();
		fragments = new BaseFragment[] { new TaskListFragment(),
				new TimedListFragment(), new StatisticFragment() };
		tabView.init(fm, fragments, actions, uncheckedIcons, checkedIcons,
				textColors);
	}

	@Override
	public void onTabClick(int position) {
		toolbarTitle.setText(actions[position]);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toolbar_action:
			tabView.getCurrentFragment().action(this);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

}
