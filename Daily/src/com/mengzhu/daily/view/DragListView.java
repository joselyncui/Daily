package com.mengzhu.daily.view;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.mengzhu.daily.AddTaskActivity;
import com.mengzhu.daily.R;
import com.mengzhu.daily.adapter.DragListAdapter;
import com.mengzhu.daily.adapter.TaskListAdapter;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Task;

/**
 * Created by MGC01 on 7/7/2015.
 */
public class DragListView extends ListView implements OnItemClickListener{
    public final String TAG = "DragListView";
    //要拖动条目的原始位置
    private int dragSrcPosition;
    //要拖动条目的目标位置
    private int dragDestPosition;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    private ImageView dragImageView;
    private ViewGroup itemView;
    private Context context;

    int dragPoint;
    int dragOffset;
    private Handler handler;
    
    private OnDropListener onDropListener;
    

    public DragListView(Context context) {
        super(context);
        init(context);
        
    }

    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    private void init(Context context){
    	this.context = context;
    	this.setOnItemClickListener(this);
    	this.handler = new Handler(context.getMainLooper());
    	
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int x = (int)ev.getX();
            int y = (int)ev.getY();
            //获取手指所在条目位置
            dragSrcPosition = pointToPosition(x,y);

            if (dragSrcPosition == INVALID_POSITION) {
				return super.onInterceptTouchEvent(ev);
			}

            //获取条目的布局
            itemView = (ViewGroup)getChildAt(dragSrcPosition-getFirstVisiblePosition());

            //手指在条目中相对Y坐标
            dragPoint = y-itemView.getTop();
            //Listview在屏幕中Y坐标
            dragOffset = (int) (ev.getRawY() - y);

                    //获取图标控件
            View dragger = itemView.findViewById(R.id.item_icon);
            if (dragger!= null && x < dragger.getRight()+20){
                //截图
                itemView.setDrawingCacheEnabled(true);
                Bitmap bm = itemView.getDrawingCache();

                startDrag(bm, y);
                return true;
            }
//            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void startDrag(Bitmap bm, int y) {
        windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        windowParams = new WindowManager.LayoutParams();
        windowParams.x = 0;
        windowParams.y = y -dragPoint + dragOffset;
        windowParams.alpha = 0.8f;
        windowParams.gravity = Gravity.TOP;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            |WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = android.R.style.Animation_Dialog;

        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bm);
        imageView.setBackgroundColor(getResources().getColor(R.color.white));
        windowManager.addView(imageView,windowParams);
        dragImageView = imageView;

    }



    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (dragImageView != null && dragDestPosition != INVALID_POSITION) {
            switch (ev.getAction()){
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int)ev.getY();

                    if (getHeight()-moveY < 200) {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                smoothScrollToPosition(getLastVisiblePosition() + 1);
                            }
                        });

                    } else if(moveY < 200 && moveY > 0) {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                smoothScrollToPosition(getFirstVisiblePosition() - 1);
                            }
                        });
                    }

                    if (moveY>0 && moveY < getHeight()) {
                        onDrag(moveY);
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    int upY = (int)ev.getY();
                    stopDrag();
                    onDrop(upY);
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 截图跟着手指移动*/
    private void onDrag(int moveY){
        if (dragImageView != null ) {
            windowParams.y = moveY-dragPoint + dragOffset;
            windowManager.updateViewLayout(dragImageView,windowParams);

        }
    }

    private void stopDrag(){
        if (dragImageView!=null) {
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }

    private void onDrop(int upY) {
        itemView.setDrawingCacheEnabled(false);//释放缓存
        //拖动到的目标位置
        int tempPosition = pointToPosition(0, upY);
        if (tempPosition != INVALID_POSITION ) {
            dragDestPosition = tempPosition;
        }

        TaskListAdapter<Task> adapter = (TaskListAdapter)getAdapter();

        if(dragDestPosition >= 0 && dragDestPosition < getAdapter().getCount()) {
        	if(onDropListener != null){
        		onDropListener.onDrop(dragSrcPosition, dragDestPosition);
        	}
         }
        

    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				AddTaskActivity.start((Activity)context, (Task)getAdapter().getItem(position));
			}
		}, 400);
	}
	
	public void setOnDropListener(OnDropListener onDropListener) {
		this.onDropListener = onDropListener;
	}
	
	public interface OnDropListener{
		public void onDrop(int from, int to);
	}
}
