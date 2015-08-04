package com.mengzhu.daily.view;

import com.mengzhu.daily.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class SwitchButton extends View implements OnClickListener {

	/**
	 * 状态切换监听
	 */
	private OnStateChangeListener mListener;
	/**
	 * 设置绘图用的画笔
	 */
	private Paint mPaint;
	/**
	 * 背景图
	 */
	private Bitmap mBackground;
	/**
	 * 打开状态的背景图
	 */
	private Bitmap mBackgroundActivited;
	/**
	 * 关闭状态的背景图
	 */
	private Bitmap mBackgroundNormal;
	/**
	 * 滑动图片
	 */
	private Bitmap mSlideButton;
	/**
	 * 滑动图片-正常状态
	 */
	private Bitmap mSlideButtonNormal;
	/**
	 * 滑动图片-按下状态
	 */
	private Bitmap mSlideButtonPressed;

	/**
	 * 绘制slideButton时的左边距
	 */
	private float mSlideLeft;

	/**
	 * 当前的状态 true-开 false-关
	 */
	private boolean isOn = false;

	/**
	 * slideButton绘制时的最大左边距
	 */
	private int mSlideMaxLeft;

	/**
	 * 判断点击是否可用
	 * 
	 * true 为可用 false 为不可用
	 */
	private boolean isClickEnable = true;

	/**
	 * 判断slideBitmap是否可滑动 当Action_Down时，如果当前点击位置并不在slideBitmap上，则不可滑动
	 */
	private boolean isSlideEnable = false;

	private boolean isEnable = true;

	public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public SwitchButton(Context context) {
		super(context);
		initView();
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBackground, 0, 0, mPaint);
		canvas.drawBitmap(mSlideButton, mSlideLeft, 0, mPaint);
	}

	/**
	 * 测量整个View的大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(mBackground.getWidth(), mBackground.getHeight());
	}

	private void initView() {
		mPaint = new Paint();
		// 抗矩齿，一般都会设置
		mPaint.setAntiAlias(true);

		// 从资源中将图片解析成bitmap对象
		mSlideButton = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_thumb_disabled);
		mSlideButtonNormal = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_thumb_disabled);
		mSlideButtonPressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_thumb_disabled_pressed);

		mBackground = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_track);

		mBackgroundActivited = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_track_activited);

		mBackgroundNormal = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_track);

		// 取得 滑动按钮可以滑动的最大的左边距
		mSlideMaxLeft = mBackground.getWidth() - mSlideButton.getWidth();

		this.setOnClickListener(this);
	}

	/**
	 * down 事件时的 x 坐标
	 */
	private int firstX;
	/**
	 * 上一个事件的x坐标
	 */
	private int lastX;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		if (!isEnable) {
			return true;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = lastX = (int) event.getX();

			isClickEnable = true;
			mSlideButton = mSlideButtonPressed;
			// isSlideEnable = isTouchSildeButton(event);
			break;
		case MotionEvent.ACTION_MOVE:
			int distace = (int) (event.getX() - lastX);
			lastX = (int) event.getX();

			if (isSlideEnable)
				mSlideLeft += distace;

			if (Math.abs(event.getX() - firstX) > 1)
				isClickEnable = false;

			break;
		case MotionEvent.ACTION_UP:

			if (!isClickEnable && isSlideEnable) {

				if (mSlideLeft >= mSlideMaxLeft / 2) {
					isOn = true;
				} else {
					isOn = false;
				}
				
				System.out.println("action up");
				switchState();
			}

			mSlideButton = mSlideButtonNormal;
			break;

		default:
			break;
		}

		flushView();
		return true;
	}

	/**
	 * 切换开关的状态 如果为开-更换当前背景图为Activited,并放置slideBitmap最右边
	 * 如果为关-更换当前背景图为Normal,并放置slideBitmap最左边
	 */
	private void switchState() {

		if (isOn) {
			mSlideLeft = mSlideMaxLeft;
			mBackground = mBackgroundActivited;

			if (mListener != null)
				mListener.onStateChanged(true);
		} else {
			mBackground = mBackgroundNormal;
			mSlideLeft = 0;

			if (mListener != null)
				mListener.onStateChanged(false);
		}

		flushView();
	}

	/**
	 * 给slideButton设定正确的位置，并刷新View
	 */
	private void flushView() {
		// 确保 slideLeft 的值是正确的,不会越界
		if (mSlideLeft < 0)
			mSlideLeft = 0;

		if (mSlideLeft > mSlideMaxLeft)
			mSlideLeft = mSlideMaxLeft;

		invalidate();
	}

	/**
	 * 判断滑动按钮是否可以滑动，如果点击时，焦点不在滑动按钮上,就不能滑动。
	 * 
	 * @param event
	 *            当前点击事件
	 * @return
	 */
	private boolean isTouchSildeButton(MotionEvent event) {
		int slideWidth = mSlideButton.getWidth();
		int width = getWidth();

		int x = (int) event.getX(); // 当前点击时,相对于自己的偏移位置

		if (isOn) { // 当开关为开时
			if (width - slideWidth < x)
				return true;
			else
				return false;
		} else { // 当开关为关时
			if (x <= slideWidth)
				return true;
			else
				return false;
		}
	}

	/**
	 * 当发生点击事件时，直接切换SwitchButton状态
	 */
	@Override
	public void onClick(View v) {

		System.out.println("onclick");
		// 当发生滑动事件以后，设置onclick无效
		if (isClickEnable && isEnable) {
			isOn = !isOn;
			switchState();
		}
	}

	/**
	 * 切换SwitchButton状态为打开
	 */
	public void turnOn() {
		
		System.out.println("turn on");
		if (!isOn) {
			isOn = true;
			switchState();
		}
	}

	/**
	 * 切换SwitchButton状态为关闭
	 */
	public void turnOff() {
		System.out.println("turn off");
		
		if (isOn) {
			isOn = false;
			switchState();
		}
	}

	public void changeState(int state) {
		if (state == 0) {
			turnOn();
		} else {
			turnOff();
		}
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	/**
	 * 当前的状态
	 * 
	 * @return
	 */
	public boolean isOn() {
		return isOn;
	}

	/**
	 * 设置监听
	 * 
	 * @param listener
	 */
	public void setOnStateChangeListener(OnStateChangeListener listener) {
		this.mListener = listener;
	}

	/**
	 * SwitchButton状态改变接口
	 * 
	 * @author 房建斌
	 * @date 2014-11-12 下午1:32:47
	 * 
	 */
	public interface OnStateChangeListener {
		public void onStateChanged(boolean isOn);
	}
}