package com.mengzhu.daily;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.util.Constant;

public class StatisticFragment extends BaseFragment {
	public static final String TAG = "StatisticFragment";
	private Context context;
	private PieChart pieChart;
	protected String[] mParties = new String[] { "完成", "未完成" };
	private DailyDataSource dataSource;
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			setData();
		}
		
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.statistic_fragment_layout,
				container, false);
		context = getActivity();
		dataSource = DailyDataSource.getInstance(getActivity());
		
		pieChart = (PieChart) view.findViewById(R.id.piechart);
		
		initPieChart();
		return view;
	}
	
	@Override
	public void onResume() {
		System.out.println("fragment onresume");
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_TASK_STATE_CHANGE);
		getActivity().registerReceiver(mReceiver, intentFilter);
		super.onResume();
	}
	
	@Override
	public void onPause() {
		getActivity().unregisterReceiver(mReceiver);
		super.onPause();
	}

	private void initPieChart() {
		pieChart.setUsePercentValues(true);
		pieChart.setDescription("");
		pieChart.setDragDecelerationFrictionCoef(0.95f);

		pieChart.setCenterTextTypeface(Typeface.createFromAsset(context.getAssets(),
				"OpenSans-Light.ttf"));
		pieChart.setDrawHoleEnabled(true);
		pieChart.setHoleColorTransparent(true);

		pieChart.setTransparentCircleColor(Color.WHITE);
		pieChart.setTransparentCircleAlpha(110);

		pieChart.setHoleRadius(58f);
		pieChart.setTransparentCircleRadius(61f);

		pieChart.setDrawCenterText(true);

		pieChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		pieChart.setRotationEnabled(true);

		setData();
		pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
	}
	
	private void setData(){
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();

		yVals1.add(new Entry((float)(dataSource.getAllTaskCount()-dataSource.getOpenTaskCount())/dataSource.getAllTaskCount() * 100, 0));
		yVals1.add(new Entry((float)dataSource.getOpenTaskCount()/dataSource.getAllTaskCount() * 100, 1));

		ArrayList<String> xVals = new ArrayList<String>();

		for (int i = 0; i < 2 + 1; i++)
			xVals.add(mParties[i % mParties.length]);

		PieDataSet dataSet = new PieDataSet(yVals1, "");
		dataSet.setSliceSpace(3f);
		dataSet.setSelectionShift(5f);

		// add a lot of colors

		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);
		
		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);


		dataSet.setColors(colors);

		PieData data = new PieData(xVals, dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setValueTextColor(Color.WHITE);
		pieChart.setData(data);

		// undo all highlights
		pieChart.highlightValues(null);

		pieChart.invalidate();
	}

//	private void setData(int count, float range) {
//
//		float mult = range;
//
//		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//
//		// IMPORTANT: In a PieChart, no values (Entry) should have the same
//		// xIndex (even if from different DataSets), since no values can be
//		// drawn above each other.
//		for (int i = 0; i < count; i++) {
//			yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
//			System.out.println("random  "+(Math.random() * mult) + mult / 5);
//		}
//
//		ArrayList<String> xVals = new ArrayList<String>();
//
//		for (int i = 0; i < count + 1; i++)
//			xVals.add(mParties[i % mParties.length]);
//
//		PieDataSet dataSet = new PieDataSet(yVals1, "");
//		dataSet.setSliceSpace(3f);
//		dataSet.setSelectionShift(5f);
//
//		// add a lot of colors
//
//		ArrayList<Integer> colors = new ArrayList<Integer>();
//
////		for (int c : ColorTemplate.VORDIPLOM_COLORS)
////			colors.add(c);
////
//		for (int c : ColorTemplate.JOYFUL_COLORS)
//			colors.add(c);
////
//		for (int c : ColorTemplate.COLORFUL_COLORS)
//			colors.add(c);
//
////		for (int c : ColorTemplate.LIBERTY_COLORS)
////			colors.add(c);
//
////		for (int c : ColorTemplate.PASTEL_COLORS)
////			colors.add(c);
//
////		colors.add(ColorTemplate.getHoloBlue());
//
//		dataSet.setColors(colors);
//
//		PieData data = new PieData(xVals, dataSet);
//		data.setValueFormatter(new PercentFormatter());
//		data.setValueTextSize(11f);
//		data.setValueTextColor(Color.WHITE);
//		pieChart.setData(data);
//
//		// undo all highlights
//		pieChart.highlightValues(null);
//
//		pieChart.invalidate();
//	}

	@Override
	public void action(Activity activity) {

	}
}
