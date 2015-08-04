package com.mengzhu.daily.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mengzhu.daily.BaseFragment;
import com.mengzhu.daily.R;

public class TabView extends LinearLayout implements OnClickListener {
	private Context context;
	public BaseFragment[] fragments;
	private int[] checkedIcons;
	private int[] uncheckedIcons;
	public int[] textColors = { android.R.color.darker_gray,
			android.R.color.holo_blue_bright };

	private int iconPosition = IconPosition.LEFT;// drawable 位置
	private FragmentManager fm;
	private BaseFragment current;// 当前fragment
	private int lastIndex = -1;

	static class IconPosition {
		public static final int LEFT = 0;
		public static final int TOP = LEFT + 1;
		public static final int RIGHT = LEFT + 2;
		public static final int BOTTOM = LEFT + 3;
	}

	public TabView(Context context) {
		super(context);
		this.context = context;
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void init(FragmentManager fm, BaseFragment[] fragments,
			String actions[], int[] uncheckedIcons, int[] checkedIcons,
			int[] textColors) {
		this.fm = fm;
		this.fragments = fragments;
		this.textColors = textColors;
		this.uncheckedIcons = uncheckedIcons;
		this.checkedIcons = checkedIcons;
		this.textColors = textColors;

		for (int i = 0; i < fragments.length; i++) {
			TextView textView = new TextView(context);
			textView.setText(actions[i]);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
			Drawable drawable = getResources().getDrawable(uncheckedIcons[i]);

			//这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			textView.setCompoundDrawables(null, drawable, null, null);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			textView.setTag(R.id.tab_position, i);
			textView.setOnClickListener(this);
			textView.setTextSize(14);
			textView.setTextColor(getResources().getColor(textColors[0]));
			addView(textView);
		}

		getChildAt(0).performClick();
	}

	@Override
	public void onClick(View v) {
		int i = (int) v.getTag(R.id.tab_position);
		showContent(fragments[i]);
		changeTextColor(i);
		((TabClickListener)context).onTabClick(i);
	}

	private void showContent(BaseFragment to) {
		if (to != current) {
			FragmentTransaction transaction = fm.beginTransaction();
			if (!to.isAdded()) {
				transaction.add(R.id.fragment_container, to);
			}

			transaction.show(to);

			if (current != null) {
				transaction.hide(current);
			}

			current = to;
			transaction.commit();
		}
	}

	private void changeTextColor(int toIndex) {

		if (lastIndex != -1) {
			TextView lastTextView = (TextView) getChildAt(lastIndex);
			lastTextView.setTextColor(getResources().getColor(textColors[0]));
			Drawable drawable = getResources().getDrawable(uncheckedIcons[lastIndex]);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());//这一步必须要做,否则不会显示.
			
			lastTextView.setCompoundDrawables(null,drawable, null,null);
		}

		if (toIndex >= 0) {
			TextView toTextView = (TextView) getChildAt(toIndex);
			toTextView.setTextColor(getResources().getColor(textColors[1]));

			Drawable drawable = getResources().getDrawable(checkedIcons[toIndex]);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());//这一步必须要做,否则不会显示.
			
			toTextView.setCompoundDrawables(null,drawable, null,null);
			lastIndex = toIndex;
		}
	}
	
	public BaseFragment getCurrentFragment(){
		return current;
	}
	
	public interface TabClickListener{
		
		/**
		 * tab 点击事件
		 * @param position
		 */
		public void onTabClick(int position);
	}

}
